<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="../resources/css/main.min.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="../resources/css/font-awesome.min.css">

    <script src="../resources/js/jquery-2.1.4.min.js"></script>
    <script src="../resources/js/bootstrap.min.js"></script>
    <script src="../resources/js/plugins/pace.min.js"></script>
    <script src="../resources/js/main.js"></script>
    <script src="../resources/js/newContract.js"></script>
    <script src="../resources/js/plugins/bootstrap-notify.min.js"></script>

    <script type="text/javascript" src="../resources/js/bootstrap-select.min.js"></script>
    <link rel="stylesheet" href="../resources/css/bootstrap-select.min.css" type="text/css"/>

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
                <h1><i class="fa fa-dashboard"></i> Add new contract</h1>
                <p>Here you can add contract for client</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="card">
                    <form method="post" action="regNewContract" name="newContract" acceptCharset="utf8">
                        <div class="form-group">
                            <label class="control-label">Avaivable numbers</label><br/>
                            <select class="selectpicker form-control" data-style="btn-primary" id="selectNumber" name="selectNumber">
                                <c:forEach items="${unusedNumbers}" var="unusedNumber">
                                    <option value="${unusedNumber}">${unusedNumber}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="control-label" >Avaivable tariffs</label><br/>
                            <select class="selectpicker form-control" data-style="btn-primary" id="selectTariff" name="selectTariff">
                                <c:forEach items="${availableTariffs}" var="availableTariff">
                                    <option data-tariff-price="${availableTariff.price}" value="${availableTariff.id}">${availableTariff.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="control-label" >Avaivable options</label><br/>
                            <select class="selectpicker form-control" multiple data-style="btn-primary" id="selectOptions" name="selectOptions">

                            </select>
                        </div>
                        <br/>
                        <div class="form-group">
                            <button class="btn btn-success" type="submit">Submit</button>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                    </form>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h3>Selected tariff info</h3>
                        <div id="tariffInfo" class="card-footer">

                        </div>

                        <h3>Avaivable options</h3>
                        <div id="availableOptions" class="card-footer">
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
