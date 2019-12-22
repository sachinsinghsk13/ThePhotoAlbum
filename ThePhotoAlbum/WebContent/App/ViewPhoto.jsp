<%@ taglib tagdir="/WEB-INF/tags/import" prefix="import"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="comp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="thephotoalbum" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>The Photo Album</title>
<import:bootstrap4_3 />
<import:stylesheet file="index.css"/>
<import:script file="photoview.js"/>
</head>
<body>
	<comp:navigationBarUser />
	 <section id="photo_view">
        <div class="container-fluid shadow-sm my-3">
            <div class="row p-3" id="img-view-row">
                <div class="col d-flex justify-content-center">
                    <img src="${pageContext.request.contextPath}/App/GetPhotoThumb?albumId=${photo.albumId}&photoId=${photo.id}" alt="">
                </div>
            </div>
            <div class="row p-3">
                <div class="col-12">
                    <div class="h4">${photo.title}</div>
                </div>
                <div class="col-12">
                    <div class="text-muted"><fmt:formatDate value="${photo.uploadDate}" pattern="hh:mm a, EE, dd-MMM-yyyy"/></div>
                </div>
                <div class="col-12">
                    <div class="text-muted"><mytag:file-size size="${photo.fileSize}"/></div>
                </div>
            </div>
            <div class="row px-3">
                <div class="col-12">
                    <a href="${pageContext.request.contextPath}/App/AlbumView?albumId=${album.id}" class="btn-link"><i class="fas fa-folder"></i> ${album.title}</a>
                </div>
            </div>
            <div class="row p-3">
                <div class="col-12">
                    <span class="badge badge-pill badge-danger">${photo.orientation}</span>
                    <span class="badge badge-pill badge-success">${photo.quality}</span>
                    <span class="badge badge-pill badge-secondary">${photo.width}x${photo.height}</span> 
                </div>
            </div>
            <div class="row px-3 py-2">
                <div class="col-lg-8 col-md-12">
                    <p class="text-dark" id="description">${photo.description}</p>
                </div>
                <div class="col-lg-4 col-md-12">
                    <button class="btn btn-sm btn-link" id="openModalBtn"><i class="fas fa-edit"></i> Edit</button>
                    <button class="btn btn-sm btn-link text-secondary" data-toggle="modal" data-target="#deletePhotoModal"><i class="fas fa-trash"></i> Delete</button>
                    
                    <c:choose>
                    	<c:when test="${photo.favourite eq 'YES'}">
                    		<button class="btn btn-sm btn-link text-danger" id="changeFavourite" data-favourite="YES"><i class="fas fa-heart"></i> Unfavourite</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-sm btn-link text-danger" id="changeFavourite" data-favourite="NO"><i class="fas fa-heart"></i> Favourite </button>
                    	</c:otherwise>
                    </c:choose>
                    
                    <a href="${pageContext.request.contextPath}/App/DownloadPhoto?albumId=${album.id}&photoId=${photo.id}" class="btn btn-sm btn-link text-info"><i class="fas fa-download"></i> Download</a>
                </div>
            </div>
            <!-- Modal For Editing Photo -->
            <div class="modal fade" id="editPhotoModal" data-backdrop="static"
				tabindex="-1" role="dialog" aria-labelledby="EditPhotoModalLabel"
				aria-hidden="true" data-album-id="${album.id}" data-photo-id="${photo.id}">
				<div class="modal-dialog modal-dialog-centered" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="EditPhotoModalLabel">Edit Photo Details</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">

							<div class="form-group">
								<input type="text" class="form-control"
									placeholder="Photo Title" id="photoTitle" value="${photo.title}">
							</div>
							<div class="form-group">
								<textarea id="photoDescription" cols="30" rows="3"
									class="form-control" style="resize: none;"
									placeholder="Photo Description">${photo.description}</textarea>
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
			<!-- Modal For Deleting Photo -->
            <div class="modal fade" id="deletePhotoModal" data-backdrop="static"
				tabindex="-1" role="dialog" aria-labelledby="deletePhotoModalLabel"
				aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="deletePhotoModalLabel">Delete This Photo?</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<p class="text-muted">
								Are you really want to delete this photo? This is an unrecoverable action.
							</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-outline-secondary btn-sm"
								data-dismiss="modal">Cancel</button>
							<a href="${pageContext.request.contextPath}/App/DeletePhoto?albumId=${album.id}&photoId=${photo.id}" class="btn btn-sm btn-danger">Delete</a>
						</div>
					</div>
				</div>
			</div>
            <div class="row p-3">
                <div class="col-12">
                    <a href="#" class="btn btn-sm btn-primary float-left shadow">Previous</a>
                    <a href="#" class="btn btn-sm btn-primary float-right shadow">Next</a>
                </div>
            </div>
            <hr>
            <div class="row p-3">
                <div class="col-12">
                    <p class="h5">More Photos From This Album</p>
                </div>
            </div>
        </div>
    </section>
</body>