var compatibleOptions = [];
var incompatibleOptions = [];
var options = [];
var prevSelected = [];
var curSelected = [];
var optionsHolder = undefined;
var compatibleOptionsSelect;
var incompatibleOptionsSelect;
var optionNameField;
var priceOptionField;
var connectionCostField;
var optionIdField;
var submitBtn;

$(document).ready(function () {
    optionsHolder = $("#optionsHolder");
    compatibleOptionsSelect = $("#compatibleOptions");
    incompatibleOptionsSelect = $("#incompatibleOptions");
    optionNameField = $("#nameOption");
    priceOptionField = $("#priceOption");
    connectionCostField = $("#connCostOption");
    optionIdField = $("#idOption");
    submitBtn = $("#submitBtn");
    getAllOptions(loadAllOptions);
});

function updateOptions() {
    var cardLen = 3;
    var optionsHtml = createOptionsHtml(options, cardLen);
    compatibleOptions = optionsHtml.compatibleOptions;
    incompatibleOptions = optionsHtml.incompatibleOptions;

    var optionsHolder = $("#optionsHolder");
    optionsHolder.html(optionsHtml.optionsInfo);

    $(optionsHolder.find(".card")).each(function (index) {
        addButtonsToCard($(this), options[index].id);
    });

    return optionsHtml;
}

function loadAllOptions(data) {
    options = data;
    var optionsHtml = updateOptions();

    var compatibleOptions = $("#compatibleOptions");
    var incompatibleOptions = $("#incompatibleOptions");
    addEvent('change', "#compatibleOptions", {"compatible" : true}, disableOnChange);
    addEvent('change', "#incompatibleOptions", {"compatible" : false}, disableOnChange);

    addClickEvent(".changeOptionBtn", {}, onOpenChangeOption);
    addClickEvent(".delOption", {}, deleteOption);

    var form = $("#updatedOption");
    addClickEvent("#addNewOptionBtn", {}, function () {
        $(".hideMe").addClass('hidden');
        $(".showMe").removeClass('hidden');
        $("#modalColumns").removeClass("col-md-6").addClass("col-md-12");
        $(".modal-dialog").removeClass("modal-lg");

        form.attr('action', 'createOption');
        $("#nameOption").val('');
        $("#priceOption").val('');
        $("#connCostOption").val('');

        compatibleOptions.html(optionsHtml.optionsList);
        incompatibleOptions.html(optionsHtml.optionsList);
        compatibleOptions.trigger('change');
        incompatibleOptions.trigger('change');

        addEvent("submit", "#updatedOption", {}, function (event) {
            $("#idOption").val(0);
            addOption();
            $("#changeOptionModal").modal('hide');
            event.preventDefault();
        });

        $("#changeOptionModal").modal('show');
    });

    addEvent('hidden.bs.modal', "#changeOptionModal", {}, function () {
        $(".hideMe").removeClass('hidden');
        $(".showMe").addClass('hidden');
        $("#modalColumns").removeClass("col-md-12").addClass("col-md-6");
        $(".modal-dialog").addClass("modal-lg");
        form.attr('action', 'changeOption');
    });
}

function addButtonsToCard(card, optionId) {
    card.append('<div class="card-footer">' +
        '<div class="btn-group">' +
        '<a id="' + optionId + '" class="changeOptionBtn btn btn-info" href="javascript:void(0);">' +
        '<i class="fa fa-lg fa-edit"></i>' +
        '</a>' +
        '<a id="' + optionId + '"class="delOption btn btn-warning" href="javascript:void(0);">' +
        '<i class="fa fa-lg fa-trash"></i>' +
        '<input type="submit" class="hidden" name="idDelOption" value="' + optionId + '"/>' +
        '</a>' +
        '</div></div>');
}

function deleteOption() {
    var button = $(this);
    button.prop('disabled', 'true');
    $.ajax({
        contentType: "application/json; charset=utf-8",
        type: "POST",
        url: "/DeltaCom/manager/deleteOption",
        data: JSON.stringify(button.attr('id')),
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function () {
            getAllOptions(loadAllOptions);
            notifySuccess("Option successfully deleted.");
        },
        error: function() {
            button.removeAttr('disabled');
            notifyError("Error occurred while deleting option. Try again later.");
        }
    });
}

