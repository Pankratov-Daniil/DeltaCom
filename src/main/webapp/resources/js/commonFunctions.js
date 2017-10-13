/**
 * Adds client id to session  (for new contract)
 * @param event
 */
function addClientToSession (event) {
    $.ajax({
        url: 'addNewClientIdToSession',
        data: {'clientId': event.data.param1}
    });
}

/**
 * Try to block contract
 * @param contractId contract id
 * @param block true if need to block, false otherwise
 * @param blockByOperator is blocked by operator
 * @param funcOnSuccess what to do on success block
 * @param successData parameters for funcOnSuccess
 */
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

/**
 * Searches item by id in array
 * @param arr where we should find item
 * @param id id of item
 * @returns found item or null if didn't find
 */
function seachInOptsArray(arr, id) {
    var foundItem = null;
    arr.forEach(function (item, index, array) {
        if(item.id == id) {
            foundItem = item;
        }
    });
    return foundItem;
}

/**
 * Makes text for options compatibility (like:"Comes with: option2, ..." or "Incompatible with: Opt4,..."
 * @param arr options array
 * @param curText current option compatibility text
 * @param curOption current option
 * @param compatible if text for compatible options
 * @returns {{text: *, arrForCompatibility: Array}} text for option compatibility and where we writes new compatibility options id
 */
function makeCompatibilityText(arr, curText, curOption, compatible) {
    var text = curText;
    var arrForCompatibility = [];
    var curOptOptions;
    if(compatible) {
        curOptOptions = curOption.compatibleOptions;
    } else {
        curOptOptions = curOption.incompatibleOptions;
    }
    // for each (in)compatible option
    $.each(curOptOptions, function (compatIndex, option) {
        // if this option is on a page
        var foundCompOption = seachInOptsArray(arr, option.id);
        if(foundCompOption != null) {
            text += (text == '' ? ((compatible ? "Comes with: " : "Incompatible with: ") + option.name) : ", "+option.name);
            arrForCompatibility.push([curOption.id, option.id]);
            if(compatible) {
                foundCompOption.compOpts += (foundCompOption.compOpts == '' ? "Comes with: " + curOption.name : ", " + curOption.name);
            } else {
                foundCompOption.incompOpts += (foundCompOption.incompOpts == '' ? "Incompatible with: " + curOption.name : ", " + curOption.name);
            }
        }
    });
    return {"text" : text, "arrForCompatibility" : arrForCompatibility};
}

/**
 * creates options for select, options for info, comp. and incomp. options
 * @param data list of options
 * @param cardLen length of card (like: "col-md-" + cardLen)
 * @returns {{optionsList: string, optionsInfo: string, compatibleOptions: Array, incompatibleOptions: Array}}
 */
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

/**
 * Fires when option changed in select users contract options
 * @returns {{prevSelected: (*|jQuery), curSelected: (*|jQuery)}}
 */
function optionsChanged(selectOptionsName, prevSelected, curSelected, compatibleOptions, incompatibleOptions) {
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
                    if (prevSelected) {
                        $.each(prevSelected, function (indexPrev, itemPrev) {
                            if (curSelected.indexOf(itemPrev) < 0) {
                                deletedVal = itemPrev;
                                return false;
                            }
                        });
                    }
                    // check if option was deselect
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
    });
}

/**
 * Fires when tariff changes
 */
function optionsUpdated() {
    curSelected = [];
    prevSelected = [];
    updateOptions($('#selectOptions'), $('#selectTariff option:selected'), $('#tariffInfo'), $('#availableOptions'), $('#selectTariff').val());
}

/**
 * Check if array have two elem in [elem1, elem2]
 * @param array find in this array
 * @param item1 first item to compare
 * @param item2 second item to compare
 * @returns {boolean} true if exits, false otherwise
 */
function checkContains(array, item1, item2) {
    return array.some(elem => (item1 != item2)&&
        ((elem[0] == item1 && elem[1] == item2) ||
            (elem[0] == item2 && elem[1] == item1)));
}

function updateSelect(sel, data) {
    sel.html(data);
    sel.selectpicker('refresh');
}