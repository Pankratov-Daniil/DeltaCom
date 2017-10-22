<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="../resources/css/main.min.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="../resources/css/font-awesome.min.css">

    <script src="../resources/js/jquery-2.1.4.min.js"></script>
    <script src="../resources/js/bootstrap.min.js"></script>
    <script src="../resources/js/plugins/pace.min.js"></script>
    <script src="../resources/js/main.js"></script>
    <script src="../resources/js/plugins/bootstrap-notify.min.js"></script>

    <title>My DeltaCom</title>
</head>
<body class="sidebar-mini fixed">
<div class="wrapper">
    <!-- Navbar-->
    <%@include file="../header.jsp" %>
    <!-- Side-Nav-->
    <%@include file="../sidebar.jsp" %>
    <div class="content-wrapper">
        <div class="page-title">
            <div>
                <h1><i class="fa fa-dashboard"></i> Overview</h1>
                <p>Have a beautiful day!</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <a style="color: #000;" href="browseAllClients">
                                <div class="col-md-4">
                                    <div class="card">
                                        <h3 class="card-title"><i class="fa fa-mobile"></i> Contracts</h3>
                                        <div class="card-footer">
                                            <p>
                                                Manage your contracts:
                                            <ul>
                                                <li>Change your contract</li>
                                                <li>Block contract</li>
                                            </ul>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="userCart.jsp" %>
</div>
</body>
</html>
