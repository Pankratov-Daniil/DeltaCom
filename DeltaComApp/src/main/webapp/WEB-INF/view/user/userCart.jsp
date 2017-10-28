<script src="../resources/js/commonFunctions.js"></script>
<script type="text/javascript" src="../resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="../resources/css/bootstrap-select.min.css" type="text/css"/>

<c:if test="${fn:contains(pageContext.request.requestURI, '/user/contracts') || sessionScope.cart ne null}">

    <script src="../resources/js/userCart.js"></script>

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
                                <form id="changeContract" name="changeContract" acceptCharset="utf8">
                                    <div class="form-group">
                                        <label class="control-label">Selected number</label><br/>
                                        <input class="btn btn-primary" type="text" name="numberModal" id="numberModal" readonly="true" value="" />
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
                                        <button id="applyContract" class="btn btn-success" type="submit">Submit</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-body">
                                    <h3>Current tariff info</h3>
                                    <div id="curTariff" class="card-footer">

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
</c:if>