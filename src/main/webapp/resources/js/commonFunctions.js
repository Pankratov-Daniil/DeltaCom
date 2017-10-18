/**
 * Adds client id to session  (for new contract)
 * @param event
 */
function addClientToSession (event) {
    $.ajax({
        url: 'addNewClientIdToSession',
        data: {'clientId': event.data.param1},
        error: function() {
            notifyError("Error occurred while adding client to session.");
        }
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
        error: function() {
            notifyError("Error occurred while blocking contract. Try again later.");
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
            return false;
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
 * @returns {{text: *}} text for option compatibility and where we writes new compatibility options id
 */
function makeCompatibilityText(arr, curText, curOption, compatible) {
    var text = curText;
    var curOptOptions = compatible ? curOption.compatibleOptions : curOption.incompatibleOptions;

    // for each (in)compatible option
    $.each(curOptOptions, function (compatIndex, option) {
        // if this option is on a page
        if(option != undefined && seachInOptsArray(arr, option.id) != null) {
            text += (text == '' ? ((compatible ? "Comes with: " : "Incompatible with: ") + option.name) : ", "+option.name);
        }
    });
    return {"text" : text};
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
            var compatibilityText =  makeCompatibilityText(opts, opts[index].compOpts, item, true);
            opts[index].compOpts = compatibilityText.text;
            compatibleOptions.push({'id' : item.id, 'compatibleOptions' : item.compatibleOptions});
        }

        if(item.incompatibleOptions.length > 0) {
            var incompatibilityText =  makeCompatibilityText(opts, opts[index].incompOpts, item, false);
            opts[index].incompOpts = incompatibilityText.text;
            incompatibleOptions.push({'id' : item.id, 'compatibleOptions' : item.incompatibleOptions});
        }
        opts[index].ends = "</div></div></div>";
    });
    opts.forEach(function (item) {
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

function findDeselectedCompatibleOptionLinks(selectOptionsName, deselectedOptionId) {
    var removedOption = $.grep(options, function (item) {
        return item.id == deselectedOptionId;
    })[0];
    options.forEach(function (option) {
        if(option.compatibleOptions.find(function(item) { return removedOption.id == item.id;}) != undefined) {
            if($(selectOptionsName + " [value='" + option.id + "']").prop('selected')) {
                $(selectOptionsName + " [value='" + option.id + "']").removeAttr('selected');
                findDeselectedCompatibleOptionLinks(selectOptionsName, option.id);
            }
        }
    });
}

function disableOption(option) {
    option.removeAttr('selected');
    option.prop('disabled', 'true');
}

function disableIncompatibleOptions(select) {
    $.each(select.children("option:selected"), function (index, item) {
        var foundOption = $.grep(options, function (option) {
            return item.value == option.id;
        })[0];
        foundOption.incompatibleOptions.forEach(function (incompOption) {
            disableOption(select.children("option[value='" + incompOption.id + "']"));
            options.forEach(function (opt) {
                if(opt.compatibleOptions.find(function(elem) {return elem.id == incompOption.id;}) != undefined) {
                    disableOption(select.children("option[value='" + opt.id + "']"));
                }
                if(opt.incompatibleOptions.find(function (elem) { return elem.id == item.value;}) != undefined) {
                    disableOption(select.children("option[value='" + opt.id + "']"));
                }
            });
        });
    });
}

function getCurSelected(select) {
    return Array.from(select.children("option:selected")).map(function (item) { return parseInt(item.value); });
}

function selectCompatible(select) {
    $.each(select.children("option:selected"), function (index, item) {
        var foundOption = $.grep(options, function (option) {
            return item.value == option.id;
        })[0];
        foundOption.compatibleOptions.forEach(function (compOption) {
            select.children("option[value='" + compOption.id + "']").prop('selected', 'true');
        });
    });
}

function optionsChanged(selectOptionsName, prevSelected, curSelected) {
    var select = $(selectOptionsName);
    prevSelected = curSelected;
    curSelected = getCurSelected(select);

    // if element deselected
    if(curSelected.length < prevSelected.length) {
        var removedOptionId = $.grep(prevSelected, function (prevItem) {
            return (curSelected.indexOf(prevItem) < 0);
        })[0];
        options.forEach(function (option) {
            if(option.compatibleOptions.find(function (item) { return item.id == removedOptionId;})) {
                select.children("option[value='" + option.id + "']").removeAttr('selected');
            }
        });
        curSelected = getCurSelected(select);
    }
    selectCompatible(select);
    $.each(select.children("option:disabled"), function (index, item) {
        item.disabled = false;
    });
    disableIncompatibleOptions(select);

    curSelected = getCurSelected(select);

    $(selectOptionsName).selectpicker('refresh');
    return {"prevSelected" : prevSelected,
        "curSelected" : curSelected};
}

function updateOptions(selectOptions, selectedTariff, tariffInfo, availableOptions, tariffId) {
    tariffInfo.html("<p>Name: " + selectedTariff.text() + "<br/>Price: " + selectedTariff.attr('data-tariff-price') + "</p>");

    $.ajax({
        url:"/DeltaCom/commons/getOptionsForTariff",
        contentType: "application/json",
        data: {
            "selectTariff" : tariffId
        },
        success: function(data) {
            data = prepareOptions(data);
            var optionsHtml = createOptionsHtml(data, 6);
            updateSelect(selectOptions, optionsHtml.optionsList);
            availableOptions.html(optionsHtml.optionsInfo);
            compatibleOptions = optionsHtml.compatibleOptions;
            incompatibleOptions = optionsHtml.incompatibleOptions;
        },
        error: function () {
            notifyError("Error occurred while getting options for tariff. Try again later.");
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

function updateSelect(sel, data) {
    sel.html(data);
    sel.selectpicker('refresh');
}

function getAllOptions(functionOnSuccess) {
    $.ajax({
        url: "/DeltaCom/commons/getAllOptions",
        contentType: "application/json",
        success: function (data) {
            functionOnSuccess(prepareOptions(data));
        },
        error: function() {
            notifyError("Error occurred while getting all options. Try again later.");
        }
    });
}

function prepareOptions(options) {
    options = findAllObjectsInOptions(options, []);
    options.forEach(function (option) {
        option.compatibleOptions = idsToObjectInOptionsCompatibilityArr(option.compatibleOptions, options);
        option.incompatibleOptions = idsToObjectInOptionsCompatibilityArr(option.incompatibleOptions, options);
    });
    return options;
}

function findAllObjectsInOptions(arr, prevArr) {
    var resultArr = [];
    $.each(arr, function (index, item) {
        if(typeof item == 'object') {
            resultArr.push(item);

            var findInComp = findAllObjectsInOptions(item.compatibleOptions, resultArr);
            var findInIncomp = findAllObjectsInOptions(item.incompatibleOptions, resultArr);

            if(findInComp && findInComp.length > 0)
                resultArr.push.apply(resultArr, findInComp);
            if(findInIncomp && findInIncomp.length > 0)
                resultArr.push.apply(resultArr, findInIncomp);
        }
    });
    prevArr.push.apply(prevArr, resultArr);
    arr.forEach(function (option, index) {
        if(typeof option == 'number') {
            var foundItem = $.grep(prevArr, function (item) {
                return option == item.id;
            })[0];
            if(foundItem != undefined) {
                arr[index] = foundItem;
            }
        }
    });
    return arr;
}

function idsToObjectInOptionsCompatibilityArr(arr, allOptions) {
    var resultArr = arr;
    arr.forEach(function (option, index) {
        if(typeof option == 'number') {
            var optionObject = $.grep(allOptions, function (item) {
                return item.id == option;
            })[0];
            arr[index] = optionObject;
        }
    });
    return resultArr;
}

function onOptionsSelectChange(select) {
    prevSelected = curSelected;
    curSelected = getCurSelected(select);
    // if element deselected
    if(curSelected.length < prevSelected.length) {
        var removedOptionId = $.grep(prevSelected, function (prevItem) {
            return (curSelected.indexOf(prevItem) < 0);
        })[0];
        options.forEach(function (option) {
            if(option.compatibleOptions.find(function (item) { return item.id == removedOptionId;})) {
                select.children("option[value='" + option.id + "']").removeAttr('selected');
            }
        });
        curSelected = getCurSelected(select);
    }
}

function notifyError(msg) {
    $.notify({
        message: msg
    }, {
        type: 'danger'
    });
}