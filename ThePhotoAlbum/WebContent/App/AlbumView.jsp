<%@ taglib tagdir="/WEB-INF/tags/import" prefix="import"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="comp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>The Photo Album</title>
<import:bootstrap4_3 />
<import:stylesheet file="index.css" />
</head>
<body>
	<comp:navigationBarUser />
	<!--  Main Content-->
	<section>
		<div class="container-fluid">
			<div class="row">
				<div class="col p-3">
					<nav aria-label="breadcrumb">
						<ol class="breadcrumb">
							<li class="breadcrumb-item active" aria-current="page"><a
								href="${pageContext.request.contextPath }/App/Home">Home</a></li>
							<li class="breadcrumb-item active" aria-current="page"><a
								href="${pageContext.request.contextPath }/App/Albums">All
									Albums</a></li>
							<li class="breadcrumb-item active" aria-current="page"><a
								href="${pageContext.request.contextPath }/App/AlbumView?albumId=${album.id}">${album.title}</a></li>
						</ol>
					</nav>
				</div>
			</div>
			<div class="row">
				<aside class="col-lg-3 col-md-4 order-lg-first order-last">
					<comp:dashboard username="${USER_SESSION.currentUser.name }" />
					<comp:findPhotos />
				</aside>
				<main class="col-lg-9 col-md-8 order-lg-last order-first"> <!-- Album View  -->
				<div class="container shadow rounded my-3">
					<div class="row p-2  shadow-sm bg-light text-dark border">
						<h5>
							<i class="fas fa-images text-primary"></i> ${album.title} <span
								class="badge badge-primary">${album.totalPhotos}</span>
						</h5>
					</div>
					<div class="row p-3 justify-content-center">
						<c:choose>
							<c:when test="${fn:length(photos) gt 0 }">
								<div class="image-grid-row">
									<c:forEach var="photo" items="${photos}"
										varStatus="status">
										<c:if test="${status.index%2 eq 0 }">
											<div class="image-grid-column">
										</c:if>
										<a href="${pageContext.request.contextPath}/App/ViewPhoto?albumId=${photo.albumId}&photoId=${photo.id}">
										<img
											src="${pageContext.request.contextPath }/App/GetPhotoThumb?albumId=${photo.albumId}&photoId=${photo.id}"
											title="Title :  ${photo.title }" />
										</a>
										<c:if
											test="${((status.index +1) % 2 eq 0) or (status.count eq fn:length(photos)) }">
											</div>
								</c:if>
								</c:forEach>
								<br>
								<div class="col-12 p-3">
									<nav aria-label="Page navigation example">
									<ul class="pagination justify-content-center">
									
										<c:if test="${paginationContext.currentPage gt 1}">
											<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/App/AlbumView?page=${paginationContext.currentPage - 1}&albumId=${album.id}">Previous</a></li>
										</c:if>
										<c:forEach begin="1" end="${paginationContext.totalPages}" var="current">
											<c:choose>
												<c:when test="${current eq paginationContext.currentPage}">
													<li class="page-item active"><a class="page-link" href="${pageContext.request.contextPath}/App/AlbumView?page=${current}&albumId=${album.id}">${current}</a></li>
												</c:when>
												<c:otherwise>
													<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/App/AlbumView?page=${current}&albumId=${album.id}">${current}</a></li>
												</c:otherwise>
											</c:choose>
										</c:forEach>
										<c:if test="${paginationContext.currentPage lt paginationContext.totalPages }">
											<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/App/AlbumView?page=${paginationContext.currentPage + 1}&albumId=${album.id}">Next</a></li>
										</c:if>
									</ul>
								</nav>
								</div>
							</div>
					</c:when>
					<c:otherwise>
						<p class="h6 text-muted">You Don't Have Uploaded Any Photo
							Yet.</p>
					</c:otherwise>
					</c:choose>
					
				</div>
				 <hr>
				<div class="row p-3">
					<p class="h5">Album Information</p>
					<table class="table table-striped">
						<tr>
							<td><strong>Title</strong></td>
							<td>${album.title}</td>
						</tr>
						<tr>
							<td><strong>Description</strong></td>
							<td>${album.description}</td>
						</tr>
						<tr>
							<td><strong>Created</strong></td>
							<td><fmt:formatDate value="${album.createTime}" pattern="hh:mm a, E, dd-MMM-yyyy"/></td>
						</tr>
						<tr>
							<td><strong>Photos</strong></td>
							<td>${album.totalPhotos}</td>
						</tr>
						<tr>
							<td><strong>Actions</strong></td>
							<td><a href="#" class="btn btn-sm btn-link text-primary"><i
									class="fas fa-edit"></i> Edit</a> <a href="#"
								class="btn btn-sm btn-link text-danger"><i
									class="fas fa-trash"></i> Delete</a></td>
						</tr>
					</table>

				</div>
			</div>
		</main>
		</div>
		</div>
	</section>
</body>