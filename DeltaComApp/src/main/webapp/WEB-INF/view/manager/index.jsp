<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="../resources/css/bootstrap-template.min.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="../resources/css/font-awesome.min.css">

    <script src="../resources/js/plugins/jquery-2.1.4.min.js"></script>
    <script src="../resources/js/plugins/bootstrap.min.js"></script>
    <script src="../resources/js/plugins/pace.min.js"></script>
    <script src="../resources/js/plugins/bootstrap-template.js"></script>
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
                <h1><i class="fa fa-dashboard"></i> Overview</h1>
                <p>You can do many beautiful things today!</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <a style="color: #000;" href="browseAllClients">
                                <div class="col-md-3">
                                    <div class="card">
                                        <h3 class="card-title"><i class="fa fa-users"></i> Clients</h3>
                                        <div class="card-footer">
                                            <p>
                                                Manage our clients:
                                            <ul>
                                                <li>Add or delete clients</li>
                                                <li>Add, edit, delete or block contracts</li>
                                                <li>Search clients by number</li>
                                            </ul>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                            <a style="color: #000;" href="tariffsActions">
                                <div class="col-md-3">
                                    <div class="card">
                                        <h3 class="card-title"><i class="fa fa-mobile"></i> Tariffs</h3>
                                        <div class="card-footer">
                                            <p>
                                                Manage tariffs:
                                            <ul>
                                                <li>Add new tariff</li>
                                                <li>Edit old tariff</li>
                                                <li>Delete useless tariff</li>
                                            </ul>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                            <a style="color: #000;" href="optionsActions">
                                <div class="col-md-3">
                                    <div class="card">
                                        <h3 class="card-title"><i class="fa fa-filter"></i> Options</h3>
                                        <div class="card-footer">
                                            <p>
                                                Manage options:
                                            <ul>
                                                <li>Add, edit or delete options</li>
                                                <li>Configure their compatibility</li>
                                            </ul>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                            <a style="color: #000;" href="numberPoolActions">
                                <div class="col-md-3">
                                    <div class="card">
                                        <h3 class="card-title"><i class="fa fa-list-ol"></i> Numbers pool</h3>
                                        <div class="card-footer">
                                            <p>
                                                Manage numbers pool:
                                            <ul>
                                                <li>Add new number</li>
                                                <li>Delete useless number</li>
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
</div>
</body>
</html>
