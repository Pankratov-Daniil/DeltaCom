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
    <link rel="stylesheet" type="text/css" href="../resources/css/bootstrap-template.min.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="../resources/css/font-awesome.min.css">

    <script src="../resources/js/plugins/jquery-2.1.4.min.js"></script>
    <script src="../resources/js/plugins/bootstrap.min.js"></script>
    <script src="../resources/js/plugins/pace.min.js"></script>
    <script src="../resources/js/plugins/bootstrap-template.js"></script>
    <%@include file="userCart.jsp" %>
    <script src="../resources/js/client/userContracts.js"></script>
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
        <div class="page-title page-title-xs">
            <div>
                <h1><i class="fa fa-mobile"></i> Contracts page</h1>
                <p>Here you can manage your contracts.</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <h3 class="card-title">Contracts</h3>
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>Number</th>
                                    <th>Tariff</th>
                                    <th>Options</th>
                                    <th>Balance</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody id="contractsTableBody">

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
