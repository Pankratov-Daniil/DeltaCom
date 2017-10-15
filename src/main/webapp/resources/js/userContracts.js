var options = [];
var client = null;
var prevSelected = [];
var curSelected = [];

$(document).ready(function () {
    $("#changeContract").submit(function (event) {
        $("#numberModal").removeAttr('disabled');
        $(changeContractModal).modal('hide');
    });
});

getAllOptions(saveAllOptions);

function saveAllOptions(allOptions) {
    options = allOptions;
    getAllTariffs();
    getClient();
}

function getAllTariffs() {
    $.ajax({
        url: "/DeltaCom/commons/getAllTariffs",
        contentType: "application/json",
        success: function (data) {
            tariffs = data;
        }
    });
}

function getClient() {
    $.ajax({
        url: "/DeltaCom/user/getCurrentClient",
        contentType: "application/json",
        success: function (data) {
            client = data;
            updateContractsTable();
        }
    });
}

function updateContractsTable() {
    var tableBody = $("#contractsTableBody");
    var tableRecords = '';

    client.contracts.forEach(function (contract) {
        tableRecords += '<tr' +  (contract.blockedByOperator ? ' style="background: red"' :
            (contract.blocked ? ' style="background: grey"' : '')) + '>';
        tableRecords += '<th>' + contract.numbersPool.number + '</th>';
        tableRecords += '<th>' + contract.tariff.name + '</th>';
        tableRecords += '<th>';
        tableBody.html(tableRecords);
        contract.tariff.options.forEach(function (option) {
            option.compatibleOptions = idsToObjectInOptionsCompatibilityArr(option.compatibleOptions, options);
            option.incompatibleOptions = idsToObjectInOptionsCompatibilityArr(option.incompatibleOptions, options);
        });
        contract.options = idsToObjectInOptionsCompatibilityArr(contract.options, options);
        contract.options.forEach(function (option) {
            tableRecords += option.name + "<br/>";
        });
        tableRecords += '</th>';
        tableRecords += '<th>' + contract.balance + '</th>';
        tableRecords += '<th>' +
            (contract.blocked ? '' : '<a href="javascript:void(0);" id="changeContractButton" data-id="'+contract.id+'" class="btn btn-success">Change contract</a><p></p>') +
            (contract.blockedByOperator ? '' : '<a href="javascript:void(0);" id="blockButton" data-id="'+contract.id+'" class="btn btn-' + (contract.blocked ? 'success"> Unblock' : 'danger">Block') + '</a>') +
            '</th>';
        tableRecords += '</tr>';

            tableBody.html(tableRecords);

        if(!contract.blocked) {
            $("#changeContractButton").click(openChangeContractModal);
        }
        if(!contract.blockedByOperator) {
            $("#blockButton").click(blockContract);
        }
    });


}

function openChangeContractModal() {
    var button = $(this);
    var contractId = button.attr('data-id');
    var contract = client.contracts.find(function (item) {
        return item.id == contractId;
    });
    var tariffSelect = $("#selectTariff");
    var optionsSelect = $("#selectOptions");
    var curTariffInfoDiv = $("#curTariff");
    var curTariffInfo = '<p>Name: ' + contract.tariff.name + '<br/>Price: ' + contract.tariff.price +
        '<br/>Selected options: ';
    contract.options.forEach(function (option, index) {
        curTariffInfo += option.name;
        if(index < contract.options.length - 1) {
            curTariffInfo += ', ';
        }
    });
    curTariffInfo += '</p>';
    curTariffInfoDiv.html(curTariffInfo);

    $("#numberModal").val(contract.numbersPool.number);
    tariffSelect.html(makeOptionsForSelect(tariffs));
    tariffSelect.selectpicker('val', contract.tariff.id);
    tariffSelect.change(optionsUpdated);

    var optionsHtml = createOptionsHtml(contract.tariff.options, 6);
    updateSelect(optionsSelect, optionsHtml.optionsList);
    $('#availableOptions').html(optionsHtml.optionsInfo);

    optionsSelect.html(makeOptionsForSelect(contract.tariff.options));
    var opts = [];
    contract.options.forEach(function (option) {
        opts.push(option.id);
    });
    optionsSelect.selectpicker('val', opts);
    optionsSelect.change(onOptionsSelectChange);

    $("select").selectpicker('refresh');

    $("#changeContractModal").modal('show');
}

function onOptionsSelectChange() {
    var optChanged = optionsChanged("#selectOptions", prevSelected, curSelected);
    prevSelected = optChanged.prevSelected;
    curSelected = optChanged.curSelected;
    $(this).selectpicker('refresh');
}

function makeOptionsForSelect(arr) {
    var res = '';
    arr.forEach(function (item) {
        res += '<option data-tariff-price="' + item.price + '" value="' + item.id + '">' + item.name + '</option>';
    });
    return res;
}

function blockContract() {
    var button = $(this);
    var contractId = button.attr('data-id');
    var contract = client.contracts.find(function (item) {
        return item.id == contractId;
    });
    $.ajax({
        url: '/DeltaCom/user/blockContract',
        contentType: "application/json",
        data: {
            "contractId" : contractId,
            "block" : !contract.blocked,
            "blockedByOperator" : contract.blockedByOperator
        },
        success: function (data) {
            contract.blocked = !contract.blocked;
            button.parent().parent().css('background', contract.blocked ? 'grey' : '');

            if(contract.blocked) {
                $("#changeContractButton").remove();
            } else {
                button.before('<a href="javascript:void(0);" id="changeContractButton" data-id="'+contractId+'" class="btn btn-success">Change contract</a><p></p>');
                $("#changeContractButton").click(openChangeContractModal);
            }

            button.removeClass('btn-' + (contract.blocked ? 'danger' : 'success'));
            button.addClass('btn-' + (contract.blocked ? 'success' : 'danger'));
            button.text(contract.blocked ? 'Unblock' : 'Block');
        }
    });
}
