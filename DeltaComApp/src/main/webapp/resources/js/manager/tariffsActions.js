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
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        }
    });
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
        success: function (tariffs) {
            afterLoadingTariffs(tariffs);
        },
        error: function() {
            notifyError("Error occurred while getting all tariffs. Try again later.");
        }
    });
}

function afterLoadingTariffs(tariffs) {
    var rowLen = 12;
    var cardLen = 3;
    var tariffsHtml = "<div class='row'>";
    tariffsList = [];
    tariffs.forEach(function (tariff, index) {
        tariff.options = idsToObjectInOptionsCompatibilityArr(tariff.options, options);
        tariff.options.forEach(function (option) {
            option.compatibleOptions = idsToObjectInOptionsCompatibilityArr(option.compatibleOptions, tariff.options);
            option.incompatibleOptions = idsToObjectInOptionsCompatibilityArr(option.incompatibleOptions, tariff.options);
        });
        tariffsList.push({'id' : tariff.id, 'name' : tariff.name, 'price' : tariff.price, 'options' : tariff.options});

        if((index % (rowLen/cardLen)) === 0) {
            tariffsHtml += '<div class="clearfix"></div>';
        }
        tariffsHtml += "<div class='col-md-" + cardLen + "'><div class='card'><div class='card-body'>";
        tariffsHtml += "<div style='text-align: center'><h3 style='margin-top: 0'><i class='fa fa-lg fa-mobile'></i> " + tariff.name + "</h3></div>";
        tariffsHtml += "<div class='card-footer'></div>";
        tariffsHtml += "<div style='text-align: center'><h4 style='margin-top: 0'>Price</h4><p>" + tariff.price.toLocaleString('ru-RU', {style: 'currency', currency : 'RUB'}) + "</p></div><div class='card-footer'></div>";
        tariffsHtml += "<h4 style='text-align: center; margin-top: 0'>Options: </h4>";
        tariffsHtml += "<ul class='fa-ul'>";
        tariff.options.forEach(function (tariffOption) {
            tariffsHtml += "<li><i class='fa-li fa fa-filter'></i> " + tariffOption.name + "</li>";
        });
        tariffsHtml += "</ul>";
        tariffsHtml += "<div class='card-footer'><div class='btn-group'>";
        tariffsHtml += '<a id="' + tariff.id + '" class="changeTariffBtn btn btn-info" href="#">' +
            '<i class="fa fa-lg fa-edit"></i>' +
            '</a>' +
            '<a id="' + tariff.id + '" class="delTariff btn btn-warning" href="#">' +
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
            addOrEditTariff(true);
            $("#changeTariffModal").modal('hide');
        });

        $("#changeTariffModal").modal('show');
    });

    addEvent('hidden.bs.modal', "#changeTariffModal", {}, onHideChangeTariffModal);
}

function onHideChangeTariffModal() {
    prevSelected = [];
    curSelected = [];

    $(".hideMe").removeClass('hidden');
    $(".showMe").addClass('hidden');
    $("#modalColumns").removeClass("col-md-12").addClass("col-md-6");
    $(".modal-dialog").addClass("modal-lg");
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
        addOrEditTariff(false);
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

/**
 * Gets tariff from form and translate it to DTO
 * @param add do we need to add a tariff
 * @returns {{id: number, name: *, price: *, optionsIds: *}}
 */
function getTariffFromForm(add) {
    return {"id" : add ? 0 : tariffIdField.val(),
        "name" : nameTariffField.val(),
        "price" : priceTariffField.val(),
        "optionsIds" : tariffOptionsSelect.val()
    }
}

/**
 * Add or edit tariff
 * @param add do we need to add a tariff
 */
function addOrEditTariff(add) {
    submitBtn.prop('disabled', 'true');
    var tariff = getTariffFromForm(add);
    var onErrorFunc = function() {
        submitBtn.removeAttr('disabled');
        notifyError("Error occurred while " + (add ? "creating" : "changing") + " tariff. Try again later.");
    };
    $.ajax({
        contentType: "application/json; charset=utf-8",
        url: "/DeltaCom/manager/" + (add ? "createTariff" : "changeTariff"),
        method: "POST",
        data: JSON.stringify(tariff),
        success: function (data) {
            if(data != '') {
                onErrorFunc();
                return;
            }
            submitBtn.removeAttr('disabled');
            getAllOptions(loadAllOptions);
            notifySuccess("Tariff successfully " + (add ? "created." : "changed."));
        },
        error: onErrorFunc
    });
}

/**
 * Deletes tariff
 */
function deleteTariff() {
    var button = $(this);
    var onErrorFunc = function() {
        button.removeAttr('disabled');
        notifyError("Error occurred while deleting tariff. Try again later.");
    };
    button.prop('disabled', 'true');
    $.ajax({
        contentType: "application/json; charset=utf-8",
        type: "POST",
        url: "/DeltaCom/manager/deleteTariff",
        data: JSON.stringify(button.attr('id')),
        success: function (data) {
            if(data != '') {
                onErrorFunc();
                return;
            }
            getAllOptions(loadAllOptions);
            notifySuccess("Tariff successfully deleted.");
        },
        error: onErrorFunc
    });
}