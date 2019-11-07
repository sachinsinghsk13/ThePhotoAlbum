<%@ tag body-content="empty" %>
<%@ attribute name="username" required="true"  rtexprvalue="true"%>
<div class="container my-3 shadow-sm border rounded">
	<div class="row p-2  shadow-sm bg-light border-bottom">
		<div class="col text-center">
			<h5>
				<i class="fas fa-cog"></i> Dashboard
			</h5>
		</div>
	</div>
	<div class="row p-2">
		<ul class="nav flex-column">
			<li class="nav-item"><a href="#" class="nav-link text-dark">
					<i class="fas fa-user"></i> My Account
			</a></li>
			<li class="nav-item"><a href="${pageContext.request.contextPath }/App/UploadPhoto.jsp" class="nav-link text-dark">
					<i class="fas fa-upload"></i> Upload Photos
			</a></li>
			<li class="nav-item"><a href="#" class="nav-link text-dark">
					<i class="fas fa-sliders-h"></i> Settings
			</a></li>
			<li class="nav-item"><a href="#" class="nav-link text-dark">
					<i class="fas fa-upload"></i> Favourites
			</a></li>
			<li class="nav-item"><a href="#" class="nav-link text-dark">
					<i class="fas fa-info-circle"></i> Notifications <span
					class="badge badge-secondary">3</span>
			</a></li>
			<li class="nav-item"><a href="#" class="nav-link text-dark">
					<i class="fas fa-question-circle"></i> Help
			</a></li>
			<li class="nav-item"><a href="${pageContext.request.contextPath }/Logout" class="nav-link text-dark">
					<i class="fas fa-sign-out-alt"></i> Logout (${username })
			</a></li>
		</ul>
	</div>
</div>