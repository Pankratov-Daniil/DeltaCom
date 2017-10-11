function addClientToSession (event) {
    $.ajax({
        url: 'addNewClientIdToSession',
        data: {'clientId': event.data.param1}
    });
}

function blockContract (contractId, block, blockByOperator, funcOnSuccess, successData) {
    $.ajax({
        url: '/DeltaCom/manager/blockContract',
        data: {'contractId': contractId,
               'block' : block,
                'blockByOperator' : blockByOperator},
        success: function(data) {
            funcOnSuccess(successData);
        },
        error: function () {
        }
    });
}

function createOptionsHtml(data) {
    var optionsList = '';
    var optionsInfo = '';
    var compatibleOptions = [];
    var incompatibleOptions = [];

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
    return {"optionsList" : optionsList,
        "optionsInfo" : optionsInfo,
        "compatibleOptions" : compatibleOptions,
        "incompatibleOptions" : incompatibleOptions};
}

function optionsChanged(selectOptionsName, prevSelected, curSelected, compatibleOptions, incompatibleOptions) {
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
                                    optionsChanged(selectOptionsName, prevSelected, curSelected, compatibleOptions, incompatibleOptions);
                                    return false;
                                }
                            }
                        });
                    } else {
                        $(this).prop('selected', 'true');
                        curSelected = $(selectOptionsName).val();
                    }
                }
                if(containsIncomp) {
                    $(this).prop('disabled','true');
                }
            });
        });
    $(selectOptionsName).selectpicker('refresh');

    return {"prevSelected" : prevSelected,
        "curSelected" : curSelected};
}

function updateOptions(selectOptions, selectedTariff, tariffInfo, availableOptions, tariffId) {
    tariffInfo.empty();
    tariffInfo.html("<p>Name: " + selectedTariff.text() + "<br/>Price: " + selectedTariff.attr('data-tariff-price') + "</p>");

    $.ajax({
        url:"/DeltaCom/manager/getOptionsForContract",
        contentType: "application/json",
        data: {
            "selectTariff" : tariffId
        },
        success: function(data) {
            var optionsHtml = createOptionsHtml(data);
            updateSelect(selectOptions, optionsHtml.optionsList);
            availableOptions.empty();
            availableOptions.append(optionsHtml.optionsInfo);
            compatibleOptions = optionsHtml.compatibleOptions;
            incompatibleOptions = optionsHtml.incompatibleOptions;
        },
        error: function () {
            availableOptions.empty();
        }
    }).done(function () {
        
    });
}

function optionsUpdated() {
    curSelected = [];
    prevSelected = [];
    updateOptions($('#selectOptions'), $('#selectTariff option:selected'), $('#tariffInfo'), $('#availableOptions'), $('#selectTariff').val());
}

function checkContains(array, item1, item2) {
    return array.some(elem => (item1 != item2)&&
        ((elem[0] == item1 && elem[1] == item2) ||
            (elem[0] == item2 && elem[1] == item1)));
}

function updateSelect(sel, data) {
    sel.empty();
    sel.append(data);
    sel.selectpicker('refresh');
}