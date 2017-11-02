<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="../resources/css/bootstrap-template.min.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="../resources/css/font-awesome.min.css">

    <script src="../resources/js/jquery-2.1.4.min.js"></script>
    <script src="../resources/js/bootstrap.min.js"></script>
    <script src="../resources/js/plugins/pace.min.js"></script>
    <script src="../resources/js/bootstrap-template.js"></script>
    <script src="../resources/js/commonFunctions.js"></script>
    <script src="../resources/js/browseAllClients.js"></script>
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
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-title-w-btn">
                        <h3 class="title"><i class="fa fa-users"></i> Clients</h3>
                        <p><a id="addNewClientBtn" class="btn btn-primary icon-btn" href="#"><i class="fa fa-plus"></i>Add client</a></p>
                    </div>
                    <div class="card-footer"></div>
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
                                    <li class="disabled"><a href="javascript:void(0);" id="prevButton">Previous</a></li>
                                    <li class="disabled"><a href="javascript:void(0);" id="nextButton">Next</a></li>
                                </ul>
                            </div>

                            <div id="manageContractModal" class="modal fade" role="dialog">
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
                                                        <form id="changeContract" acceptCharset="utf8">
                                                            <div class="form-group">
                                                                <label class="control-label">Selected number</label><br/>
                                                                <input class="btn btn-primary hideOnCreateContract" type="text" name="numberModal" id="numberModal" readonly="true" value="" />
                                                                <select class="selectpicker form-control showOnCreateContract" data-style="btn-primary" id="selectNumber" name="selectNumber" required>

                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label class="control-label" >Available tariffs</label><br/>
                                                                <select class="selectpicker form-control" data-style="btn-primary" id="selectTariff" name="selectTariff">

                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label class="control-label" >Available options</label><br/>
                                                                <select class="selectpicker form-control" multiple data-style="btn-primary" id="selectOptions" required name="selectOptions">

                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <button id="applyContractBtn" class="btn btn-success" type="submit">Submit</button>
                                                            </div>
                                                            <input type="hidden" name="${_csrf.parameterName}"
                                                                   value="${_csrf.token}"/>
                                                        </form>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="card">
                                                        <div class="card-body">
                                                            <div class="hideOnCreateContract">
                                                                <h3>Current tariff info</h3>
                                                                <div id="curTariff" class="card-footer">

                                                                </div>
                                                            </div>
                                                            <h3>Selected tariff info</h3>
                                                            <div id="tariffInfo" class="card-footer">

                                                            </div>

                                                            <h3>Available options</h3>
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
                            <div id="addNewClientModal" class="modal fade" role="dialog">
                                <div class="modal-dialog modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title">Add new client</h4>
                                        </div>
                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="card">
                                                        <h3 class="card-title">Add new user</h3>
                                                        <div class="card-body">
                                                            <form method="post" action="regNewClient" id="newUserForm" acceptCharset="utf8">
                                                                <div class="form-group">
                                                                    <label class="control-label">First name</label>
                                                                    <input class="form-control" type="text" placeholder="Enter first name" id="firstNameField" required pattern="[A-Za-zА-Яа-яЁё]+">
                                                                </div>
                                                                <div class="form-group">
                                                                    <label class="control-label">Last name</label>
                                                                    <input class="form-control" type="text" placeholder="Enter last name" id="lastNameField" required pattern="[A-Za-zА-Яа-яЁё]+">
                                                                </div>
                                                                <div class="form-group">
                                                                    <label class="control-label">Birth date</label>
                                                                    <input class="form-control" type="date" id="birthDateField" required>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label class="control-label">Passport</label>
                                                                    <input class="form-control" type="text" placeholder="Enter passport" id="passportField" required>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label class="control-label">Address</label>
                                                                    <textarea class="form-control" rows="2" placeholder="Enter address" id="addressField" required></textarea>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label class="control-label">Email</label>
                                                                    <input class="form-control" type="email" placeholder="Enter email address" id="emailField" required>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label class="control-label">Password</label>
                                                                    <input class="form-control" type="password" placeholder="Enter password" id="passwordField" required pattern=".{6,}">
                                                                </div>
                                                                <div class="form-group">
                                                                    <button id="submitNewUser" class="btn btn-success" type="submit">Submit</button>
                                                                    <button id="resetNewUser" class="btn btn-default" type="reset">Clear form</button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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
