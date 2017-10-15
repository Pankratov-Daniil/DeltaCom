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
    <script src="../resources/js/commonFunctions.js"></script>
    <script src="../resources/js/userContracts.js"></script>
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
                <h1><i class="fa fa-mobile"></i> Contracts page</h1>
                <p>Here you can manage your contracts.</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <h3 class="card-title">Contracts</h3>
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

    <div id="changeContractModal" class="modal fade" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Manage contract</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="card">
                                <form action="changeContract" id="changeContract" name="changeContract" acceptCharset="utf8">
                                    <div class="form-group">
                                        <label class="control-label">Selected number</label><br/>
                                        <input class="btn btn-primary" type="text" name="numberModal" id="numberModal" disabled value="" />
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label" >Avaivable tariffs</label><br/>
                                        <select class="selectpicker form-control" data-style="btn-primary" id="selectTariff" name="selectTariff">

                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label" >Avaivable options</label><br/>
                                        <select class="selectpicker form-control" multiple data-style="btn-primary" id="selectOptions" required name="selectOptions">

                                        </select>
                                    </div>
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
                                    <h3>Currect tariff info</h3>
                                    <div id="curTariff" class="card-footer">

                                    </div>

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
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
