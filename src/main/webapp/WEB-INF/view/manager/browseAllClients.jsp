<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="../resources/css/main.min.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="../resources/css/font-awesome.min.css">

    <script src="../resources/js/jquery-2.1.4.min.js"></script>
    <script src="../resources/js/bootstrap.min.js"></script>
    <script src="../resources/js/plugins/pace.min.js"></script>
    <script src="../resources/js/main.js"></script>
    <script src="../resources/js/commonFunctions.js"></script>
    <script src="../resources/js/browseAllClients.js"></script>

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
                <h1><i class="fa fa-users"></i> Browse all clients</h1>
                <p>Here you can browse all clients</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-body">
                        <div>
                            Show
                                <select id="countEntries">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                </select>
                                entries
                            <div class="col-md-2  pull-right">
                                <form id="searchNumberForm">
                                    <div class=" input-group input-group-sm">
                                        <input class="form-control" type="text" id="numberForSearch" name="numberForSearch" pattern="\d+"><span class="input-group-btn">
                                        <button class="btn btn-default" type="button" id="startSearchByNumber"><i class="fa fa-search"></i></button>
                                        <button class="btn btn-default" type="button" id="resetFindUserByNumber"><i class="fa fa-times"></i></button></span>
                                        <button type="submit" class="hidden"></button>
                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}"/>
                                    </div>
                                </form>
                            </div>
                            <label class="control-label pull-right">Search by number:</label>
                        </div>
                        <br/><br/>
                        <div>
                            <table class="table table-hover" id="clientsTable">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Name</th>
                                        <th>Birth date</th>
                                        <th>Passport</th>
                                        <th>Address</th>
                                        <th>Email</th>
                                        <th>Contracts</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody id="tableBody">

                                </tbody>
                            </table>
                            <div class="bs-component">
                                <ul class="pager">
                                    <li class=""><a href="javascript:void(0);" id="prevButton">Previous</a></li>
                                    <li class=""><a href="javascript:void(0);" id="nextButton">Next</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
