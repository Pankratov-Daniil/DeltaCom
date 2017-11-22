var userCounter = 0;
var clientsCount = 0;
var recordsInTable = 0;
var countEntriesVal;
var searchedByNumber = false;
var savedTariff = null;
var savedOptions = [];
var selectOptionsName = "#selectOptions";
var compatibleOptions = [];
var incompatibleOptions = [];
var prevSelected = [];
var curSelected = [];
var options = [];
var tariffs = [];
var countEntriesField;
var addedClient = undefined;

/**
 * Calls when document is ready. Starting page configuration.
 */
$(document).ready(function () {
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        }
    });

    countEntriesField = $("#countEntries");

    getAllOptions(saveAllOptions);
    addClickEvent('#prevButton', {}, function () {
        if(!$('#prevButton').parent().hasClass('disabled')) {
            userCounter -= recordsInTable + countEntriesVal;
            if(userCounter < 0)
                userCounter = 0;
            getClientsForTable(userCounter);
        }
    });
    addClickEvent('#nextButton', {}, function () {
        if(!$('#nextButton').parent().hasClass('disabled')) {
            getClientsForTable(userCounter);
        }
    });
    addEvent('change', "#countEntries", {}, function () {
        countEntriesVal = parseInt(countEntriesField.val(), 10);
        if(!searchedByNumber) {
            userCounter = 0;
            recordsInTable = 0;
            getClientsForTable(0);
        }
    });
    addEvent("keypress", "#numberForSearch", {}, function (event) {
        // try to prevent submitting search number form on 'enter' press
        if(event.keyCode == 13) {
            event.preventDefault();
            $("#startSearchByNumber").click();
        }
    });
    addClickEvent("#startSearchByNumber", {} ,findUserByNumber);
    addClickEvent("#resetFindUserByNumber", {}, resetFindUserByNumber);
    addClickEvent("#addNewClientBtn", {}, addNewClientOpenModal);
    addClickEvent("#submitNewUser", {}, addNewClient)

    countEntriesField.trigger('change');
});

/**
 * Calls after 'getAllOptions' ajax call
 * @param allOptions options from database
 */
function saveAllOptions(allOptions) {
    options = allOptions;
    getAllTariffs();
}

/**
 * Gets all tariffs by ajax call
 */
function getAllTariffs() {
    $.ajax({
        url: "/DeltaCom/commons/getAllTariffs",
        contentType: "application/json; charset=utf-8",
        method: "POST",
        success: function (gottenTariffs) {
            tariffs = gottenTariffs;
        }
    });
}

/**
 * Gets client list for table by ajax call
 * @param minIndex index of first client on needed page
 */
function getClientsForTable (minIndex) {
    var nextBtnPar = $("#nextButton").parent();
    var prevBtnPar = $("#prevButton").parent();
    var nextBtnDisabled = nextBtnPar.hasClass('disabled');
    var prevBtnDisabled = prevBtnPar.hasClass('disabled');
    enableOrDisableNavBtns(true, nextBtnPar, nextBtnDisabled, prevBtnPar, prevBtnDisabled);
    var countEntries = countEntriesVal + 1;
    $.ajax({
        url:"/DeltaCom/manager/getClientsForSummaryTable",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        method: "POST",
        data: {
            "startIndex" : minIndex,
            "countEntries" : countEntries
        },
        success: function (data) {
            enableOrDisableNavBtns(false, nextBtnPar, nextBtnDisabled, prevBtnPar, prevBtnDisabled);
            updateTable(data, countEntries);
        },
        error: function() {
            enableOrDisableNavBtns(false, nextBtnPar, nextBtnDisabled, prevBtnPar, prevBtnDisabled);
            notifyError("Error occurred while getting all clients. Try again later.");
        }
    });
}

function enableOrDisableNavBtns(disable, nextBtn, nextBtnDisabled, prevBtn, prevBtnDisabled) {
    if(disable) {
        if(!nextBtnDisabled) {
            nextBtn.addClass('disabled');
        }
        if(!prevBtnDisabled) {
            prevBtn.addClass('disabled');
        }
    } else {
        if(nextBtnDisabled) {
            nextBtn.removeClass('disabled');
        }
        if(prevBtnDisabled) {
            prevBtn.removeClass('disabled');
        }
    }
}

/**
 * Finds user by entered number
 */
