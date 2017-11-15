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
    <script src="../resources/js/commonFunctions.js"></script>
    <script src="../resources/js/manager/numbersPoolActions.js"></script>
    <script src="../resources/js/plugins/bootstrap-template.js"></script>
    <script src="../resources/js/plugins/bootstrap-notify.min.js"></script>
    <script src="../resources/js/plugins/bootstrap-select.min.js"></script>
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
                        <h3 class="title"><i class="fa fa-list-ol"></i> Numbers</h3>
                        <p><a id="addNewNumberBtn" class="btn btn-primary icon-btn" href="#"><i class="fa fa-plus"></i>Add number</a></p>
                    </div>
                    <div class="card-footer"></div>
                    <div id="numbersHolder" class="card-body">

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="addNumberModal" class="modal fade" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="showMe modal-title">Add number</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div id="modalColumns" class="col-md-12">
                            <div class="card">
                                <form id="addNumber">
                                    <div class="form-group">
                                        <label class="control-label" for="number">Number</label><br/>
                                        <input class="form-control" type="text" name="number" id="number" required pattern="\d+" value="" />
                                    </div>
                                    <div class="form-group">
                                        <button id="submitBtn" class="btn btn-success" type="submit">Submit</button>
                                    </div>
                                </form>
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
