<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="./resources/css/main.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="./resources/css/font-awesome.min.css">

    <script src="./resources/js/jquery-2.1.4.min.js"></script>
    <script src="./resources/js/bootstrap.min.js"></script>
    <script src="./resources/js/plugins/pace.min.js"></script>
    <script src="./resources/js/main.js"></script>

    <title>My DeltaCom</title>
</head>
<body class="sidebar-mini fixed">
<div class="wrapper">
    <!-- Navbar-->
    <%@include file="./header.jsp" %>
    <!-- Side-Nav-->
    <%@include file="./sidebar.jsp" %>
    <div class="content-wrapper">
        <div class="page-title">
            <div>
                <h1><i class="fa fa-dashboard"></i> Blank Page</h1>
                <p>Start a beautiful journey here</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-body">Load Your Data Here</div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
