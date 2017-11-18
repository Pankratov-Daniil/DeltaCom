<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
    <script src="./resources/js/preAuth.js"></script>

    <title>My DeltaCom</title>
</head>
<body>
<section class="material-half-bg">
    <div class="cover"></div>
</section>
<section class="login-content">
    <div class="login-box">
        <form id="checkCodeForm" action="<c:url value="/checkPreAuthCode" />" method="post" class="login-form">
            <h3 class="login-head">ENTER CODE FROM SMS</h3>
            <div class="form-group">
                <label class="control-label">CODE</label>
                <input class="form-control" type="text" id="code" name="code" placeholder="Sms code" required pattern=".{4,4}">
            </div>
            <div class="form-group btn-container">
                <button id="checkCodeBtn" class="btn btn-primary btn-block" type="submit"><i class="fa fa-sign-in fa-lg fa-fw"></i>ENTER</button>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
    <img src="./resources/img/siteLogo.png" class="img-responsive">
</section>
</body>

</html>
