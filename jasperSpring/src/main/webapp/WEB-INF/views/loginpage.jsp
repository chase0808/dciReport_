<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>DCI Report</title>
		<!-- Bootstrap -->
		<link href="<c:url value = "/resources/bootstrap/css/bootstrap.min.css" />"  rel="stylesheet">
		<link href="<c:url value = "/resources/css/signin.css" />" rel="stylesheet">
		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>
	<body>
		
		<div class="container">
			<form:form class="form-signin" role="form" method="POST" action="/report/logining">
				<h2 class="form-signin-heading">Please sign in</h2>
				<c:if test="${not empty error}">
				<div class="alert alert-danger">
					
					${error} 
				</div>
				</c:if>

				<c:if test="${not empty logout}">
				<div class="alert alert-success">
					
					${logout}
				</div>
				</c:if>

				<form:input type="text"  class="form-control" placeholder="User Name"  path="username"  required = "required" />
				<form:input type="password"   class="form-control" placeholder="Password"  path="password"  required = "required"/>
						<label class="checkbox">
							<input type="checkbox" value="remember-me"> Remember me
						</label>
						<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
					</form:form>
				</div>
				<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
				<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
				<!-- Include all compiled plugins (below), or include individual files as needed -->
				<script src="bootstrap/js/bootstrap.min.js"></script>
			</body>
		</html>