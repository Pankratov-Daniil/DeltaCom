var options = [];
var prevSelected = [];
var curSelected = [];
var tariffs = [];

$(document).ready(function () {
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        }
    });
    getAllOptions(saveAllOptions);
});

function saveAllOptions(allOptions) {
    options = allOptions;
    getAllTariffs();
    getClient();
}

function updateContractsTable() {
    var tableBody = $("#contractsTableBody");
    var tableRecords = '';

    client.contracts.forEach(function (contract) {
        tableRecords += '<tr' +  (contract.blockedByOperator ? ' style="background: #B22222"' :
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
            addClickEvent(".changeContractButton", {}, openChangeContractModal);
        }
        if(!contract.blockedByOperator) {
            addClickEvent(".blockButton", {}, blockContract);
        }
    });


}

function blockContract() {
    var button = $(this);
    var contractId = button.attr('data-id');
    var contract = client.contracts.find(function (item) {
        return item.id == contractId;
    });
    var onErrorFunc = function() {
        notifyError("Error occurred while blocking contract. Try again later.");
    };
    $.ajax({
        url: '/DeltaCom/user/blockContract',
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        method: "POST",
        data: {
            "contractId" : contractId,
            "block" : !contract.blocked,
            "blockedByOperator" : contract.blockedByOperator
        },
        success: function (data) {
            if(data != '') {
                onErrorFunc();
                return;
            }
            contract.blocked = !contract.blocked;
            button.parent().parent().css('background', contract.blocked ? 'grey' : '');

            if(contract.blocked) {
                $(".changeContractButton[data-id='" + contractId + "']").remove();
                removeCartFromSession();
            } else {
                button.before('<a href="javascript:void(0);" data-id="'+contractId+'" class="btn btn-success changeContractButton">Change contract</a><p></p>');
                addClickEvent(".changeContractButton", {}, openChangeContractModal);
            }

            button.removeClass('btn-' + (contract.blocked ? 'danger' : 'success'));
            button.addClass('btn-' + (contract.blocked ? 'success' : 'danger'));
            button.text(contract.blocked ? 'Unblock' : 'Block');

            notifySuccess("Contract successfully " + (contract.blocked ? "blocked" : "unblocked") + ".");
        },
        error: onErrorFunc
    });
}