function findUserByNumber() {
    var numberForSearchInput = $("#numberForSearch");
    var form = $("#searchNumberForm");
    if (!form[0].checkValidity() || numberForSearchInput.val() == '') {
        form[0].trigger('submit');
        return;
    }
    var tableBody = $("#tableBody");
    var found = false;
    // try to find number on page
    $("#tableBody .contractNum").each(function () {
        if ($(this).text() == numberForSearchInput.val()) {
            found = true;
            var savedStr = "<tr>" + $(this).closest("tr").html() + "</tr>";
            tableBody.html(savedStr);
            $('#nextButton').parent().attr('class', 'disabled');
            $('#prevButton').parent().attr('class', 'disabled');
            searchedByNumber = true;
            return false;
        }
    });
    if (!found) {
        $.ajax({
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            url: "/DeltaCom/manager/searchClientByNumber",
            method: "POST",
            data: {"number" : numberForSearchInput.val()},
            success: function (data) {
                tableBody.empty();
                searchedByNumber = true;
                userCounter = 0;
                updateTable([data], 2);
            },
            error: function() {
                notifyError("Error occurred while searching client by number. Try again later.");
            }
        });
    }
}

/**
 * Calls on find number reset button pressed
 */
function resetFindUserByNumber() {
    $("#numberForSearch").val('');
    searchedByNumber = false;
    countEntriesField.trigger('change');
}

/**
 * Updates clients table on page
 * @param data clients data
 */
function updateTable (data, countEntries) {
    var tableRecords = '';
    var tableBody = $("#tableBody");
    recordsInTable = 0;

    $('#nextButton').parent().attr('class', (data.length < countEntries ? 'disabled' : ''));
    $('#prevButton').parent().attr('class', (userCounter == 0 ? 'disabled' : ''));
    $.each(data, function (index, item) {
        if(index < countEntries - 1) {
            userCounter++;
            recordsInTable++;

            tableRecords += '<tr>';
            tableRecords += '<th>'+ userCounter + '</th>';
            tableRecords += '<th>' + item.firstName + ' ' + item.lastName + '</th>';
            tableRecords += '<th>' + (new Date(item.birthDate)).toLocaleDateString() + '</th>';
            tableRecords += '<th>' + item.passport + '</th>';
            tableRecords += '<th>' + item.address + '</th>';
            tableRecords += '<th>' + item.email + '</th>';

            tableRecords += '<th>';
            $.each(item.contracts, function (contractIndex, contract) {
                tableRecords += getContractAsHtml(contract);
            });
            tableRecords += '<a id="' + item.id + '" class="btn btn-primary btn-sm addContract" href="javascript:void(0);">Add new contract</a>';
            tableRecords +='</th>';

            tableRecords += '<th>';
            tableRecords += '<a id="' + item.id + '" class="deleteClient btn btn-warning" href="javascript:void(0);">';
            tableRecords += '<i class="fa fa-lg fa-trash"></i></a>';
            tableRecords += '</th>';

            tableRecords += '</tr>';
        }
    });
    addClickEvent(".deleteClient", {}, deleteClient);
    addClickEvent(".manageContract", {"addContract" : false}, onOpenContractManager);
    addClickEvent(".addContract", {"addContract" : true}, onOpenContractManager);

    tableBody.html(tableRecords);

    if(addedClient != undefined) {
        var addContractStr = ".addContract#" + addedClient;
        $(addContractStr).trigger('click');
        addedClient = undefined;
    }
}

function deleteClient() {
    var onErrorFunc = function () {
        notifyError("Error occurred while removing client.");
    };
    $.ajax({
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        url: '/DeltaCom/manager/deleteClient',
        method: "POST",
        data: {"clientId" : parseInt($(this).attr('id'))},
        success: function (data) {
            if(data != '') {
                onErrorFunc();
                return;
            }
            notifySuccess("Client successfully removed.");
            userCounter -= 1;
            var oldUserCnt = userCounter;
            userCounter -= countEntriesVal * parseFloat('0.' + ((userCounter/countEntriesVal) + '').split ('.') [1]);
            if((oldUserCnt - userCounter) % countEntriesVal == 0) {
                userCounter -= countEntriesVal;
            }
            getClientsForTable(userCounter);
        },
        error: onErrorFunc
    });
}

