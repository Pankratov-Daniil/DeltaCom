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