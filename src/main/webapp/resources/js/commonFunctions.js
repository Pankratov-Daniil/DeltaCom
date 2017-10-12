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

function seachInOptsArray(arr, id) {
    var foundItem = null;
    arr.forEach(function (item, index, array) {
        if(item.id == id) {
            foundItem = item;
        }
    });
    return foundItem;
}

function makeCompatibilityText(arr, curText, curOptions, compatible) {
    var text = curText;
    var arrForCompatibility = [];
    var curOptOptions;
    if(compatible) {
        curOptOptions = curOptions.compatibleOptions;
    } else {
        curOptOptions = curOptions.incompatibleOptions;
    }
    $.each(curOptOptions, function (compatIndex, option) {
        if(seachInOptsArray(arr, option.id) != null) {
            if(text == '') {
                text = compatible ? "Comes with: " : "Incompatible with: ";
            }
            arrForCompatibility.push([curOptions.id, option.id]);
            text += option.name;
            if (compatIndex < curOptOptions.length - 1)
                text += ", ";

            var compElem = seachInOptsArray(arr, option.id);
            if (compElem != null) {
                if(compatible) {
                    if (compElem.compOpts == '') {
                        compElem.compOpts = "Comes with: " + curOptions.name + " ";
                    } else {
                        compElem.compOpts += ", " + curOptions.name;
                    }
                } else {
                    if (compElem.incompOpts == '') {
                        compElem.incompOpts = "Incompatible with: " + curOptions.name;
                    } else {
                        compElem.incompOpts += ", " + curOptions.name;
                    }
                }
            }
        }
    });
    return {"text" : text, "arrForCompatibility" : arrForCompatibility};
}

function createOptionsHtml(data, cardLen) {
    var opts = [];
    $.each(data, function (index, item) {
        opts.push({'id': item.id, 'body': '', 'compOpts': '', 'incompOpts': '', 'ends': ''});
    });
    var optionsList = '';
    var optionsInfo = '';
    var compatibleOptions = [];
    var incompatibleOptions = [];

    optionsInfo += "<div class='row'>";
    $.each(data, function (index, item) {
        optionsList += '<option value="' + item.id + '">' + item.name + '</option>';
        if(index % (12 / cardLen) == 0) {
            opts[index].body += "<div class='clearfix'></div>";
        }
        opts[index].body += "<div class='col-md-" + cardLen + "'><div class='card'><div class='card-body'>";
        opts[index].body += "<p>Name: " + item.name + "<br/>Price: " + item.price + "<br/>Connection cost: " + item.connectionCost + "</p>";

        if(item.compatibleOptions.length > 0) {
            var compatabilityText =  makeCompatibilityText(opts, opts[index].compOpts, item, true);
            opts[index].compOpts = compatabilityText.text;
            compatibleOptions = compatabilityText.arrForCompatibility;
        }

        if(item.incompatibleOptions.length > 0) {
            var incompatibilityText =  makeCompatibilityText(opts, opts[index].incompOpts, item, false);
            opts[index].incompOpts = incompatibilityText.text;
            incompatibleOptions = incompatibilityText.arrForCompatibility;
        }
        opts[index].ends = "</div></div></div>";
    });
    opts.forEach(function (item, index) {
        if(item.compOpts == '') {
            item.compOpts = "<br/><p></p>";
        }
        if(item.incompOpts == '') {
            item.incompOpts = "<br/><p></p>";
        }
        optionsInfo += item.body + "<p>" + item.compOpts + "</p><p>" + item.incompOpts + "</p>" + item.ends;
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
    tariffInfo.html("<p>Name: " + selectedTariff.text() + "<br/>Price: " + selectedTariff.attr('data-tariff-price') + "</p>");

    $.ajax({
        url:"/DeltaCom/manager/getOptionsForContract",
        contentType: "application/json",
        data: {
            "selectTariff" : tariffId
        },
        success: function(data) {
            var optionsHtml = createOptionsHtml(data, 6);
            updateSelect(selectOptions, optionsHtml.optionsList);
            availableOptions.html(optionsHtml.optionsInfo);
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