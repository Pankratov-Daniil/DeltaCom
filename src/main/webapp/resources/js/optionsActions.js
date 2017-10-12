var compatibleOptions = [];
var incompatibleOptions = [];
var options = [];

$(document).ready(function () {
    loadAllOptions();
});

function loadAllOptions() {
    $.ajax({
        url: "/DeltaCom/manager/getAllOptions",
        contentType: "application/json",
        success: function (data) {
            var cardLen = 3;
            var optionsHtml = createOptionsHtml(data, cardLen);
            compatibleOptions = optionsHtml.compatibleOptions;
            incompatibleOptions = optionsHtml.incompatibleOptions;

            var optionsHolder = $("#optionsHolder");
            optionsHolder.html(optionsHtml.optionsInfo);

            var i;
            for(i = 0; i < data.length; i++){
                options.push({
                    'id' : data[i].id,
                    'name' : data[i].name,
                    'price' : data[i].price,
                    'connCost' : data[i].connectionCost,
                    'compatibleOptions' : data[i].compatibleOptions,
                    'incompatibleOptions' : data[i].incompatibleOptions
                });
            }

            i = 0;
            $(optionsHolder.find(".card")).each(function () {
                $(this).append('<div class="card-footer">');
                $(this).append('<div class="btn-group">' +
                    '<a id="' + data[i].id + '" class="changeOptionBtn btn btn-info" href="#">' +
                    '<i class="fa fa-lg fa-edit"></i>' +
                    '</a>' +
                    '<a id="' + data[i].id + '"class="delOption btn btn-warning" href="deleteOption">' +
                    '<i class="fa fa-lg fa-trash"></i>' +
                    '<input type="submit" class="hidden" name="idDelOption" value="' + data[i].id + '"/>' +
                    '</a>' +
                    '</div>');
                $(this).append('</div>');

                var wereHere = [];
                options[i].compatibleOptions.forEach(function (item) {
                    var compOpt = options.find(function (elem) {
                        if(elem.id == item.id) return true;
                    });
                    if(compOpt && compOpt.compatibleOptions.indexOf(item) < 0) {
                        compOpt.compatibleOptions.push(options[i]);
                    }
                });
                wereHere = [];
                options[i].incompatibleOptions.forEach(function (item, index) {
                    var incompOpt = options.find(function (elem) {
                        if(elem.id == item.id) return true;
                    });
                    if (incompOpt && incompOpt.incompatibleOptions.indexOf(item) < 0) {
                        incompOpt.incompatibleOptions.push(options[i]);
                    }
                });
                i++;
            });

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
                var compatibleOptions = $("#compatibleOptions");
                var incompatibleOptions = $("#incompatibleOptions");
                compatibleOptions.html(optionsHtml.optionsList);
                incompatibleOptions.html(optionsHtml.optionsList);
                compatibleOptions.change(disableInOtherListOnChange);
                incompatibleOptions.change(disableInOtherListOnChange);
                compatibleOptions.trigger('change');
                incompatibleOptions.trigger('change');
                $("select").selectpicker('refresh');

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
    });
}

function createSelectList(arr) {
    var text = '';
    arr.forEach(function (item) {
        text += '<option value="' + item.id + '">' + item.name + '</option>';
    });
    return text;
}

function disableInOtherListOnChange() {
    var compatibleName = ($(this).attr('id') == "compatibleOptions" ? "#incompatibleOptions" : "#compatibleOptions");
    $.each($(compatibleName).children("option:disabled"), function (index, item) {
            item.disabled = false;
    });
    $.each($(this).children("option:selected"), function (index, item) {
        var optInOtherList = $(compatibleName).children("option[value='" + item.value + "']");
        if(optInOtherList) {
            optInOtherList.removeAttr('selected');
            optInOtherList.prop('disabled', 'true');
        }
    });
    $("select").selectpicker('refresh');
}

function onOpenChangeOption() {
    var option = findInOptionsById($(this).attr('id'));
    var optionsList = createSelectList(options);
    var compatibleOptions = $("#compatibleOptions");
    var incompatibleOptions = $("#incompatibleOptions");
    compatibleOptions.change(disableInOtherListOnChange);
    incompatibleOptions.change(disableInOtherListOnChange);

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
    incompatibleOptions.trigger('change');
    var curTariffHtml = $(this).parent().siblings('.card-body').html();
    $("#curTariff").html(curTariffHtml);

    $("#updatedOption").submit(function () {
        $("#idOption").val(option.id);
        var deletedComp = [];
        var deletedIncomp = [];
        var selectedOpts = $(compatibleOptions).children("option:selected");
        option.compatibleOptions.forEach(function (item) {
            var deleted = true;
            $.each(selectedOpts, function (index, selOpt) {
                if(selOpt.value == item.id) {
                    deleted = false;
                }
            });
            if(deleted) {
                deletedComp.push(item.id);
            }
        });

        selectedOpts = $(incompatibleOptions).children("option:selected");
        option.incompatibleOptions.forEach(function (item) {
            var deleted = true;
            $.each(selectedOpts, function (index, selOpt) {
                if(selOpt.value == item.id) {
                    deleted = false;
                }
            });
            if(deleted) {
                deletedIncomp.push(item.id);
            }
        });

        $("#deletedCompOpts").val(deletedComp.join(','));
        $("#deletedIncompOpts").val(deletedIncomp.join(','));
    });

    $("#changeOptionModal").modal('show');
}

function findInOptionsById(id) {
    var option = null;
    options.forEach(function (item) {
        if(item.id == id) {
            option = item;
        }
    });
    return option;
}