function getContractAsHtml(contract) {
    var tableRecords = '';
    tableRecords += '<div class="btn-group">';
    tableRecords += '<a id="blockContractBtn' + contract.id + '" class="btn btn-sm btn-' + (contract.blocked ? 'danger' : 'default') + ' dropdown-toggle contractNum" href="javascript:void(0);" data-toggle="dropdown">';
    tableRecords += contract.numbersPool.number;
    tableRecords += '<span class="caret"></span></a>';
    tableRecords += '<ul class="dropdown-menu">';
    tableRecords += '<li><a id="' + contract.id + '" class="manageContract" href="javascript:void(0);"><i class="fa fa-pencil fa-fw"></i> Edit contract</a></li>';
    tableRecords += '<li class="divider"></li>';
    tableRecords += '<li><a id="blockContractLink' + contract.id + '" href="javascript:void(0);">';
    tableRecords += (contract.blocked ? '<i class="fa fa-unlock fa-fw"></i> Unblock contract' : '<i class="fa fa-ban fa-fw"></i> Block contract');
    tableRecords += '</a></li>';
    tableRecords += '<li><a id="deleteContractLink' + contract.id + '" href="javascript:void(0);"><i class="fa fa-trash-o fa-fw"></i> Delete contract</a></li>';
    tableRecords += '</ul>';
    tableRecords += '</div>';
    tableRecords += '<p></p>';
    addClickEvent('#deleteContractLink' + contract.id, {}, function () {
        deleteContract({'btnId' : '#blockContractBtn'+contract.id, 'contractId' : contract.id});
    });
    addClickEvent('#blockContractLink'+contract.id, {}, function () {
        blockContract(contract.id, !contract.blocked, true, onSuccessfullBlock,
            {'btnId' : '#blockContractBtn'+contract.id,
                'linkId' : '#blockContractLink'+contract.id,
                'blocked' : !contract.blocked,
                'contract' : contract
            });
    });
    return tableRecords;
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
    var onErrorFunc = function() {
        notifyError("Error occurred while blocking contract. Try again later.");
    };
    $.ajax({
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        url: '/DeltaCom/manager/blockContract',
        data: {'contractId': contractId,
            'block' : block,
            'blockByOperator' : blockByOperator},
        method: "POST",
        success: function(data) {
            if(data != '') {
                onErrorFunc();
                return;
            }
            funcOnSuccess(successData);
            notifySuccess("Contract successfully " + (block ? "blocked" : "unblocked") + ".");
        },
        error: onErrorFunc
    });
}

/**
 * Deletes contract by ajax call
 * @param data
 */
function deleteContract(data) {
    var onErrorFunc = function() {
        notifyError("Error occurred while removing contract.");
    };
    $.ajax({
        contentType: "application/json; charset=utf-8",
        url: '/DeltaCom/manager/deleteContract',
        method: "POST",
        data: JSON.stringify(data.contractId),
        success: function(body) {
            if(body != '') {
                onErrorFunc();
                return;
            }
            $(data.btnId).parent().remove();
            notifySuccess("Contract successfully removed.");
        },
        error: onErrorFunc
    });
}

/**
 * Opens 'add new client' modal window. Calls when 'add client' button pressed
 */
function addNewClientOpenModal() {
    // clear form
    $("#resetNewUser").trigger('click');
    $("#addNewClientModal").modal('show');
}

/**
 * Adds new client by ajax call
 */
function addNewClient(event) {
    var form = $("#newUserForm");
    if(!form[0].checkValidity()) {
        form[0].trigger('submit');
        return false;
    }
    event.preventDefault();

    var client = {
        "id" : 0,
        "firstName" : $("#firstNameField").val(),
        "lastName" : $("#lastNameField").val(),
        "birthDate" : $("#birthDateField").val(),
        "passport" : $("#passportField").val(),
        "address" : $("#addressField").val(),
        "email" : $("#emailField").val(),
        "password" : '',
        "accessLevels" : ["1"],
        "isActivated" : false,
        "forgottenPassToken" : null,
        "openIdToken" : null,
        "twoFactorAuth" : false,
        "smsCode" : null
    };

    var button = $(this);
    button.prop('disabled', 'true');
    var onErrorFunc = function() {
        notifyError("Error occurred while adding client. Try again later.");
    };
    $.ajax({
        contentType: "application/json; charset=utf-8",
        url: '/DeltaCom/commons/regNewClient',
        method: "POST",
        data: JSON.stringify(client),
        success: function(data) {
            if(data != '') {
                onErrorFunc();
                return;
            }
            getClientsCount(afterAddingClient);
            getClientIdByEmail(client.email);
            notifySuccess("Client successfully added.");
        },
        error: onErrorFunc
    });
    button.removeAttr('disabled');
    $("#addNewClientModal").modal('hide');
}

function getClientIdByEmail(email) {
    $.ajax({
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        url: '/DeltaCom/manager/getClientIdByEmail',
        method: "POST",
        async: false,
        data: {"email" : email},
        success: function (clientId) {
            addedClient = clientId;
        }
    });
}

