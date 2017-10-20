var compatibleOptions = [];
var incompatibleOptions = [];
var options = [];
var prevSelected = [];
var curSelected = [];

$(document).ready(function () {
    getAllOptions(loadAllOptions);
});

function addButtonsToCard(card, optionId) {
    card.append('<div class="card-footer">' +
        '<div class="btn-group">' +
        '<a id="' + optionId + '" class="changeOptionBtn btn btn-info" href="#">' +
        '<i class="fa fa-lg fa-edit"></i>' +
        '</a>' +
        '<a id="' + optionId + '"class="delOption btn btn-warning" href="deleteOption">' +
        '<i class="fa fa-lg fa-trash"></i>' +
        '<input type="submit" class="hidden" name="idDelOption" value="' + optionId + '"/>' +
        '</a>' +
        '</div></div>');
}

function loadAllOptions(data) {
    options = data;

    var cardLen = 3;
    var optionsHtml = createOptionsHtml(data, cardLen);
    compatibleOptions = optionsHtml.compatibleOptions;
    incompatibleOptions = optionsHtml.incompatibleOptions;

    var optionsHolder = $("#optionsHolder");
    optionsHolder.html(optionsHtml.optionsInfo);

    $(optionsHolder.find(".card")).each(function (index) {
        addButtonsToCard($(this), data[index].id);
    });

    var compatibleOptions = $("#compatibleOptions");
    var incompatibleOptions = $("#incompatibleOptions");
    compatibleOptions.change(disableOnChange);
    incompatibleOptions.change(disableOnChange);

    $(".changeOptionBtn").click(onOpenChangeOption);

    $(".delOption").click(function () {
        $(this).attr('href', $(this).attr('href') + '?idDelOption=' + $(this).attr('id'));
    });

    var form = $("#updatedOption");
    $("#addNewOptionBtn").click(function () {
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

        form.submit(function () {
            $("#idOption").val(0);
        });

        $("#changeOptionModal").modal('show');
    });

    $("#changeOptionModal").on('hidden.bs.modal',function () {
        $(".hideMe").removeClass('hidden');
        $(".showMe").addClass('hidden');
        $("#modalColumns").removeClass("col-md-12").addClass("col-md-6");
        $(".modal-dialog").addClass("modal-lg");
        form.attr('action', 'changeOption');
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

function disableOnChange() {
    var select = $("#"+$(this).attr('id'));
    onOptionsSelectChange(select);

    if($(this).attr('id') == 'compatibleOptions') {
        selectCompatible($("#compatibleOptions"));
    }
    disableInOtherLists([$("#compatibleOptions") ,$("#incompatibleOptions")]);
    disableIncompatibleOptionsInOptionsPage([$("#compatibleOptions")]);
    curSelected = getCurSelected(select);
    $("select").selectpicker('refresh');
}

function onOpenChangeOption() {
    var option = seachInArrayById(options, $(this).attr('id'));
    var optionsList = createSelectList(options);
    var compatibleOptions = $("#compatibleOptions");
    var incompatibleOptions = $("#incompatibleOptions");

    $("#nameOption").val(option.name);
    $("#priceOption").val(option.price);
    $("#connCostOption").val(option.connCost);
    compatibleOptions.html(optionsList);
    option.compatibleOptions.forEach(function (item) {
        var optionInSelect = compatibleOptions.children('option[value="' + item.id + '"]');
        if(optionInSelect) {
            optionInSelect.prop('selected', 'true');
        }
    });

    incompatibleOptions.html(optionsList);
    option.incompatibleOptions.forEach(function (item) {
        var optionInSelect = incompatibleOptions.children('option[value="' + item.id + '"]');
        if(optionInSelect) {
            optionInSelect.prop('selected', 'true');
        }
    });

    $(compatibleOptions).children("option[value='" + $(this).attr('id') + "']").remove();
    $(incompatibleOptions).children("option[value='" + $(this).attr('id') + "']").remove();

    $("select").selectpicker('refresh');
    compatibleOptions.trigger('change');

    var curTariffHtml = $(this).parent().siblings('.card-body').html();
    $("#curTariff").html(curTariffHtml);

    $("#updatedOption").submit(function () {
        $("#idOption").val(option.id);
    });

    $("#changeOptionModal").modal('show');
}
