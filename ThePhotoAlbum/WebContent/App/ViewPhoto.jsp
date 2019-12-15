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
<import:stylesheet file="index.css" />
</head>
<body>
	<comp:navigationBarUser />
	 <section id="photo_view">
        <div class="container-fluid shadow-sm my-3">
            <div class="row p-3" id="img-view-row">
                <div class="col d-flex justify-content-center">
                    <img src="${pageContext.request.contextPath}/App/GetPhoto?albumId=${photo.albumId}&photoId=${photo.id}" alt="">
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
                    <button class="btn btn-sm btn-link"><i class="fas fa-edit"></i> Edit</button>
                    <a href="#" class="btn btn-sm btn-link text-secondary"><i class="fas fa-trash"></i> Delete</a>
                    <button class="btn btn-sm btn-link text-danger"><i class="fas fa-heart"></i> Favourite</button>
                    <a href="${pageContext.request.contextPath}/App/DownloadPhoto?albumId=${album.id}&photoId=${photo.id}" class="btn btn-sm btn-link text-info"><i class="fas fa-download"></i> Download</a>
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