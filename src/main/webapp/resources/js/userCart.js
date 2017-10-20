var needToSave = false;
var cart = undefined;
var contract = undefined;
var tariffs = [];
var options = [];
var curSelected = [];
var prevSelected = [];

function saveAllOptions(allOptions) {
    options = allOptions;
    getAllTariffs();
}

$(document).ready(function () {
    getCartFromSession();

    $("#changeContract").submit(function (event) {
        needToSave = false;
        removeCartFromSession();
    });

    $("#changeContractModal").on('hidden.bs.modal',function () {
        if(checkChanges()) {
            addCartToSession();
        }
    });
});

function getContractByNumber(number) {
    $.ajax({
        url: "/DeltaCom/user/getContractByNumber",
        contentType: "application/json",
        data: {
            "number" : number
        },
        success: function (data) {
            contract = data;
        },
        error: function() {
            notifyError("Error occurred while getting contract by number. Try again later.");
        }
    });
}

function getAllTariffs() {
    $.ajax({
        url: "/DeltaCom/commons/getAllTariffs",
        contentType: "application/json; charset=utf-8",
        method: "POST",
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function (data) {
            tariffs = data;
        },
        error: function() {
            notifyError("Error occurred while getting tariffs. Try again later.");
        }
    });
}

function checkChanges() {
    var allOptionsFound = true;
    var selectedTariffId = $("#selectTariff").selectpicker('val');
    var curTariffId = -1;

    if(cart != undefined) {
        allOptionsFound = findInOptions(cart.optionsIds, false);
        curTariffId = cart.tariffId;
    } else {
        if(client != undefined) {
            var curContract = $.grep(client.contracts, function (elem) {
                return elem.numbersPool.number == $("#numberModal").val();
            })[0];
            allOptionsFound = findInOptions(curContract.options, true);
            curTariffId = curContract.tariff.id;
        }
    }
    if(curTariffId == selectedTariffId && allOptionsFound) {
        return false;
    }
    return true;
}

function findInOptions(options, needId) {
    var selectedOptions = $("#selectOptions").selectpicker('val');
    if((selectedOptions != null && selectedOptions.length != options.length) || selectedOptions == null) {
        return false;
    }
    options.forEach(function (curOption) {
        var optionId = needId ? curOption.id : curOption;
        if(selectedOptions.find(function (selectedOption) {
                return optionId == selectedOption;
            }) == undefined) {
            return false;
        }
    });
    return true;
}

function addCartToSession() {
    var number = $("#numberModal").val();
    var selectedTariff = $("#selectTariff").selectpicker('val');
    var selectedOptions = $("#selectOptions").selectpicker('val');
    selectedOptions = (selectedOptions == null ? [] : selectedOptions);

    $.ajax({
        url: "/DeltaCom/user/saveCart",
        contentType: "application/json",
        data: {
            "numberModal" : number,
            "selectTariff" : selectedTariff,
            "selectOptions" : selectedOptions
        },
        success: function (data) {
            cart = (data.number == null ? undefined : data);
            getAllOptions(saveAllOptions);
            getContractByNumber(cart.number);
        },
        error: function() {
            notifyError("Error occurred while saving cart. Try again later.");
        }
    });
}

function getCartFromSession() {
    $.ajax({
        url: "/DeltaCom/user/getCart",
        contentType: "application/json",
        success: function (data) {
            cart = (data.number == null ? undefined : data);

            if(cart != undefined) {
                getContractByNumber(cart.number);
                getAllOptions(saveAllOptions);
                $("#cartLink").click(openChangeContractModal);
            }
        },
        error: function() {
            notifyError("Error occurred while getting cart. Try again later.");
        }
    });
}

function openChangeContractModal() {
    var button = $(this);
    var contractId = button.attr('data-id');

    var clientContract = (typeof client !== 'undefined') ? client.contracts.find(function (item) {
        return item.id == contractId;
    }) : undefined;

    var useCart = (cart != undefined && clientContract == undefined) || ((cart != undefined) && (clientContract != undefined) &&  (cart.number == clientContract.numbersPool.number));

    var contractModal = useCart ? contract : clientContract;
    var tariffSelect = $("#selectTariff");
    var optionsSelect = $("#selectOptions");
    var curTariffInfoDiv = $("#curTariff");
    var curTariffInfo = '<p>Name: ' + contractModal.tariff.name + '<br/>Price: ' + contractModal.tariff.price;
    $("#tariffInfo").html(curTariffInfo);
    curTariffInfo += '<br/>Selected options: ';
    contractModal.options = idsToObjectInOptionsCompatibilityArr(contractModal.options, options);
    contractModal.options.forEach(function (option, index) {
        curTariffInfo += option.name;
        if(index < contractModal.options.length - 1) {
            curTariffInfo += ', ';
        }
    });
    curTariffInfo += '</p>';
    curTariffInfoDiv.html(curTariffInfo);

    $("#numberModal").val(contractModal.numbersPool.number);
    tariffSelect.html(makeOptionsForSelect(tariffs));
    if(useCart) {
        tariffSelect.selectpicker('val', cart.tariffId);
    } else {
        tariffSelect.selectpicker('val', contractModal.tariff.id);
    }
    tariffSelect.change(optionsUpdated);

    contractModal.tariff.options = idsToObjectInOptionsCompatibilityArr(contractModal.tariff.options, options);
    contractModal.tariff.options.forEach(function (option) {
        option.compatibleOptions = idsToObjectInOptionsCompatibilityArr(option.compatibleOptions, options);
        option.incompatibleOptions = idsToObjectInOptionsCompatibilityArr(option.incompatibleOptions, options);
    });

    var optionsHtml = createOptionsHtml(contractModal.tariff.options, 6);
    updateSelect(optionsSelect, optionsHtml.optionsList);
    $('#availableOptions').html(optionsHtml.optionsInfo);

    optionsSelect.html(makeOptionsForSelect(contractModal.tariff.options));
    var opts = [];
    if(useCart) {
        opts = cart.optionsIds;
    } else {
        contractModal.options.forEach(function (option) {
            opts.push(option.id);
        });
    }
    optionsSelect.selectpicker('val', opts);
    optionsSelect.change(onOptionsSelectChange);
    optionsSelect.trigger('change');

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

function removeCartFromSession() {
    $.ajax({
        url: "/DeltaCom/user/removeCart",
        contentType: "application/json",
        success: function (data) {
            cart = undefined;
        },
        error: function() {
            notifyError("Error occurred while removing cart. Try again later.");
        }
    });
}