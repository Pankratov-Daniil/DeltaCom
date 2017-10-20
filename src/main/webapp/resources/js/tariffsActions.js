var options = [];
var tariffsList = [];
var prevSelected = [];
var curSelected = [];
var tariffOptionsSelect;
var nameTariffField;
var priceTariffField;
var tariffIdField;
var submitBtn;

$(document).ready(function () {
    tariffOptionsSelect =  $("#tariffOptions");
    nameTariffField = $("#nameTariff");
    priceTariffField = $("#priceTariff");
    tariffIdField = $("#idTariff");
    submitBtn = $("#submitBtn");
    getAllOptions(loadAllOptions);
});

function loadAllOptions(allOptions) {
    options = allOptions;
    loadAllTariffs();
}

function loadAllTariffs() {
    $.ajax({
        url: "/DeltaCom/commons/getAllTariffs",
        contentType: "application/json; charset=utf-8",
        method: "POST",
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function (tariffs) {
            var cardLen = 4;
            var tariffsHtml = "<div class='row'>";
            tariffs.forEach(function (tariff) {
                tariffsHtml += "<div class='col-md-" + cardLen + "'><div class='card'><div class='card-body'>";
                tariffsHtml += "<p>Name: " + tariff.name + "<br/>Price: " + tariff.price + "</p><br/>Options: ";

                tariff.options = idsToObjectInOptionsCompatibilityArr(tariff.options, options);
                tariff.options.forEach(function (option) {
                    option.compatibleOptions = idsToObjectInOptionsCompatibilityArr(option.compatibleOptions, tariff.options);
                    option.incompatibleOptions = idsToObjectInOptionsCompatibilityArr(option.incompatibleOptions, tariff.options);
                });

                tariffsList.push({'id' : tariff.id, 'name' : tariff.name, 'price' : tariff.price, 'options' : tariff.options});

                tariff.options.forEach(function (tariffOption, index) {
                    tariffsHtml += tariffOption.name;
                    if(index < tariff.options.length - 1) {
                        tariffsHtml += ", ";
                    }
                });
                tariffsHtml += "<div class='card-footer'><div class='btn-group'>";
                tariffsHtml += '<a id="' + tariff.id + '" class="changeTariffBtn btn btn-info" href="#">' +
                    '<i class="fa fa-lg fa-edit"></i>' +
                    '</a>' +
                    '<a id="' + tariff.id + '"class="delTariff btn btn-warning" href="#">' +
                    '<i class="fa fa-lg fa-trash"></i>' +
                    '<input type="submit" class="hidden" name="idDelTariff" value="' + tariff.id + '"/>' +
                    '</a>' ;
                tariffsHtml += "</div></div></div></div></div>";
            });
            tariffsHtml += "</div>";
            $("#tariffsHolder").html(tariffsHtml);

            addClickEvent(".changeTariffBtn", {}, onOpenChangeTariff);
            addClickEvent(".delTariff", {}, deleteTariff);

            addEvent("change", "#tariffOptions", {}, onTariffOptionsSelectChange);

            addClickEvent("#addNewTariffBtn", {}, function () {
                $(".hideMe").addClass('hidden');
                $(".showMe").removeClass('hidden');
                $("#modalColumns").removeClass("col-md-6").addClass("col-md-12");
                $(".modal-dialog").removeClass("modal-lg");
                tariffOptionsSelect.html(createSelectList(options));
                tariffOptionsSelect.trigger('change');

                nameTariffField.val('');
                priceTariffField.val('');

                addEvent('submit', "#updatedTariff", {}, function (event) {
                    tariffIdField.val(0);
                    event.preventDefault();
                    addTariff();
                    $("#changeTariffModal").modal('hide');
                });

                $("#changeTariffModal").modal('show');
            });

            addEvent('hidden.bs.modal', "#changeTariffModal", {},function () {
                prevSelected = [];
                curSelected = [];

                $(".hideMe").removeClass('hidden');
                $(".showMe").addClass('hidden');
                $("#modalColumns").removeClass("col-md-12").addClass("col-md-6");
                $(".modal-dialog").addClass("modal-lg");
            });
        },
        error: function() {
            notifyError("Error occurred while getting all tariffs. Try again later.");
        }
    });
}

