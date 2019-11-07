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
<import:script file="upload.js"></import:script>
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
				<main class="col-lg-9 col-md-8 order-lg-last order-first">
					<div class="container shadow rounded my-3">
						<div class="row p-2  shadow-sm bg-light border">
							<h5>Upload New Photos</h5>
						</div>
						<div class="row p-2">
							<div class="col">
								<h4>Select Album & Photos</h4>
							</div>
						</div>
						<div class="form-row p-3">
							<div class="col-md-6 col-sm-12 my-2">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fas fa-folder"></i>
										</div>
									</div>
									<select class="custom-select">
										<option selected>Mobile Uploads</option>
										<option value="1">Ruchika Singh</option>
										<option value="2">Ashwani Chauhan</option>
										<option value="3">Other</option>
									</select>
								</div>
							</div>
							<div class="col-md-6 col-sm-12 my-2">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fas fa-image"></i>
										</div>
									</div>
									<div class="custom-file">
										<input type="file" max="2" class="custom-file-input"
											id="photo_upload_input" multiple> <label
											class="custom-file-label" for="photo_upload_input">
											Pick Photos</label>
									</div>
								</div>
							</div>
						</div>
						<hr />
						<div class="row p-2">
							<div class="col">
								<h4>Images Preview</h4>
							</div>
						</div>
						<div class="row p-2">
							<div class="container" id="image-preview-container">
								<p class="h6 text-muted text-center">Select Images To See
									Preview.</p>
							</div>
						</div>
						<hr />
						<div class="row p-2">
							<div class="col">
								<h4>Upload</h4>
							</div>
						</div>
						<div class="row p-2 d-none" id="progressbar-div">
							<div class="col">
								<div class="progress" style="height: 10px;">
									<div
										class="progress-bar progress-bar-striped progress-bar-animated"
										style="width: 0%"></div>
								</div>
							</div>
						</div>
						<div class="row p-2">
							<div class="offset-3 col-6">
								<button class="btn btn-success btn-sm btn-block"
									id="upload-button">Start Upload</button>
							</div>
						</div>
					</div>
				</main>
			</div>
		</div>
	</section>
</body>