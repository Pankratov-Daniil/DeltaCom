var compatibleOptions = [];
var incompatibleOptions = [];

var prevSelected = [];
var curSelected = [];

var selectOptionsName = "#selectOptions";
$(document).ready(function () {
    $("#selectTariff").change(updateOptions);
    $(selectOptionsName).change(optionsChanged);
    updateOptions();
});

function updateOptions() {
    var selectedTariff = $('#selectTariff option:selected');
    var tariffInfo = $('#tariffInfo');
    var availableOptions = $('#availableOptions');
    var tariffId = $('#selectTariff').val();

    curSelected = [];
    prevSelected = [];
    tariffInfo.empty();
    tariffInfo.html("<p>Name: " + selectedTariff.text() + "<br/>Price: " + selectedTariff.attr('data-tariff-price') + "</p>");

    $.ajax({
        url:"/DeltaCom/manager/getOptionsForContract",
        contentType: "application/json",
        data: {
            "selectTariff" : tariffId
        },
        success: function(data) {
            var optionsDrop = $('#selectOptions');
            var optionsList = '';
            var optionsInfo = '';
            compatibleOptions = [];
            incompatibleOptions = [];
            optionsInfo += "<div class='row'>";
            $.each(data, function (index, item) {
                optionsList += '<option value="' + item.id + '">' + item.name + '</option>';
                optionsInfo += "<div class='col-md-6'><div class='card'><div class='card-body'>";
                optionsInfo += "<p>Name: " + item.name + "<br/>Price: " + item.price + "<br/>Connection cost: " + item.connectionCost + "</p>";
                if(item.compatibleOptions.length > 0) {
                    optionsInfo += "<p>Comes with: ";
                    $.each(item.compatibleOptions, function (compatIndex, option) {
                        compatibleOptions.push([item.id, option.id]);
                        optionsInfo += option.name;
                        if(compatIndex < item.compatibleOptions.length - 1)
                            optionsInfo += ", ";
                    });
                    optionsInfo += "</p>";
                } else
                    optionsInfo += "<br/><p></p>";
                if(item.incompatibleOptions.length > 0) {
                    optionsInfo += "<p>Incompatible with: ";
                    $.each(item.incompatibleOptions, function (incompatIndex, option) {
                        incompatibleOptions.push([item.id, option.id]);
                        optionsInfo += option.name;
                        if(incompatIndex < item.incompatibleOptions.length - 1)
                            optionsInfo += ", ";
                    });
                    optionsInfo += "</p>";
                }else
                    optionsInfo += "<br/><p></p>";
                optionsInfo += "</div></div></div>";
            });
            optionsInfo += "</div>";
            optionsDrop.empty();
            optionsDrop.append(optionsList);
            optionsDrop.selectpicker('refresh');
            availableOptions.empty();
            availableOptions.append(optionsInfo);
        },
        error: function () {
            $('#selectOptions').empty();
            availableOptions.empty();
        }
    });
}

function optionsChanged() {
    // incompatible section
    $.each($(selectOptionsName + " option:disabled"), function () {
        $(this).removeAttr('disabled');
    });
    $(selectOptionsName).selectpicker('refresh');

    prevSelected = curSelected;
    curSelected = $(selectOptionsName).val();

    // for each selected elem
    if($(selectOptionsName).val())
        $.each($(selectOptionsName).val(), function (index, item) {
            // for each option
            $.each($(selectOptionsName + " option"), function (optInd, optItm) {
                var containsIncomp = checkContains(incompatibleOptions, item, $(this).val());
                var containsComp = checkContains(compatibleOptions, item, $(this).val());

                if(containsIncomp) {
                    $(this).prop('disabled','true');
                }
                if(containsComp) {
                    var deletedVal = -1;
                    if(prevSelected)
                        $.each(prevSelected, function (indexPrev, itemPrev) {
                            if(curSelected.indexOf(itemPrev) < 0){
                                deletedVal = itemPrev;
                                return false;
                            }
                        });
                    if(deletedVal > -1) {
                        $.each(compatibleOptions, function (compInd, compItm) {
                            if(compItm[0] == deletedVal || compItm[1] == deletedVal) {
                                var index = curSelected.indexOf(compItm[1].toString());
                                if(index < 0) {
                                    index = curSelected.indexOf(compItm[0].toString());
                                }
                                if(index > -1) {
                                    $(selectOptionsName + " [value='"+curSelected[index]+"']").removeAttr('selected');
                                    curSelected = $(selectOptionsName).val();
                                    return false;
                                }
                            }
                        });
                    } else {
                        $(this).prop('selected', 'true');
                        curSelected = $(selectOptionsName).val();
                    }
                }
            });
        });
    $(selectOptionsName).selectpicker('refresh');
}

function checkContains(array, item1, item2) {
    return array.some(elem => (item1 != item2)&&
        ((elem[0] == item1 && elem[1] == item2) ||
            (elem[0] == item2 && elem[1] == item1)));
}