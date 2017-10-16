var options = [];
var client = null;
var prevSelected = [];
var curSelected = [];
var tariffs = [];

getAllOptions(saveAllOptions);

function saveAllOptions(allOptions) {
    options = allOptions;
    getAllTariffs();
    getClient();
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
        contract.tariff.options = idsToObjectInOptionsCompatibilityArr(contract.tariff.options, options);
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
            (contract.blocked ? '' : '<a href="javascript:void(0);" data-id="'+contract.id+'" class="btn btn-success changeContractButton">Change contract</a><p></p>') +
            (contract.blockedByOperator ? '' : '<a href="javascript:void(0);" data-id="'+contract.id+'" class="blockButton btn btn-' + (contract.blocked ? 'success"> Unblock' : 'danger">Block') + '</a>') +
            '</th>';
        tableRecords += '</tr>';

            tableBody.html(tableRecords);

        if(!contract.blocked) {
            $(".changeContractButton").click(openChangeContractModal);
        }
        if(!contract.blockedByOperator) {
            $(".blockButton").click(blockContract);
        }
    });


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
                $(".changeContractButton[data-id='" + contractId + "']").remove();
                removeCartFromSession();
            } else {
                button.before('<a href="javascript:void(0);" data-id="'+contractId+'" class="btn btn-success changeContractButton">Change contract</a><p></p>');
                $(".changeContractButton").click(openChangeContractModal);
            }

            button.removeClass('btn-' + (contract.blocked ? 'danger' : 'success'));
            button.addClass('btn-' + (contract.blocked ? 'success' : 'danger'));
            button.text(contract.blocked ? 'Unblock' : 'Block');
        }
    });
}