/**
 * Gets client count
 * @param funcOnSuccess function calls on success
 */
function getClientsCount(funcOnSuccess) {
    $.ajax({
        contentType: "application/json; charset=utf-8",
        url: '/DeltaCom/manager/getClientsCount',
        method: "POST",
        success: function (maxClients) {
            clientsCount = maxClients;
            funcOnSuccess();
        }
    });
}

/**
 * Function calls after getting client count
 */
function afterAddingClient() {
    var neededCount = countEntriesVal * parseFloat('0.' + ((clientsCount/countEntriesVal) + '').split ('.') [1]);
    if(neededCount == 0) {
        userCounter = clientsCount - countEntriesVal;
        getClientsForTable(clientsCount - countEntriesVal);
    } else {
        userCounter = clientsCount - neededCount;
        getClientsForTable(clientsCount - neededCount);
    }
}

/**
 * Calls when contract manager opens
 * @param addNewContract true if need to add new contract, false if change contract
 */
function prepareContractModal(addNewContract) {
    var showOnCreateContractElems = $(".showOnCreateContract");
    var hideOnCreateContractElems = $(".hideOnCreateContract");

    if(addNewContract) {
        showOnCreateContractElems.removeClass('hidden');
        showOnCreateContractElems.prop('required');
        hideOnCreateContractElems.addClass('hidden');
    } else {
        showOnCreateContractElems.addClass('hidden');
        showOnCreateContractElems.removeAttr('required');
        hideOnCreateContractElems.removeClass('hidden');
    }
}

/**
 * Gets contract by number and pass it to 'funcOnSuccess'
 * @param funcOnSuccess function called on success
 */
function getContractByNumber(number, funcOnSuccess) {
    $.ajax({
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        url: "/DeltaCom/commons/getContractByNumber",
        data: {"number": number},
        method: "POST",
        success: function (data) {
            funcOnSuccess(data);
        },
        error: function () {
            notifyError("Error occurred while getting contract by number. Try again later.");
        }
    });
}

/**
 * Calls when click on 'manage contract' or 'add new contract' button
 */
function onOpenContractManager(event) {
    prepareContractModal(event.data.addContract);

    var curTariff = $("#curTariff");
    var number = $(this).closest("div").children(".contractNum").text();
    $("#numberModal").val(number);

    savedTariff = null;
    savedOptions = [];
    prevSelected = [];
    curSelected = [];

    // if changing contract
    if(!event.data.addContract) {
        getContractByNumber(number, function (contract) {
            savedTariff = contract.tariff;
            var curTariffHtml = "<p>Tariff name: " + contract.tariff.name + "<br/>" +
                "Options: ";
            contract.options = idsToObjectInOptionsCompatibilityArr(contract.options, options);
            $.each(contract.options, function (index, item) {
                curTariffHtml += item.name;
                if (index < contract.options.length - 1)
                    curTariffHtml += ", ";
                savedOptions.push(item.id);
            });
            curTariff.html(curTariffHtml + "</p>");
            passTariffsToModal();
            addClickEvent("#applyContractBtn", {}, onChangeContract);
        });
    } else {
        getUnusedNumbers($(this).attr('id'));
        if(tariffs.length > 0) {
            savedTariff = tariffs[0];
            passTariffsToModal();
        }
        $("#selectOptions").selectpicker('deselectAll');
    }
    $("#manageContractModal").modal('show');
}

/**
 * Passes tariffs to modal window
 */
function passTariffsToModal() {
    var tariffInfo = $("#tariffInfo");
    var availableOptions = $("#availableOptions");
    var selectTariff = $("#selectTariff");
    var selectOptions = $("#selectOptions");
    var tariffHtml = '';

    $.each(tariffs, function (index, item) {
        tariffHtml += '<option data-tariff-price="' + item.price + '" value="' + item.id + '">' + item.name + '</option>';
    });
    updateSelect(selectTariff, tariffHtml);
    selectTariff.selectpicker('val', savedTariff.id);
    if(tariffs.length > 0) {
        savedTariff.options = idsToObjectInOptionsCompatibilityArr(savedTariff.options, options);
        savedTariff.options.forEach(function (option) {
            option.compatibleOptions = idsToObjectInOptionsCompatibilityArr(option.compatibleOptions, options);
            option.incompatibleOptions = idsToObjectInOptionsCompatibilityArr(option.incompatibleOptions, options);
        });
        var optionsHtml = createOptionsHtml(savedTariff.options, 6);
        compatibleOptions = (optionsHtml.compatibleOptions);
        incompatibleOptions = (optionsHtml.incompatibleOptions);
        updateSelect(selectOptions, optionsHtml.optionsList);

        $.each(savedOptions, function (index, item) {
            var values = $('#selectOptions [value=' + item + ']');
            if(values.text() != '') {
                values.prop('selected', 'true');
                curSelected.push(item);
            }
        });
        selectOptions.selectpicker('refresh');
        optsChanged();

        addEvent('change', selectOptionsName, {}, optsChanged);
        addEvent('change', "#selectTariff", {}, optionsUpdated);

        tariffInfo.html("<p>Name: " + savedTariff.name + "<br/>Price: " + savedTariff.price + "</p>");
        availableOptions.html(optionsHtml.optionsInfo);
    }
}

