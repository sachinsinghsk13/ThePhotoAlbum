<%@ taglib tagdir="/WEB-INF/tags/import" prefix="import"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="comp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
							<li class="breadcrumb-item" aria-current="page"><a
								href="${pageContext.request.contextPath }/App/Home">Home</a></li>
							<li class="breadcrumb-item active" aria-current="page"><a
								href="${pageContext.request.contextPath }/App/Albums">All
									Albums</a></li>
						</ol>
					</nav>
				</div>
			</div>
			<div class="row">
				<aside class="col-lg-3 col-md-4 order-lg-first order-last">
					<comp:dashboard username="${USER_SESSION.currentUser.name }" />
					<comp:findPhotos />
				</aside>
				<main class="col-lg-9 col-md-8 order-lg-last order-first"> <!-- Albums -->

				<div class="container shadow rounded my-3">
					<div class="row p-2 shadow-sm bg-light border">
						<h5>
							<i class="fas fa-images text-success"></i> Albums <span
								class="badge badge-secondary">${paginationContext.totalItems}</span>
						</h5>
					</div>
					<div class="row p-2">
						<c:choose>
							<c:when test="${fn:length(albums) gt 0 }">
								<c:forEach var="album" items="${albums }">
									<div class="col-lg-4 col-md-6 col-sm-12 my-2"
										title="${album.description }">
										<div class="container album shadow-sm p-2">
											<div class="row">
												<div class="col-3 mx-1">
													<img
														src="${pageContext.request.contextPath }/App/GetAlbumThumb?albumId=${album.id}"
														alt="" height="90px" width="90px">
												</div>
												<div class=" offset-1 col-7">
													<h6>${album.title }</h6>
													<p class="text-muted">${album.totalPhotos }Photos</p>
													<a href="" class="btn btn-sm btn-primary">View Photos</a>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
								<br>
								<div class="col-12">
									<nav aria-label="Page navigation example">
									<ul class="pagination justify-content-center">
									
										<c:if test="${paginationContext.currentPage gt 1}">
											<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/App/Albums?page=${paginationContext.currentPage - 1}">Previous</a></li>
										</c:if>
										<c:forEach begin="1" end="${paginationContext.totalPages}" var="current">
											<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/App/Albums?page=${current}">${current}</a></li>
										</c:forEach>
										<c:if test="${paginationContext.currentPage lt paginationContext.totalPages }">
											<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/App/Albums?page=${paginationContext.currentPage + 1}">Next</a></li>
										</c:if>
									</ul>
								</nav>
								</div>
							</c:when>
							<c:otherwise>
								<div class="p-3 text-center w-100">
									<p class="h6 text-muted">You Don't have uploaded any photo
										yet.</p>
								</div>
							</c:otherwise>
						</c:choose>
					</div>

				</div>

				</main>
			</div>
		</div>
	</section>
</body>