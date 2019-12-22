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
<title>Verify OTP</title>
<import:bootstrap4_3 />
<import:stylesheet file="index.css"/>
</head>
<body>
	<comp:navigationbarIndex/>

	<!--Optional Alerts -->
	<c:if test="${fn:length(alerts) gt 0}">
		<section class="my-3">
			<div class="container">
				<c:forEach var="a" items="${alerts}">
					<div class="alert alert-${a.type}">${a.message}</div>
				</c:forEach>
			</div>
		</section>
	</c:if>
	<section class="my-3">
		<div class="container shadow-sm">
			<div class="row">
				<div class="offset-lg-2 col-lg-8 offset-md-2 col-md-8 p-3"
					id="create-account-form">
					<div class="m-2 p-2 text-center">
						<h4>
							${newUser.user.name}, An OTP has been sent to <b>${newUser.user.email},</b>
							Check Your Email Now.
						</h4>
						<form action="${pageContext.request.contextPath}/OTPVerification"
							class="p-3 bg-light" method="POST">
							<div class="form-row">
								<div class="col-md-6 offset-md-3">
									<div class="form-group">
										<label for="">Enter OTP</label>
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text"> <i
													class="fas fa-lock"></i>
												</span>
											</div>
											<input type="text" class="form-control"
												placeholder="e.g. 34535" name="otp" />
										</div>
									</div>
								</div>
							</div>
							<div class="form-row my-2">
								<div class="offset-md-3 col-md-6">
									<input type="submit" class="form-control btn btn-primary"
										value="Verify OTP" />
								</div>
							</div>
							<div class="form-row my-2">
								<div class="offset-md-3 col-md-6">
									<a href="${pageContext.request.contextPath}/index.jsp"
										class="btn btn-outline-secondary btn-block">Cancel
										Registration</a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
	<comp:footer />
</body>
</html>