var options = [];
var tariffsList = [];
var prevSelected = [];
var curSelected = [];

$(document).ready(function () {
    loadAllTariffs();
});

function loadAllOptions() {
    options = [];
    $.ajax({
        url: "/DeltaCom/manager/getAllOptions",
        contentType: "application/json",
        success: function (allOptions) {
            allOptions.forEach(function (option) {
                options.push({'id' : option.id, 'name' : option.name, 'compatibleOptions' : option.compatibleOptions});
            });
            options.forEach(function (option) {
                option.compatibleOptions.forEach(function (compOpt) {
                    var foundOpt = $.grep(options, function (item) {
                        return item.id == compOpt.id;
                    });
                    if(foundOpt[0]) {
                        foundOpt[0].compatibleOptions.push(option);
                    }
                });
            });
        }
    });
}
function loadAllTariffs() {
    $.ajax({
        url: "/DeltaCom/manager/getAllTariffs",
        contentType: "application/json",
        success: function (tariffs) {
            loadAllOptions();

            var cardLen = 4;
            var tariffsHtml = "<div class='row'>";
            tariffs.forEach(function (tariff) {
                tariffsList.push({'id' : tariff.id, 'name' : tariff.name, 'price' : tariff.price, 'options' : tariff.options});
                tariffsHtml += "<div class='col-md-" + cardLen + "'><div class='card'><div class='card-body'>";
                tariffsHtml += "<p>Name: " + tariff.name + "<br/>Price: " + tariff.price + "</p><br/>Options: ";
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

            var form = $("#updatedTariff");
            $("#addNewTariffBtn").click(function () {
                $(".hideMe").addClass('hidden');
                $(".showMe").removeClass('hidden');
                $("#modalColumns").removeClass("col-md-6").addClass("col-md-12");
                $(".modal-dialog").removeClass("modal-lg");

                var tariffOptionsSelect = $("#tariffOptions");
                tariffOptionsSelect.html(createSelectList(options));
                tariffOptionsSelect.change(onTariffOptionsSelectChange);
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
                $(".hideMe").removeClass('hidden');
                $(".showMe").addClass('hidden');
                $("#modalColumns").removeClass("col-md-12").addClass("col-md-6");
                $(".modal-dialog").addClass("modal-lg");
                form.attr('action', 'changeTariff');
            });
        }
    });
}

function onTariffOptionsSelectChange() {
    var select = $(this);
    prevSelected = curSelected;
    alert(curSelected.length + " " + prevSelected.length);
    curSelected = [];
    var selectedOptions = select.children('option:selected');
    if(selectedOptions.length < prevSelected.length) {
        alert("ok");
    } else {
        $.each(selectedOptions, function (index, selectOptionId) {
            curSelected.push(selectOptionId.value);
            var selectedOption = $.grep(options, function (item) {
                return item.id == selectOptionId.value;
            });
            selectedOption[0].compatibleOptions.forEach(function (compOpt) {
                select.children('option[value="' + compOpt.id + '"]').prop('selected', 'true');
                onTariffOptionsSelectChange();
                curSelected.push(compOpt.id);
            });
        });
    }

    $(this).selectpicker('refresh');
}

function createSelectList(arr) {
    var text = '';
    arr.forEach(function (item, index) {
        text += '<option value="' + item.id + '">' + item.name + '</option>';
    });
    return text;
}

function onOpenChangeTariff() {
    var tariffBtn = $(this);
    var tariff = $.grep(tariffsList, function (item) {
        return item.id == tariffBtn.attr('id');
    })[0];
    $("#nameTariff").val(tariff.name);
    $("#priceTariff").val(tariff.price);
    $("#tariffOptions").html(createSelectList(options));


    $("#changeTariffModal").modal('show');
}