/**
 * Prepare clientDTO
 * @param clientId client id
 */
function prepareClient(clientId, number) {
    var selectedOptionsSelect = $("#selectOptions").val();
    var selectedOptions = [];
    selectedOptionsSelect.forEach(function (optionStr) {
        selectedOptions.push(parseInt(optionStr));
    });

    return {
        "clientId" : clientId,
        "number" : number,
        "tariffId" : parseInt($("#selectTariff").val()),
        "optionsIds" : selectedOptions
    };
}

/**
 * Checks validity of 'change contract' form and prevents submit if form is valid
 */
function checkValidityChangeContract(event) {
    var form = $("#changeContract");
    if(!form[0].checkValidity()) {
        form[0].trigger("submit");
        return false;
    }
    event.preventDefault();
    return true;
}

/**
 * Changes contract by ajax call
 */
function onChangeContract(event) {
    var onErrorFunc = function () {
        notifyError("Error occurred while changing contract. Try again later.");
    };
    if(checkValidityChangeContract(event)) {
        $.ajax({
            contentType: "application/json; charset=utf-8",
            url: "/DeltaCom/commons/changeContract",
            data: JSON.stringify(prepareClient(0, $("#numberModal").val())),
            method: "POST",
            success: function (data) {
                if(data != '') {
                    onErrorFunc();
                    return;
                }
                $("#manageContractModal").modal('hide');
                notifySuccess("Contract successfully changed.");
            },
            error: onErrorFunc
        });
    }
}

/**
 * Gets all unused numbers
 * @param clientId client id
 */
function getUnusedNumbers(clientId) {
    $.ajax({
        contentType: "application/json; charset=utf-8",
        url: "/DeltaCom/manager/getUnusedNumbers",
        method: "POST",
        success: function (numbers) {
            passNumbersToModal(numbers);
            addClickEvent("#applyContractBtn", {"clientId" : clientId}, onAddContract);
        }
    });
}

/**
 * Adds new contract by ajax call
 */
function onAddContract(event) {
    if(checkValidityChangeContract(event)) {
        var number = $("#selectNumber").val();
        var clientId = event.data.clientId;
        var onErrorFunc = function () {
            notifyError("Error occurred while adding contract. Try again later.");
        };
        $.ajax({
            contentType: "application/json; charset=utf-8",
            url: "/DeltaCom/manager/regNewContract",
            method: "POST",
            data: JSON.stringify(prepareClient(clientId, number)),
            success: function (data) {
                if(data != '') {
                    onErrorFunc();
                    return;
                }
                $("#manageContractModal").modal('hide');
                getContractByNumber(number, function (contract) {
                    $(".addContract#"+clientId).before(getContractAsHtml(contract));
                });
                notifySuccess("Contract successfully added.");
            },
            error: onErrorFunc
        });
    }
}

/**
 * Passes numbers to modal window
 * @param numbers list of numbers
 */
function passNumbersToModal(numbers) {
    var numbersHtml = '';
    numbers.forEach(function (number) {
        numbersHtml += '<option value="' + number + '">' + number + '</option>';
    });
    updateSelect($("#selectNumber"), numbersHtml);
}

function optsChanged() {
    var optChanged = optionsChanged(selectOptionsName, prevSelected, curSelected);
    prevSelected = optChanged.prevSelected;
    curSelected = optChanged.curSelected;
}

function onSuccessfullBlock(successData) {
    var removeClassBtn = 'btn-' + (successData.blocked ? 'default' : 'danger');
    var addClassBtn = 'btn-' + (successData.blocked ? 'danger' : 'default');
    var textLink = successData.blocked ? 'Unblock contract' : 'Block contract';
    $(successData.btnId).removeClass(removeClassBtn);
    $(successData.btnId).addClass(addClassBtn);
    $(successData.linkId).text(textLink);
    
    successData.contract.blocked = successData.blocked;
}
