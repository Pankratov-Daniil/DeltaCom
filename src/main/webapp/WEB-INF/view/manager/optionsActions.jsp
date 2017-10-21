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
    <link rel="stylesheet" type="text/css" href="../resources/css/main.min.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="../resources/css/font-awesome.min.css">

    <script src="../resources/js/jquery-2.1.4.min.js"></script>
    <script src="../resources/js/bootstrap.min.js"></script>
    <script src="../resources/js/plugins/pace.min.js"></script>
    <script src="../resources/js/commonFunctions.js"></script>
    <script src="../resources/js/optionsActions.js"></script>
    <script src="../resources/js/main.js"></script>
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
        <%--<div class="page-title">--%>
            <%--<div>--%>
                <%--<h1><i class="fa fa-filter"></i> Options actions</h1>--%>
                <%--<p>Here you can manage options: add, delete, edit</p>--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                        <div class="card-title-w-btn">
                            <h3 class="title"><i class="fa fa-filter"></i> Options</h3>
                            <p><a id="addNewOptionBtn" class="btn btn-primary icon-btn" href="#"><i class="fa fa-plus"></i>Add option</a></p>
                        </div>
                    <div class="card-footer"></div>
                    <div id="optionsHolder" class="card-body">

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="changeOptionModal" class="modal fade" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="hideMe modal-title">Change option</h4>
                    <h4 class="showMe modal-title hidden">Add option</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div id="modalColumns" class="col-md-6">
                            <div class="card">
                                <form action="changeOption" id="updatedOption" name="newOption">
                                    <div class="hidden"><input type="text" name="id" id="idOption" value="" /></div>
                                    <div class="form-group">
                                        <label class="control-label" for="nameOption">Name</label><br/>
                                        <input class="form-control" type="text" name="name" id="nameOption" required value="" />
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label" for="priceOption">Price</label><br/>
                                        <input class="form-control" type="text" name="price" id="priceOption" required pattern="\d+" value="" />
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label" for="connCostOption">Connection cost</label><br/>
                                        <input class="form-control" type="text" name="connectionCost" id="connCostOption" required pattern="\d+" value="" />
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label" for="compatibleOptions">Compatible options</label><br/>
                                        <select class="selectpicker form-control" multiple data-style="btn-primary" id="compatibleOptions" name="compatibleOptionsList">

                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label" for="incompatibleOptions">Incompatible options</label><br/>
                                        <select class="selectpicker form-control" multiple data-style="btn-primary" id="incompatibleOptions" name="incompatibleOptionsList">

                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <button id="submitBtn" class="btn btn-success" type="submit">Submit</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="col-md-6 hideMe">
                            <div class="card">
                                <div class="card-body">
                                    <h3>Current option info</h3>
                                    <div id="curTariff" class="card-footer">

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
