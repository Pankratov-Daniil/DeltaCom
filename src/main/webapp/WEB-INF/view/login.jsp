<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=utf8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <spring:url value="resources/css/bootstrap.css" var="bootstrap"/>
    <link href="${bootstrap}" rel="stylesheet" />

    <title>Title</title>
</head>
<body class="login2background">
<div class="container">
    <div class="col-lg-6 col-md-6 col-sm-8  loginbox">
        <div class=" row">
            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-6">
                <img src="https://s4.postimg.org/64q1bgunx/logo_3.png" alt="Logo" class="logo">
            </div>
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-6  ">
                <span class="singtext" >Sign in </span>
            </div>

        </div>
        <div class=" row loginbox_content ">
            <div class="input-group input-group-sm" >
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-user"></span>
                        </span>
                <input class="form-control" type="text" placeholder="User name"  >
            </div>
            <br>
            <div class="input-group input-group-sm">
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-lock"></span>
                        </span>
                <input class="form-control" type="password" placeholder="Password" >
            </div>
        </div>
        <div class="row ">
            <div class="col-lg-8 col-md-8  col-sm-8 col-xs-7 forgotpassword ">
                <a href="#"  > Forgot Username / Password?</a>
            </div>
            <div class="col-lg-4 col-md-4 col-sm-4  col-xs-5 ">
                <a href="#" class=" btn btn-default submit-btn">Submit <span class="glyphicon glyphicon-log-in"></span> </a>
            </div>
        </div>
    </div>
    <div class="col-lg-6 col-md-6 col-sm-4 "></div>


</div>
</body>
</html>
