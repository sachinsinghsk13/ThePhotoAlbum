<%@ tag body-content="empty" %>
<nav
	class="navbar navbar-expand-lg navbar-light bg-light shadow-sm sticky-top">
	<a href="${pageContext.request.contextPath}/App/Home.jsp" class="navbar-brand" id="logo-text"> <img
		src="${pageContext.request.contextPath}/static/images/logo.png" alt="" /> MyPhotoAlbum
	</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarmenu" aria-expanded="false"
		aria-controls="#navbarmenu">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse ml-5" id="navbarmenu">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a href="#" class="nav-link"><i
					class="fas fa-home"></i> Home</a></li>
			<li class="nav-item"><a href="#" class="nav-link"><i
					class="fas fa-image"></i> Gallery</a></li>
			<li class="nav-item"><a href="#" class="nav-link"
				title="View All Your Albums"><i class="fas fa-images"></i>
					Albums</a></li>
			<li class="nav-item"><a href="#" class="nav-link"><i
					class="fas fa-upload"></i> Recent Uploads</a></li>
		</ul>
		<ul class="navbar-nav ml-auto">
			<li class="nav-item"><a href="#" class="nav-link"> <i
					class="fas fa-envelope"></i> Contact Us
			</a></li>
			<li class="nav-item"><a href="#" class="nav-link"><i
					class="fas fa-info-circle"></i> About Us</a></li>
		</ul>
	</div>
</nav>