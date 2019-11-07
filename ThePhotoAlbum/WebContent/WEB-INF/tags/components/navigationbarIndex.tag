<%@ tag body-content="empty" %>

<nav
	class="navbar navbar-expand-lg navbar-light bg-light shadow-sm sticky-top">
	<a href="${pageContext.request.contextPath}" class="navbar-brand" id="logo-text">
      <img src="${pageContext.request.contextPath}/static/images/logo.png" alt="logo">
      MyPhotoAlbum
    </a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarmenu" aria-expanded="false"
      aria-controls="#navbarmenu">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse ml-5" id="navbarmenu">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
          <a href="#" class="nav-link"><i class="fas fa-home"></i> Home</a>
        </li>
        <li class="nav-item">
          <a href="#" class="nav-link"><i class="fas fa-info-circle"></i> About</a>
        </li>
        <li class="nav-item">
          <a href="#" class="nav-link"><i class="fas fa-phone"></i> Contact Us</a>
        </li>
      </ul>
      <div class="ml-auto">
        <a href="#login-section" class="btn btn-primary mx-1">Login</a>
        <a href="#registration-section" class="btn btn-outline-success mx-1">Register</a>
      </div>
    </div>
</nav>