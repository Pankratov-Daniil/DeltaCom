var options = [];
var tariffsList = [];
var prevSelected = [];
var curSelected = [];

$(document).ready(function () {
    getAllOptions(loadAllOptions);
});

function loadAllOptions(allOptions) {
    options = allOptions;
    loadAllTariffs();
}

function loadAllTariffs() {
    $.ajax({
        url: "/DeltaCom/commons/getAllTariffs",
        contentType: "application/json",
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
                    '<a id="' + tariff.id + '"class="delTariff btn btn-warning" href="deleteTariff">' +
                    '<i class="fa fa-lg fa-trash"></i>' +
                    '<input type="submit" class="hidden" name="idDelTariff" value="' + tariff.id + '"/>' +
                    '</a>' ;
                tariffsHtml += "</div></div></div></div></div>";
            });
            tariffsHtml += "</div>";
            $("#tariffsHolder").html(tariffsHtml);

            $(".changeTariffBtn").click(onOpenChangeTariff);

            $(".delTariff").click(function () {
                $(this).attr('href', $(this).attr('href') + '?idDelTariff=' + $(this).attr('id'));
            });

            var tariffOptionsSelect = $("#tariffOptions");
            tariffOptionsSelect.change(onTariffOptionsSelectChange);

            var form = $("#updatedTariff");
            $("#addNewTariffBtn").click(function () {
                $(".hideMe").addClass('hidden');
                $(".showMe").removeClass('hidden');
                $("#modalColumns").removeClass("col-md-6").addClass("col-md-12");
                $(".modal-dialog").removeClass("modal-lg");
                tariffOptionsSelect.html(createSelectList(options));
                tariffOptionsSelect.trigger('change');
                form.attr('action', 'createTariff');
                $("#nameTariff").val('');
                $("#priceTariff").val('');
                $("#connCostTariff").val('');

                form.submit(function () {
                    $("#idTariff").val(0);
                });

                $("#changeTariffModal").modal('show');
            });

            $("#changeTariffModal").on('hidden.bs.modal',function () {
                prevSelected = [];
                curSelected = [];

                $(".hideMe").removeClass('hidden');
                $(".showMe").addClass('hidden');
                $("#modalColumns").removeClass("col-md-12").addClass("col-md-6");
                $(".modal-dialog").addClass("modal-lg");
                form.attr('action', 'changeTariff');
            });
        },
        error: function() {
            notifyError("Error occurred while getting all tariffs. Try again later.");
        }
    });
}

function onTariffOptionsSelectChange() {
    var select = $("#tariffOptions");
    onOptionsSelectChange(select);
    selectCompatible(select);
    curSelected = getCurSelected(select);
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
    var tariffOptionsSelect =  $("#tariffOptions");

    $("#nameTariff").val(tariff.name);
    $("#priceTariff").val(tariff.price);
    $("#updatedTariff").submit(function () {
        $("#idTariff").val(tariff.id);
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