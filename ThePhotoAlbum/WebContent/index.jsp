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
<import:stylesheet file="index.css"/>
<import:script file="index.js"></import:script>
</head>
<body>
	<comp:navigationbarIndex/>
	<comp:hero-section/>
	<!-- Login Section-->
	<section class="my-3">
		<div class="container shadow">
			<div class="row mx-2">
				<div class="col-lg-7 col-md-5 p-3">
					<import:image file="beach.jpg" cls="img-fluid"></import:image>
				</div>
				<div class="col-lg-5 col-md-7 p-3">
					<form action="${pageContext.request.contextPath}/Login"
						method="POST" class=" p-4 bg-light" id="login-form">
						<div class="form-group">
							<label for="email">Email:</label>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><i class="fas fa-user"></i></span>
								</div>
								<input type="text" class="form-control"
									placeholder="example@gmail.com" aria-label="Username"
									aria-describedby="basic-addon1" id="login-email" name="email" />
							</div>
						</div>
						<div class="form-group">
							<label for="login-password">Password:</label>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text" id="basic-addon1"><i
										class="fas fa-lock"></i></span>
								</div>
								<input type="password" class="form-control"
									placeholder="********" aria-label="Username"
									aria-describedby="basic-addon1" id="login-password"
									name="password" />
							</div>
						</div>
						<div class="custom-control custom-checkbox">
							<input type="checkbox" class="custom-control-input" id="remember"
								name="rememberme" value="YES" /> <label
								class="custom-control-label text-dark" for="remember">Remember
								Me</label>
						</div>
						<div class="form-group">
							<a href="#" class="btn-link">Forget Your Password?</a>
						</div>
						<div class="form-group">
							<input type="submit" class="btn btn-primary btn-block shadow-sm"
								value="Login" id="login-submit" />
						</div>
						<div class="form-group">
							<a href="#create-account-form"
								class="btn btn-success btn-block shadow-sm">Create Account</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>

	<!-- Cards Section -->
	<section class="my-3">
		<div class="container shadow">
			<div class="card-deck p-3">
				<div class="card">
					<div class="card-body">
						<p class="card-title h5">Free Account !</p>
						<hr />
						<p class="card-text text-left">You can create a absolutly free
							account to use The Photo Album</p>
						<a href="#" class="card-link">Learn More...</a>
					</div>
				</div>
				<div class="card">
					<div class="card-body">
						<p class="card-title h5">Unlimited Albums !</p>
						<hr />
						<p class="card-text text-left">You can oranganize your photos
							into unlimited numbers of album. Each album can contain unlimited
							photos</p>
						<a href="#" class="card-link">Learn More...</a>
					</div>
				</div>
				<div class="card">
					<div class="card-body">
						<p class="card-title h5">Unlimited Photo Uploads !</p>
						<hr />
						<p class="card-text text-left">Don't stop yourself to clicking
							photos because here you can upload unlimited photos.</p>
						<a href="#" class="card-link">Learn More...</a>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- Registration Section -->
	<section class="my-3">
		<div class="container shadow rounded">
			<div class="row">
				<div class="offset-lg-2 col-lg-8 offset-md-2 col-md-8 p-3"
					id="create-account-form">
					<div class="text-center h3 text-dark">Join Us Now, Create a
						Account</div>
					<form action="${pageContext.request.contextPath}/UserRegistration"
						class="border rounded p-3" id="registration-form" method="POST">
						<div class="form-row">
							<div class="form-group col-lg-12">
								<label for="">Full Name</label> <input type="text"
									class="form-control" placeholder="e.g. Shivam Singhal"
									id="reg-fullname" name="fullname" />
							</div>
						</div>
						<div class="form-row">
							<div class="col">
								<label for="">Email</label> <input type="text"
									class="form-control" placeholder="example@gmail.com"
									id="reg-email" name="email" />
							</div>
						</div>
						<div class="form-row">
							<div class="col-lg-6 my-2">
								<label for="">Birthday</label> <input type="date"
									class="form-control" placeholder="Birthday" id="reg-birthday"
									name="birthday" value="2019-01-01" />
							</div>
							<div class="col-lg-6 my-3">
								Gender <select class="custom-select" id="reg-gender"
									name="gender">
									<option selected value="MALE">Male</option>
									<option value="FEMALE">Female</option>
								</select>
							</div>
						</div>
						<div class="form-row">
							<div class="col-md-6 col-sm-12">
								<div class="form-group">
									<label for="">Password</label> <input type="password"
										name="password" class="form-control"
										placeholder="Enter Password" id="reg-password">
								</div>
							</div>
							<div class="col-md-6 col-sm-12">
								<div class="form-group">
									<label for="">Confirm Password</label> <input type="password"
										class="form-control" placeholder="Enter Password Again"
										id="reg-confirm-password">
								</div>
							</div>
						</div>
						<div class="form-row">
							<div class="col-lg-6 my-2">
								<input type="submit" class="form-control btn btn-sm btn-primary"
									value="Register" />
							</div>
							<div class="col-lg-6 my-2">
								<input type="reset" value="Reset"
									class="form-control btn btn-sm btn-secondary" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<comp:footer />
</body>
</html>