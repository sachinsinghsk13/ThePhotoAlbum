<%@ taglib tagdir="/WEB-INF/tags/import" prefix="import"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="comp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>The Photo Album</title>
<import:bootstrap4_3 />
<import:script file="albumview.js" />
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
									<c:forEach var="photo" items="${photos}" varStatus="status">
										<c:if test="${status.index%2 eq 0 }">
											<div class="image-grid-column">
										</c:if>
										<a
											href="${pageContext.request.contextPath}/App/ViewPhoto?albumId=${photo.albumId}&photoId=${photo.id}">
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
												<li class="page-item"><a class="page-link"
													href="${pageContext.request.contextPath}/App/AlbumView?page=${paginationContext.currentPage - 1}&albumId=${album.id}">Previous</a></li>
											</c:if>
											<c:forEach begin="1" end="${paginationContext.totalPages}"
												var="current">
												<c:choose>
													<c:when test="${current eq paginationContext.currentPage}">
														<li class="page-item active"><a class="page-link"
															href="${pageContext.request.contextPath}/App/AlbumView?page=${current}&albumId=${album.id}">${current}</a></li>
													</c:when>
													<c:otherwise>
														<li class="page-item"><a class="page-link"
															href="${pageContext.request.contextPath}/App/AlbumView?page=${current}&albumId=${album.id}">${current}</a></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
											<c:if
												test="${paginationContext.currentPage lt paginationContext.totalPages }">
												<li class="page-item"><a class="page-link"
													href="${pageContext.request.contextPath}/App/AlbumView?page=${paginationContext.currentPage + 1}&albumId=${album.id}">Next</a></li>
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
							<td><fmt:formatDate value="${album.createTime}"
									pattern="hh:mm a, E, dd-MMM-yyyy" /></td>
						</tr>
						<tr>
							<td><strong>Photos</strong></td>
							<td>${album.totalPhotos}</td>
						</tr>
						<tr>
							<td><strong>Actions</strong></td>
							<td><button id="openEditModal"
									class="btn btn-sm btn-link text-primary">
									<i class="fas fa-edit"></i> Edit
								</button> <button class="btn btn-sm btn-link text-danger" data-target="#deleteAlbumModal" data-toggle="modal"><i
									class="fas fa-trash"></i> Delete</button></td>
						</tr>
					</table>

				</div>
			</div>
			<!-- Modal -->
			<div class="modal fade" id="editAlbumModal" data-backdrop="static"
				tabindex="-1" role="dialog" aria-labelledby="EditAlbumModalLabel"
				aria-hidden="true" data-album-id="${album.id}">
				<div class="modal-dialog modal-dialog-centered" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="EditAlbumModalLabel">Edit Album</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">

							<div class="form-group">
								<input type="text" class="form-control"
									placeholder="Album Title" id="albumTitle"
									value="${album.title}">
							</div>
							<div class="form-group">
								<textarea id="albumDescription" cols="30" rows="3"
									class="form-control" style="resize: none;"
									placeholder="Album Description">${album.description}</textarea>
							</div>

						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-outline-secondary btn-sm"
								data-dismiss="modal">Cancel</button>
							<button type="button" class="btn btn-success btn-sm" id="saveBtn">
								Save</button>
						</div>
					</div>
				</div>
			</div>
			<!-- Modal For Deleting Album -->
            <div class="modal fade" id="deleteAlbumModal" data-backdrop="static"
				tabindex="-1" role="dialog" aria-labelledby="deleteAlbumModalLabel"
				aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="deleteAlbumModalLabel">Delete This Album?</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<p class="text-muted">
								Are you really want to delete this Album? This will delete all ${album.totalPhotos} photos which are in this album
							</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-outline-secondary btn-sm"
								data-dismiss="modal">Cancel</button>
							<a href="${pageContext.request.contextPath}/App/DeleteAlbum?albumId=${album.id}" class="btn btn-sm btn-danger">Delete</a>
						</div>
					</div>
				</div>
			</div>
			</main>
		</div>
		</div>
	</section>
</body>