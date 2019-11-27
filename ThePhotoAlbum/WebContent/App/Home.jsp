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
				<aside class="col-lg-3 col-md-4 order-lg-first order-last">
					<comp:dashboard username="${USER_SESSION.currentUser.name }" />
					<comp:findPhotos />
				</aside>
				<main class="col-lg-9 col-md-8 order-lg-last order-first"> <!-- Recent Uploads View  -->

				<div class="container shadow rounded my-3">
					<div class="row p-2  shadow-sm bg-light text-dark border">
						<h5>
							<i class="fas fa-clock text-primary"></i> Recent Uploads
						</h5>
					</div>
					<div class="row p-3 justify-content-center">
					<c:forEach var="photo"  varStatus="itr"   items="${recentUploads }">
							<img src="${ pageContext.request.contextPath}/App/DownloadPhoto?albumId=${photo.albumId}&photoId=${photo.id}" class="img-fluid"/>
							<h4>${itr.index }</h4>
					</c:forEach>
						<!-- <div class="img-grid-row">
                                <div class="img-grid-col">
                                    <img src="img1.jpg" class="rounded shadow-sm" />
                                    <img src="img/img11.png" class="rounded shadow-sm" />
                                    <img src="img/img13.png" class="rounded shadow-sm" />
                                </div>
                                <div class="img-grid-col">
                                    <img src="img4.jpg" class="rounded shadow-sm" />
                                    <img src="img/img12.png" class="rounded shadow-sm" />
                                    <img src="img2.jpg" class="rounded shadow-sm" />
                                    <img src="img/img1.png" class="rounded shadow-sm" />
                                </div>
                            </div> -->
						<p class="h6 text-muted">You Don't have uploaded any photo
							yet.</p>
						<!-- <div class="text-center">
                                <div class="spinner-grow text-primary" role="status">
                                    <span class="sr-only">Loading...</span>
                                </div>
                                <div class="spinner-grow text-danger" role="status">
                                    <span class="sr-only">Loading...</span>
                                </div>
                                <div class="spinner-grow text-success" role="status">
                                    <span class="sr-only">Loading...</span>
                                </div>
                            </div> -->
					</div>
				</div>

				<!-- Albums -->

				<div class="container shadow rounded my-3">
					<div class="row p-2 shadow-sm bg-light border">
						<h5>
							<i class="fas fa-images text-success"></i> Albums <span
								class="badge badge-secondary">${totalAlbums}</span>
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
													<p class="text-muted">${album.totalPhotos } Photos</p>
													<a href="" class="btn btn-sm btn-primary">View Photos</a>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="p-3 text-center w-100">
									<p class="h6 text-muted">You Don't have uploaded any photo
										yet.</p>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<c:if test="${fn:length(albums) gt 0 }">
						<div class="p-2 justify-content-right">
							<a class="btn btn-outline-secondary btn-sm" href="#">See All
								Albums</a>
						</div>
					</c:if>

				</div>
				<div class="container shadow-sm rounded my-3">
					<div class="row p-2 shadow-sm bg-light">
						<h5>
							<i class="fas fa-heart text-danger"></i> Favourites <span
								class="badge badge-secondary">103</span>
						</h5>
					</div>
					<div class="row p-2"></div>
					<div class="p-3 text-center">
						<p class="h6 text-muted ">No Favourities Marked Yet.</p>
					</div>
				</div>
				</main>
			</div>
		</div>
	</section>
</body>