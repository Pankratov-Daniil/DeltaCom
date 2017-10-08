<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="../resources/css/main.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="../resources/css/font-awesome.min.css">

    <script src="../resources/js/jquery-2.1.4.min.js"></script>
    <script src="../resources/js/bootstrap.min.js"></script>
    <script src="../resources/js/plugins/pace.min.js"></script>
    <script src="../resources/js/main.js"></script>
    <script src="../resources/js/plugins/bootstrap-notify.min.js"></script>

    <script type="text/javascript" src="../resources/js/bootstrap-select.min.js"></script>
    <link rel="stylesheet" href="../resources/css/bootstrap-select.min.css" type="text/css"/>

    <title>My DeltaCom</title>
</head>
<body class="sidebar-mini fixed">
<div class="wrapper">
    <!-- Navbar-->
    <header class="main-header hidden-print">
        <div class="logo"><img src="../resources/img/siteLogoPages.png" class="img-responsive"></div>
        <nav class="navbar navbar-static-top">
            <!-- Sidebar toggle button--><a class="sidebar-toggle" href="#" data-toggle="offcanvas"></a>
            <!-- Navbar Right Menu-->
            <div class="navbar-custom-menu">
                <ul class="top-nav">
                    <!-- User Menu-->
                    <li class="dropdown"><a class="dropdown-toggle" href="#" data-toggle="dropdown" role="button"
                                            aria-haspopup="true" aria-expanded="false"><i class="fa fa-user fa-lg"></i></a>
                        <ul class="dropdown-menu settings-menu">
                            <li><a href="page-user.html"><i class="fa fa-cog fa-lg"></i> Settings</a></li>
                            <li><a href="page-user.html"><i class="fa fa-user fa-lg"></i> Profile</a></li>
                            <li>
                                <a onclick="logoutForm.submit();">
                                    <c:url var="logoutUrl" value="/logout"/>
                                    <form id="logoutForm" class="form-inline" action="${logoutUrl}" method="post">
                                        <i class="fa fa-sign-out fa-lg"></i>Logout
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </form>
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <!-- Side-Nav-->
    <aside class="main-sidebar hidden-print">
        <section class="sidebar">
            <%@include file="../sidebar.jsp" %>
        </section>
    </aside>
    <div class="content-wrapper">
        <div class="page-title">
            <div>
                <h1><i class="fa fa-user-plus"></i> Add new client page</h1>
                <p>Here you can register new user</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
            </div>
            <div class="col-md-4">
                <div class="card">
                    <h3 class="card-title">Register</h3>
                    <div class="card-body">
                        <form method="post" action="regNewClient" name="newUser" acceptCharset="utf8">
                            <div class="form-group">
                                <label class="control-label">First name</label>
                                <input class="form-control" type="text" placeholder="Enter first name" name="firstName">
                            </div>
                            <div class="form-group">
                                <label class="control-label">Last name</label>
                                <input class="form-control" type="text" placeholder="Enter last name" name="lastName">
                            </div>
                            <div class="form-group">
                                <label class="control-label">Birth date</label>
                                <input class="form-control" type="date" name="birthDate">
                            </div>
                            <div class="form-group">
                                <label class="control-label">Passport</label>
                                <input class="form-control" type="text" placeholder="Enter passport" name="passport">
                            </div>
                            <div class="form-group">
                                <label class="control-label">Address</label>
                                <textarea class="form-control" rows="2" placeholder="Enter address" name="address"></textarea>
                            </div>
                            <div class="form-group">
                                <label class="control-label">Email</label>
                                <input class="form-control" type="email" placeholder="Enter email address" name="email">
                            </div>
                            <div class="form-group">
                                <label class="control-label">Password</label>
                                <input class="form-control" type="password" placeholder="Enter password" name="password">
                            </div>
                            <div class="form-group">
                                <sec:authorize access="hasRole('ADMIN')">
                                    <label class="control-label">Access level</label><br/>

                                    <select class="selectpicker form-control" multiple data-style="btn-primary" id="selectAccessLevel" name="accessLevelsSelect">
                                        <c:forEach items="${accessLevels}" var="accessLevel">
                                            <option value="${accessLevel.id}">${accessLevel.name}</option>
                                        </c:forEach>
                                    </select>
                                </sec:authorize>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-success" type="submit">Submit</button>
                                <button class="btn btn-default" type="reset">Clear form</button>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}"
                                   value="${_csrf.token}"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
