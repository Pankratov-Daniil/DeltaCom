<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="./resources/css/bootstrap-template.min.css">
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Lato:300,400,400i,700">
    <link rel="stylesheet" type="text/css" href="./resources/css/NiconneFont.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="./resources/css/font-awesome.min.css">
    <script src="./resources/js/plugins/jquery-2.1.4.min.js"></script>
    <script src="./resources/js/plugins/bootstrap.min.js"></script>
    <script src="./resources/js/plugins/pace.min.js"></script>
    <script src="./resources/js/plugins/bootstrap-template.js"></script>
    <script src="../resources/js/plugins/bootstrap-notify.min.js"></script>
    <script src="./resources/js/process-login.js"></script>

    <title>My DeltaCom</title>
</head>
<body>
<section class="material-half-bg">
    <div class="cover"></div>
</section>
<section class="login-content">
    <div class="login-box">
        <form class="login-form" action="<c:url value="/login" />" method="post">
            <h3 class="login-head"><i class="fa fa-lg fa-fw fa-user"></i>SIGN IN</h3>
            <c:if test="${not empty param.error}">
                <p style="color:red;" align="center">
                    Entered email or password are incorrect.
                </p>
            </c:if>
            <div class="form-group">
                <label class="control-label">EMAIL</label>
                <input class="form-control" type="email" name="email" placeholder="Email" autofocus>
            </div>
            <div class="form-group">
                <label class="control-label">PASSWORD</label>
                <input class="form-control" type="password" name="password" placeholder="Password">
            </div>
            <div class="form-group">
                <div class="utility">
                    <div class="animated-checkbox">
                        <label class="semibold-text">
                            <input type="checkbox" name="staySigned"><span class="label-text">Stay Signed in</span>
                        </label>
                    </div>
                    <p class="semibold-text mb-0"><a data-toggle="flip">Forgot Password?</a></p>
                </div>
            </div>
            <div class="form-group btn-container">
                <button type="submit" class="btn btn-primary btn-block"><i class="fa fa-sign-in fa-lg fa-fw"></i>SIGN IN</button>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <form id="sendPasswordLinkForm" class="forget-form" action="<c:url value="/sendResetPasswordLink" />" method="post">
            <h3 class="login-head"><i class="fa fa-lg fa-fw fa-lock"></i>Forgot Password?</h3>
            <div class="form-group">
                <label class="control-label">EMAIL</label>
                <input class="form-control" name="email" type="email" id="sendPassEmail" placeholder="Email">
            </div>
            <div class="form-group btn-container">
                <button id="resetPassBtn" type="submit" class="btn btn-primary btn-block"><i class="fa fa-unlock fa-lg fa-fw"></i>RESET</button>
            </div>
            <div class="form-group mt-20">
                <p class="semibold-text mb-0"><a data-toggle="flip"><i id="backToLoginLink" class="fa fa-angle-left fa-fw"></i> Back to Login</a>
                </p>
            </div>
        </form>
    </div>
    <img src="./resources/img/siteLogo.png" class="img-responsive">
</section>
</body>

</html>