function getOptionFromForm(add) {
    return {"id" : add ? 0 : optionIdField.val(),
        "name" : optionNameField.val(),
        "price" : priceOptionField.val(),
        "connectionCost" : connectionCostField.val(),
        "incompatibleOptions" : incompatibleOptionsSelect.val() != null ? incompatibleOptionsSelect.val() : [],
        "compatibleOptions" : compatibleOptionsSelect.val() != null ? compatibleOptionsSelect.val() : []}
}

function addOption() {
    submitBtn.prop('disabled', 'true');
    var option = getOptionFromForm(true);
    $.ajax({
        contentType: "application/json; charset=utf-8",
        url: "/DeltaCom/manager/createOption",
        method: "POST",
        data: JSON.stringify(option),
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function () {
            submitBtn.removeAttr('disabled');
            getAllOptions(loadAllOptions);
            notifySuccess("Option successfully created.");
        },
        error: function() {
            submitBtn.removeAttr('disabled');
            notifyError("Error occurred while creating option. Try again later.");
        }
    });
}

function editOption() {
    submitBtn.prop('disabled', 'true');
    var option = getOptionFromForm(false);

    $.ajax({
        contentType: "application/json; charset=utf-8",
        url: "/DeltaCom/manager/changeOption",
        method: "POST",
        data: JSON.stringify(option),
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function () {
            submitBtn.removeAttr('disabled');
            getAllOptions(loadAllOptions);
            notifySuccess("Option successfully changed.");
        },
        error: function() {
            submitBtn.removeAttr('disabled');
            notifyError("Error occurred while changing option. Try again later.");
        }
    });
}

function createSelectList(arr) {
    var text = '';
    arr.forEach(function (item) {
        text += '<option value="' + item.id + '">' + item.name + '</option>';
    });
    return text;
}

function disableIncompatibleOptionsInOptionsPage(selects) {
    selects.forEach(function (select) {
        disableIncompatibleOptions(select, selects);
    });
}

function disableInOtherLists(selects) {
    selects.forEach(function (select) {
        var compatibleName = (select.attr('id') == "compatibleOptions" ? "#incompatibleOptions" : "#compatibleOptions");
        $.each($(compatibleName).children("option:disabled"), function (index, item) {
            item.disabled = false;
        });
        $.each(select.children("option:selected"), function (index, item) {
            var optInOtherList = $(compatibleName).children("option[value='" + item.value + "']");
            if (optInOtherList) {
                disableOption(optInOtherList);
            }
        });
    });
}

function disableOnChange(event) {
    var select = $("#"+$(this).attr('id'));
    if(event.data.compatible) {
        onOptionsSelectChange(select);
    }

    if($(this).attr('id') == 'compatibleOptions') {
        selectCompatible($("#compatibleOptions"));
    }
    disableInOtherLists([$("#compatibleOptions") ,$("#incompatibleOptions")]);
    disableIncompatibleOptionsInOptionsPage([$("#compatibleOptions")]);
    curSelected = getCurSelected(select);
    $("select").selectpicker('refresh');
}

/**
 * Calls when modal window opens for change option
 */
function onOpenChangeOption() {
    var option = searchInArrayById(options, $(this).attr('id'));
    var optionsList = createSelectList(options);

    optionNameField.val(option.name);
    priceOptionField.val(option.price);
    connectionCostField.val(option.connectionCost);
    compatibleOptionsSelect.html(optionsList);
    option.compatibleOptions.forEach(function (item) {
        var optionInSelect = compatibleOptionsSelect.children('option[value="' + item.id + '"]');
        if(optionInSelect) {
            optionInSelect.prop('selected', 'true');
        }
    });

    incompatibleOptionsSelect.html(optionsList);
    option.incompatibleOptions.forEach(function (item) {
        var optionInSelect = incompatibleOptionsSelect.children('option[value="' + item.id + '"]');
        if(optionInSelect) {
            optionInSelect.prop('selected', 'true');
        }
    });

    $(compatibleOptionsSelect).children("option[value='" + $(this).attr('id') + "']").remove();
    $(incompatibleOptionsSelect).children("option[value='" + $(this).attr('id') + "']").remove();

    $("select").selectpicker('refresh');
    compatibleOptionsSelect.trigger('change');

    var curTariffHtml = $(this).closest(".card-footer").siblings(".card-body").html();
    $("#curTariff").html(curTariffHtml);

    addEvent("submit", "#updatedOption", {}, function (event) {
        optionIdField.val(option.id);
        editOption();
        $("#changeOptionModal").modal('hide');
        event.preventDefault();
    });
    $("#changeOptionModal").modal('show');
}
