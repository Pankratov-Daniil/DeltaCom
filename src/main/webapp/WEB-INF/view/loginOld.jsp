<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf8" language="java" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <link href="./resources/css/bootstrap.min.css" rel="stylesheet" />
    <link href="./resources/css/login.css" rel="stylesheet" />

    <title>Title</title>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <div class="login-form">
                    <form id="loginForm" action="tryToLogin" method="post" role="login" />
                        <img src="./resources/img/siteLogo.png" class="img-responsive"/>
                        <div class="form-group">
                            <input type="email" name="email" tabindex="1" class="form-control" placeholder="Email" value="">
                        </div>
                        <div class="form-group">
                            <input type="password" name="password" tabindex="2" class="form-control" placeholder="Password" value="">
                        </div>
                        <div class="row">
                            <div id="checkboxArea" class="col-md-6 form-group pull-left checkbox">
                                <input id="checkbox1" type="checkbox" name="remember">
                                <label id="checkboxLabel" for="checkbox1">Remember Me</label>
                            </div>
                            <div id="forgot" class="col-md-6">
                                <a  href="#">Forgot password?</a>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success">LOGIN</button>
                    </form>
                </div>
            </div>
            <div class="col-md-4"></div>
        </div>
    </div>
</body>
</html>
