var cart = undefined;
var contract = undefined;
var tariffs = [];
var options = [];
var curSelected = [];
var prevSelected = [];
var client = null;

$(document).ready(function () {
    getCartFromSession();
    addEvent('submit', "#changeContract", {}, changeContract);
    addEvent('hidden.bs.modal', "#changeContractModal", {}, function () {
        if(checkChanges()) {
            addCartToSession();
        }
    });
    getClient();
});

/**
 * Gets current client
 */
function getClient(funcOnSuccess) {
    $.ajax({
        url: "/DeltaCom/user/getCurrentClient",
        contentType: "application/json; charset=utf-8",
        method: "POST",
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function (data) {
            client = data;
            if(funcOnSuccess != undefined) {
                funcOnSuccess();
            }
        },
        error: function() {
            notifyError("Error occurred while getting user. Try again later.");
        }
    });
}

function saveAllOptions(allOptions) {
    options = allOptions;
    getAllTariffs();
}

function getContractByNumber(number) {
    $.ajax({
        url: "/DeltaCom/commons/getContractByNumber",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: {
            "number" : number
        },
        method: "POST",
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function (data) {
            contract = data;
        },
        error: function() {
            notifyError("Error occurred while getting contract by number. Try again later.");
        }
    });
}

/**
 * Gets all tariffs
 */
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
    return !(curTariffId == selectedTariffId && allOptionsFound);
}

/**
 * Finds if option exists by id
 */
function findInOptions(options, needId) {
    var selectedOptions = $("#selectOptions").selectpicker('val');
    if((selectedOptions != null && selectedOptions.length != options.length) || (selectedOptions == null && options.length != 0)) {
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

/**
 * Adds cart to session
 */
function addCartToSession() {
    var number = $("#numberModal").val();
    var selectedTariff = $("#selectTariff").selectpicker('val');
    var selectedOptions = $("#selectOptions").selectpicker('val');
    selectedOptions = (selectedOptions == null ? [] : selectedOptions);

    cart = {
        "number" : number,
        "tariffId" : selectedTariff,
        "optionsIds" : selectedOptions
    };

    $.ajax({
        url: "/DeltaCom/user/saveCart",
        contentType: "application/json; charset=utf-8",
        method: "POST",
        data: JSON.stringify(cart),
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function () {
            getAllOptions(saveAllOptions);
            getContractByNumber(cart.number);
            if($("#cartContainer").length == 0) {
                $(".top-nav").prepend('<li id="cartContainer"><a href="javascript:void(0);" id="cartLink"><i class="fa fa-shopping-basket"></i></a></li>');
                addClickEvent("#cartLink", {}, openChangeContractModal);
            }
            notifySuccess("Changes saved to cart.");
        },
        error: function() {
            cart = undefined;
            notifyError("Error occurred while saving cart. Try again later.");
        }
    });
}

/**
 * Gets cart from session
 */
function getCartFromSession() {
    $.ajax({
        url: "/DeltaCom/user/getCart",
        contentType: "application/json; charset=utf-8",
        method: "POST",
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function (data) {
            cart = (data.number == undefined ? undefined : data);

            if(cart != undefined) {
                getContractByNumber(cart.number);
                getAllOptions(saveAllOptions);
                addClickEvent("#cartLink", {}, openChangeContractModal);
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
    var useCart = (cart != undefined ? true : (clientContract != undefined ? false : null));
    if(useCart == null) {
        return;
    }
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
    addEvent('change', "#selectOptions", {}, onOptionsSelectChange);
    optionsSelect.trigger('change');
    addClickEvent("#applyContract", {}, changeContract);
    $("select").selectpicker('refresh');
    $("#changeContractModal").modal('show');
}

/**
 * Changes contract
 */
function changeContract(event) {
    var form = $("#changeContract");
    if(!form[0].checkValidity()) {
        form[0].trigger('submit');
        return false;
    }
    event.preventDefault();

    var selectedOptionsSelect = $("#selectOptions").val();
    var selectedOptions = [];
    selectedOptionsSelect.forEach(function (optionStr) {
        selectedOptions.push(parseInt(optionStr));
    });
    var contractDTO = {
        "clientId" : client.id,
        "number" : $("#numberModal").val(),
        "tariffId" : parseInt($("#selectTariff").val()),
        "optionsIds" : selectedOptions
    };

    $.ajax({
        url: "/DeltaCom/commons/changeContract",
        contentType: "application/json; charset=utf-8",
        method: "POST",
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        data: JSON.stringify(contractDTO),
        success: function () {
            $("#changeContractModal").modal('hide');
            getClient();
            removeCartFromSession();
            notifySuccess("Contract successfully changed.");
        },
        error: function () {
            notifyError("Error occurred while removing cart. Try again later.");
        }
    });
}

function onOptionsSelectChange() {
    var optChanged = optionsChanged("#selectOptions", prevSelected, curSelected);
    prevSelected = optChanged.prevSelected;
    curSelected = optChanged.curSelected;
    $(this).selectpicker('refresh');
}

/**
 * Makes html from option array
 */
function makeOptionsForSelect(arr) {
    var res = '';
    arr.forEach(function (item) {
        res += '<option data-tariff-price="' + item.price + '" value="' + item.id + '">' + item.name + '</option>';
    });
    return res;
}

/**
 * Removes cart from session
 */
function removeCartFromSession() {
    $.ajax({
        url: "/DeltaCom/user/removeCart",
        contentType: "application/json; charset=utf-8",
        method: "POST",
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        success: function () {
            $("#cartContainer").remove();
            cart = undefined;
        },
        error: function() {
            notifyError("Error occurred while removing cart. Try again later.");
        }
    });
}