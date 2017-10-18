var ids = [];
var pointerIds = 0;
var userCounter = 0;
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

$(document).ready(function () {
    getAllOptions(saveAllOptions);
    var countEntries = $("#countEntries");
    countEntriesVal = parseInt(countEntries.val(),10);
    updateTable(-1);

    $('#prevButton').click(function () {
        if(!$(this).parent().hasClass('disabled')) {
            if(pointerIds > 0) {
                pointerIds--;
            }
            userCounter -= recordsInTable + countEntriesVal;
            if(userCounter < 0)
                userCounter = 0;
            updateTable(ids[pointerIds]);
        }
    });

    $('#nextButton').click(function () {
        if(!$(this).parent().hasClass('disabled')) {
            if (pointerIds != ids.length - 1) {
                pointerIds++;
            }
            updateTable(ids[pointerIds]);
        }
    });

    countEntries.change(function () {
        countEntriesVal = parseInt($(this).val(), 10);
        if(!searchedByNumber) {
            ids = [];
            pointerIds = -1;
            userCounter = 0;
            recordsInTable = 0;
            updateTable(-1);
        }
    });

    $("#numberForSearch").keypress(function (event) {
        // try to prevent submitting search number form on 'enter' press
        if(event.keyCode == 13) {
            event.preventDefault();
            $("#startSearchByNumber").click();
        }
    });

    $("#startSearchByNumber").click(findUserByNumber);
    $("#resetFindUserByNumber").click(resetFindUserByNumber);
});

function saveAllOptions(allOptions) {
    options = allOptions;
}

function updateTable (minId) {
    var countEntries = countEntriesVal + 1;
    $.ajax({
        url:"/DeltaCom/manager/getClientsForSummaryTable",
        contentType: "application/json",
        data: {
            "startId" : minId,
            "countEntries" : countEntries
        },
        success: function (data) {
            passTableToPage(data, minId, countEntries);
        },
        error: function() {
            notifyError("Error occurred while getting all clients. Try again later.");
        }
    });
}

function findUserByNumber() {
    var nubmerForSearchInput = $("#numberForSearch");
    if (!$("#searchNumberForm")[0].checkValidity() || nubmerForSearchInput.val() == '') {
        return;
    }
    var tableBody = $("#tableBody");
    var found = false;
    $("#tableBody .contractNum").each(function () {
        if ($(this).text() == nubmerForSearchInput.val()) {
            found = true;
            var savedStr = "<tr>" + $(this).closest("tr").html() + "</tr>";
            tableBody.html(savedStr);
            $('#nextButton').parent().attr('class', 'disabled');
            $('#prevButton').parent().attr('class', 'disabled');
            searchedByNumber = true;
        }
    });

    if (!found) {
        tableBody.empty();
        $.ajax({
            contentType: "application/json",
            url: "/DeltaCom/manager/searchClientByNumber",
            data: {
                "number" : nubmerForSearchInput.val()
            },
            success: function (data) {
                searchedByNumber = true;
                userCounter = 0;
                ids = [];
                passTableToPage([data], -1, 2);
            },
            error: function() {
                notifyError("Error occurred while searching client by number. Try again later.");
            }
        });
    }
}

function resetFindUserByNumber() {
    $("#numberForSearch").val('');
    searchedByNumber = false;
    ids = [];
    userCounter = 0;
    recordsInTable = 0;
    updateTable(-1);
}

function passTableToPage (data, minId, countEntries) {
    var tableRecords = '';
    var tableBody = $("#tableBody");
    var firstId = ids.length > 0 ? ids[0] : -1;
    recordsInTable = 0;

    $('#nextButton').parent().attr('class', (data.length < countEntries ? 'disabled' : ''));
    $('#prevButton').parent().attr('class', (firstId == minId ? 'disabled' : ''));
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
                tableRecords += '</ul>';
                tableRecords += '</div>';
                tableRecords += '<p></p>';
            });

            tableRecords += '<a id="addToSession'+userCounter+'" class="btn btn-primary btn-sm" href="addNewContract">Add new contract</a>';
            tableRecords +='</th>';
            tableRecords += '</tr>'
        }
        if((index == 0 || index == countEntries - 1) && $.inArray(item.id, ids) < 0) {
            ids.push(item.id);
        }
        tableBody.append(tableRecords);
        tableRecords = '';
        $('#addToSession'+userCounter).click({param1: item.id}, addClientToSession);
        $.each(item.contracts, function (contractIndex, contract) {
            $('#blockContractLink'+contract.id).click(function () {
                blockContract(contract.id, !contract.blocked, true, onSuccessfullBlock,
                    {'btnId' : '#blockContractBtn'+contract.id,
                        'linkId' : '#blockContractLink'+contract.id,
                        'blocked' : !contract.blocked,
                        'contract' : contract
                    });
            });
        });
    });

    $(".openTariffManager").click(onOpenTariffManager);
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
            contentType: "application/json",
            url: "/DeltaCom/commons/getAllTariffs",
            success: function (data) {
                $.each(data, function (index, item) {
                    tariffHtml += '<option data-tariff-price="' + item.price + '" value="' + item.id + '">' + item.name + '</option>';
                });
                updateSelect(selectTariff, tariffHtml);
                selectTariff.selectpicker('val', savedTariff.id);
                if(data.length > 0) {
                    $.ajax({
                        url: "/DeltaCom/commons/getOptionsForTariff",
                        contentType: "application/json",
                        data: {
                            "selectTariff": savedTariff.id
                        },
                        success: function (optionsData) {
                            optionsData = prepareOptions(optionsData);

                            var optionsHtml = createOptionsHtml(optionsData, 6);
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

                            $(selectOptionsName).change(optsChanged);
                            selectTariff.change(optionsUpdated);

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