function onTariffOptionsSelectChange() {
    onOptionsSelectChange(tariffOptionsSelect);
    selectCompatible(tariffOptionsSelect);
    curSelected = getCurSelected(tariffOptionsSelect);
    $(this).selectpicker('refresh');
}

function createSelectList(arr) {
    var text = '';
    arr.forEach(function (item) {
        text += '<option value="' + item.id + '">' + item.name + '</option>';
    });
    return text;
}

function onOpenChangeTariff() {
    var tariffBtn = $(this);
    var tariff = $.grep(tariffsList, function (item) {
        return item.id == tariffBtn.attr('id');
    })[0];

    nameTariffField.val(tariff.name);
    priceTariffField.val(tariff.price);
    addEvent('submit', "#updatedTariff", {}, function (event) {
        tariffIdField.val(tariff.id);
        editTariff();
        $("#changeTariffModal").modal('hide');
        event.preventDefault();
    });
    tariffOptionsSelect.html(createSelectList(options));
    $(this).selectpicker('refresh');
    var curTariffInfo = '<p>Name: ' + tariff.name + '<br/>Price: ' + tariff.price + '<br/>Options: ';
    var idsOptionsForSelect = [];
    tariff.options.forEach(function (opt, index) {
        idsOptionsForSelect.push(opt.id);
        curTariffInfo += opt.name;
        if(index < tariff.options.length -1) {
            curTariffInfo += ', ';
        }
    });
    curTariffInfo += '</p>';
    $("#curTariff").html(curTariffInfo);
    tariffOptionsSelect.selectpicker('val', idsOptionsForSelect);
    tariffOptionsSelect.trigger('change');
    $(this).selectpicker('refresh');
    $("#changeTariffModal").modal('show');
}

function getTariffFromForm(add) {
    return {"id" : add ? 0 : tariffIdField.val(),
        "name" : nameTariffField.val(),
        "price" : priceTariffField.val(),
        "optionsIds" : tariffOptionsSelect.val()
    }
}

function editTariff() {
    submitBtn.prop('disabled', 'true');
    var tariff = getTariffFromForm(false);

    $.ajax({
        contentType: "application/json; charset=utf-8",
        url: "/DeltaCom/manager/changeTariff",
        method: "POST",
        data: JSON.stringify(tariff),
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function () {
            submitBtn.removeAttr('disabled');
            getAllOptions(loadAllOptions);
            notifySuccess("Tariff successfully changed.");
        },
        error: function() {
            submitBtn.removeAttr('disabled');
            notifyError("Error occurred while changing Tariff. Try again later.");
        }
    });
}

function addTariff() {
    submitBtn.prop('disabled', 'true');
    var tariff = getTariffFromForm(true);
    $.ajax({
        contentType: "application/json; charset=utf-8",
        url: "/DeltaCom/manager/createTariff",
        method: "POST",
        data: JSON.stringify(tariff),
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function () {
            submitBtn.removeAttr('disabled');
            getAllOptions(loadAllOptions);
            notifySuccess("Tariff successfully created.");
        },
        error: function() {
            submitBtn.removeAttr('disabled');
            notifyError("Error occurred while creating tariff. Try again later.");
        }
    });
}

function deleteTariff() {
    var button = $(this);
    button.prop('disabled', 'true');
    $.ajax({
        contentType: "application/json; charset=utf-8",
        type: "POST",
        url: "/DeltaCom/manager/deleteTariff",
        data: JSON.stringify(button.attr('id')),
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function () {
            getAllOptions(loadAllOptions);
            notifySuccess("Tariff successfully deleted.");
        },
        error: function() {
            button.removeAttr('disabled');
            notifyError("Error occurred while deleting tariff. Try again later.");
        }
    });
}