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
    <script src="../resources/js/plugins/bootstrap-notify.min.js"></script>
    <script src="../resources/js/client/settings.js"></script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAer_p-x4DHMiDxSu6Ns1MtTTF3Zo0en04&callback=initMap">
    </script>

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
                <p>Have a beautiful day!</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <h2>Settings</h2>
                    <div class="card-body">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#passwordChange" data-toggle="tab" aria-expanded="true">Change password</a>
                            </li>
                            <li>
                                <a href="#clientLocations" data-toggle="tab" aria-expanded="true">Locations</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane fade active in" id="passwordChange">
                                <br/>
                                <form id="changePasswordForm">
                                    <div class="form-group">
                                        <label class="control-label" for="oldPassword">Enter current password</label><br/>
                                        <input class="form-control" type="password" id="oldPassword" required pattern=".{6,}" value="" />
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label" for="newPassword">Enter new password</label><br/>
                                        <input class="form-control" type="password" id="newPassword" required pattern=".{6,}" value="" />
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label" for="confirmPassword">Enter new password again</label><br/>
                                        <input class="form-control" type="password" id="confirmPassword" required pattern=".{6,}" value="" />
                                    </div>
                                    <div class="form-group">
                                        <button id="submitBtn" class="btn btn-success" type="submit">Submit</button>
                                        <button class="btn btn-default" type="reset">Clear form</button>
                                    </div>
                                </form>
                            </div>
                            <div class="tab-pane fade" id="clientLocations">
                                <div class="row">
                                    <div class="col-md-5">
                                        <table class="table table-hover table-responsive">
                                            <thead>
                                                <tr>
                                                    <th>IP</th>
                                                    <th>Country</th>
                                                    <th>City</th>
                                                    <th>Date</th>
                                                </tr>
                                            </thead>
                                            <tbody id="locationsTableBody">

                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="col-md-7" id="map">

                                    </div>
                                </div>
                            </div>
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
