var ids = [];
var pointerIds = 0;
var userCounter = 0;
var recordsInTable = 0;
var countEntriesVal;

$(document).ready(function () {
    countEntriesVal = parseInt($("#countEntries").val(),10);
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

    $("#countEntries").change(function () {
        countEntriesVal = parseInt($(this).val(),10);
        ids = [];
        pointerIds = -1;
        userCounter = 0;
        recordsInTable = 0;
        updateTable(-1);
    });
});

function updateTable (minId) {
    var countEntries = countEntriesVal + 1;
    jQuery.ajax({
        url:"/DeltaCom/manager/getClientsForSummaryTable",
        contentType: "application/json",
        data: {
            "startId" : minId,
            "countEntries" : countEntries
        },
        success: function(data) {
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
                    var contractCount = 0;
                    $.each(item.contracts, function (contractIndex, contract) {
                        tableRecords += '<div class="btn-group">';
                        tableRecords += '<a class="btn btn-sm btn-' + (contract.blocked ? 'danger' : 'primary') + ' dropdown-toggle" href="javascript:void(0);" data-toggle="dropdown">';
                        tableRecords += contract.numbersPool.number;
                        tableRecords += '<span class="caret"></span></a>';
                        tableRecords += '<ul class="dropdown-menu">';
                        tableRecords += '<li><a href="#">Manage tariff</a></li>';
                        tableRecords += '<li class="divider"></li>';
                        tableRecords += '<li><a id="';
                        tableRecords += 'blockContract'+contractCount;
                        tableRecords += '" href="';
                        tableRecords += (contract.blocked ?
                            'javascript:void(0);">Unblock contract' :
                            'javascript:void(0);">Block contract');
                        tableRecords += '</a></li>';
                        tableRecords += '</ul>';
                        tableRecords += '</div>';
                        tableRecords += '<p></p>';
                        contractCount++;
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
                contractCount = 0;
                $.each(item.contracts, function (contractIndex, contract) {
                    $('#blockContract'+contractCount).click({param1: contract.id, param2: !contract.blocked, param3: '#blockContract'+contractCount}, blockContract);
                    contractCount++;
                });

            });
        }
    });
}

function addClientToSession (event) {
    $.ajax({
        url: 'addNewClientIdToSession',
        data: {'clientId': event.data.param1}
    });
}

function blockContract (event) {
    $.ajax({
        url: '/DeltaCom/manager/blockContract',
        data: {'contractId': event.data.param1,
               'block' : event.data.param2},
        success: function(data) {
            $(event.data.param3).text(event.data.param2 ? 'Unblock contract' : 'Block contract');
            $(event.data.param3).parent().parent().parent().children(".btn").removeClass('btn-' + (event.data.param2 ? 'primary' : 'danger'))
            $(event.data.param3).parent().parent().parent().children(".btn").addClass('btn-' + (event.data.param2 ? 'danger' : 'primary'));
        }
    });
}
