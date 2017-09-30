<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=utf8" language="java" %>
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
            <div class="col-xs-6">
                <div class="panel panel-login">
                    <div class="panel-body">
                        <div class="col-lg-12">
                            <form id="loginForm" role="form">
                                <h2>Login</h2>
                                <div class="form-group">
                                    <input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password">
                                </div>
                                <div class="col-xs-6 form-group pull-left checkbox">
                                    <input id="checkbox1" type="checkbox" name="remember">
                                    <label for="checkbox1">Remember Me</label>
                                </div>
                                <div class="col-xs-6 form-group pull-right">
                                    <a href="#">Forgot password?</a>
                                </div>
                                <button type="button" class="btn btn-success">LOGIN</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
