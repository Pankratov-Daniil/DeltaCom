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
var countEntriesField;

/**
 * Calls when document is ready. Starting page configuration.
 */
$(document).ready(function () {
    countEntriesField = $("#countEntries");

    getAllOptions(saveAllOptions);
    addClickEvent('#prevButton', {}, function () {
        if(!$('#prevButton').parent().hasClass('disabled')) {
            userCounter -= recordsInTable + countEntriesVal;
            if(userCounter < 0)
                userCounter = 0;
            updateTable(userCounter);
        }
    });
    addClickEvent('#nextButton', {}, function () {
        if(!$('#nextButton').parent().hasClass('disabled')) {
            updateTable(userCounter);
        }
    });
    addEvent('change', "#countEntries", {}, function () {
        countEntriesVal = parseInt(countEntriesField.val(), 10);
        if(!searchedByNumber) {
            userCounter = 0;
            recordsInTable = 0;
            updateTable(0);
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
}

/**
 * Updates table by ajax call
 * @param minId
 */
function updateTable (minId) {
    var countEntries = countEntriesVal + 1;
    $.ajax({
        url:"/DeltaCom/manager/getClientsForSummaryTable",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        method: "POST",
        data: {
            "startIndex" : minId,
            "countEntries" : countEntries
        },
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function (data) {
            passTableToPage(data, minId, countEntries);
        },
        error: function() {
            notifyError("Error occurred while getting all clients. Try again later.");
        }
    });
}

/**
 * Finds user by entered number
 */
function findUserByNumber() {
    var numberForSearchInput = $("#numberForSearch");
    var form = $("#searchNumberForm");
    if (!form[0].checkValidity() || numberForSearchInput.val() == '') {
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
            headers: {
                'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
            },
            success: function (data) {
                tableBody.empty();
                searchedByNumber = true;
                userCounter = 0;
                passTableToPage([data], -1, 2);
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

function passTableToPage (data, minId, countEntries) {
    var tableRecords = '';
    var tableBody = $("#tableBody");
    recordsInTable = 0;

    $('#nextButton').parent().attr('class', (data.length < countEntries ? 'disabled' : ''));
    $('#prevButton').parent().attr('class', (userCounter == 0 ? 'disabled' : ''));
    tableBody.empty();
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
                tableRecords += '<div class="btn-group">';
                tableRecords += '<a id="blockContractBtn' + contract.id + '" class="btn btn-sm btn-' + (contract.blocked ? 'danger' : 'default') + ' dropdown-toggle contractNum" href="javascript:void(0);" data-toggle="dropdown">';
                tableRecords += contract.numbersPool.number;
                tableRecords += '<span class="caret"></span></a>';
                tableRecords += '<ul class="dropdown-menu">';
                tableRecords += '<li><a class="openTariffManager" href="#">Manage tariff</a></li>';
                tableRecords += '<li class="divider"></li>';
                tableRecords += '<li><a id="';
                tableRecords += 'blockContractLink'+contract.id;
                tableRecords += '" href="';
                tableRecords += (contract.blocked ?
                    'javascript:void(0);">Unblock contract' :
                    'javascript:void(0);">Block contract');
                tableRecords += '</a></li>';
                tableRecords += '<li><a id="deleteContractLink' + contract.id + '" href="#">Delete contract</a></li>';
                tableRecords += '</ul>';
                tableRecords += '</div>';
                tableRecords += '<p></p>';
            });

            tableRecords += '<a id="addToSession'+userCounter+'" class="btn btn-primary btn-sm" href="addNewContract">Add new contract</a>';
            tableRecords +='</th>';
            tableRecords += '</tr>'
        }
        tableBody.append(tableRecords);
        tableRecords = '';

        addClickEvent('#addToSession'+userCounter, {param1: item.id}, addClientToSession);
        $.each(item.contracts, function (contractIndex, contract) {
            addClickEvent('#deleteContractLink' + contract.id, {}, function (e) {
                deleteContract({'btnId' : '#blockContractBtn'+contract.id, 'contractId' : contract.id});
            });
            addClickEvent('#blockContractLink'+contract.id,
                {}, function (e) {
                    blockContract(contract.id, !contract.blocked, true, onSuccessfullBlock,
                        {'btnId' : '#blockContractBtn'+contract.id,
                            'linkId' : '#blockContractLink'+contract.id,
                            'blocked' : !contract.blocked,
                            'contract' : contract
                        });
                });
        });
    });
    addClickEvent(".openTariffManager", {}, onOpenTariffManager);
}

function deleteContract(data) {
    $.ajax({
        contentType: "application/json",
        url: '/DeltaCom/manager/deleteContract',
        data: {'contractId': data.contractId},
        success: function() {
            $(data.btnId).parent().remove();
            notifySuccess("Contract successfully removed.");
        },
        error: function() {
            notifyError("Error occurred while removing contract.");
        }
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
        "password" : $("#passwordField").val(),
        "accessLevels" : ["1"]
    };

    var button = $(this);
    button.prop('disabled', 'true');
    $.ajax({
        contentType: "application/json; charset=utf-8",
        url: '/DeltaCom/commons/regNewClient',
        method: "POST",
        data: JSON.stringify(client),
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function() {
            if(recordsInTable < countEntriesVal) {
                userCounter -= recordsInTable;
                updateTable(userCounter);
            } else {
                getClientsCount(afterAddingClient);
            }
            notifySuccess("Client successfully added.");
        },
        error: function() {
            notifyError("Error occurred while adding client. Try again later.");
        }
    }).done(function () {
        button.removeAttr('disabled');
        $("#addNewClientModal").modal('hide');
    });
}

function getClientsCount(funcOnSuccess) {
    $.ajax({
        contentType: "application/json; charset=utf-8",
        url: '/DeltaCom/manager/getClientsCount',
        method: "POST",
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function (maxClients) {
            clientsCount = maxClients;
            funcOnSuccess();
        }
    });
}

function afterAddingClient() {
    var neededCount = countEntriesVal * parseFloat('0.' + ((clientsCount/countEntriesVal) + '').split ('.') [1]);
    if(neededCount == 0) {
        userCounter = clientsCount - countEntriesVal;
        updateTable(clientsCount - countEntriesVal);
    } else {
        userCounter = clientsCount - neededCount;
        updateTable(clientsCount - neededCount);
    }
}

function onOpenTariffManager() {
    var number = $(this).closest("div").children(".contractNum").text();
    $("#numberModal").val(number);

    var curTariff = $("#curTariff");
    var tariffInfo = $("#tariffInfo");
    var availableOptions = $("#availableOptions");
    var selectTariff = $("#selectTariff");
    var selectOptions = $("#selectOptions");

    savedTariff = null;
    savedOptions = [];
    prevSelected = [];
    curSelected = [];
    var tariffHtml = '';
    $.ajax({
        contentType: "application/json",
        url: "/DeltaCom/manager/getContractByNumber",
        data: {
            "number" : number
        },
        success: function (data) {
            savedTariff = data.tariff;
            var curTariffHtml = "<p>Tariff name: " + data.tariff.name + "<br/>" +
                                "Options: ";
            data.options = idsToObjectInOptionsCompatibilityArr(data.options, options);
            $.each(data.options, function (index, item) {
                curTariffHtml += item.name;
                if(index < data.options.length - 1)
                    curTariffHtml += ", ";
                savedOptions.push(item.id);
            });
            curTariff.html(curTariffHtml + "</p>");
        },
        error: function() {
            notifyError("Error occurred while getting contract by number. Try again later.");
        }
    }).done(function () {
        $.ajax({
            url: "/DeltaCom/commons/getAllTariffs",
            contentType: "application/json; charset=utf-8",
            method: "POST",
            headers: {
                'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
            },
            success: function (data) {
                $.each(data, function (index, item) {
                    tariffHtml += '<option data-tariff-price="' + item.price + '" value="' + item.id + '">' + item.name + '</option>';
                });
                updateSelect(selectTariff, tariffHtml);
                selectTariff.selectpicker('val', savedTariff.id);
                if(data.length > 0) {
                    $.ajax({
                        url: "/DeltaCom/commons/getOptionsForTariff",
                        contentType: "application/json; charset=utf-8",
                        method: "POST",
                        data: JSON.stringify(savedTariff.id),
                        headers: {
                            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
                        },
                        success: function (optionsData) {
                            var optionsHtml = createOptionsHtml(prepareOptions(optionsData), 6);
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
                        },
                        error: function () {
                            notifyError("Error occurred while getting options for tariff. Try again later.");
                            selectOptions.empty();
                            availableOptions.empty();
                        }
                    });
                }
            },
            error: function() {
                notifyError("Error occurred while getting all tariffs. Try again later.");
            }
        });
    });
    $(manageTariffModal).modal('show');
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
