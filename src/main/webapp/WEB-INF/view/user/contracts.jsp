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
                <h1><i class="fa fa-mobile"></i> Contracts page</h1>
                <p>Here you can manage your contracts.</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <h3 class="card-title">Contracts</h3>
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Number</th>
                            <th>Tariff</th>
                            <th>Balance</th>
                            <th>Blocked</th>
                            <th>Blocked by operator</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${clientContracts}" var="contract">
                            <tr>
                                <td>${contract.id}</td>
                                <td>${contract.numbersPool}</td>
                                <td>${contract.tariff}</td>
                                <td>${contract.balance}</td>
                                <td>${contract.blocked}</td>
                                <td>${contract.blockedByOperator}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
