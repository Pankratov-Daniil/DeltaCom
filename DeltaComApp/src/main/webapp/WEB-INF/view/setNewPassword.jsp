<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="token" content="${token}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="./resources/css/bootstrap-template.min.css">
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Lato:300,400,400i,700">
    <link rel="stylesheet" type="text/css" href="./resources/css/NiconneFont.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="./resources/css/font-awesome.min.css">
    <script src="./resources/js/jquery-2.1.4.min.js"></script>
    <script src="./resources/js/bootstrap.min.js"></script>
    <script src="./resources/js/plugins/pace.min.js"></script>
    <script src="./resources/js/bootstrap-template.js"></script>
    <script src="../resources/js/plugins/bootstrap-notify.min.js"></script>
    <script src="./resources/js/resetPassword.js"></script>

    <title>My DeltaCom</title>
</head>
<body>
<section class="material-half-bg">
    <div class="cover"></div>
</section>
<section class="login-content">
    <div class="login-box">
        <div class="login-form">
            <h3 class="login-head">ENTER NEW PASSWORD</h3>
            <div class="form-group">
                <label class="control-label">PASSWORD</label>
                <input class="form-control" type="password" id="password" placeholder="Password" required pattern=".{6,}">
            </div>
            <div class="form-group btn-container">
                <button id="changePassBtn" class="btn btn-primary btn-block"><i class="fa fa-sign-in fa-lg fa-key"></i>CHANGE PASSWORD</button>
            </div>
        </div>
    </div>
    <img src="./resources/img/siteLogo.png" class="img-responsive">
</section>
</body>

</html>
