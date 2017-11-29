var map;
var numberSelect;
var twoFactorCheckbox;
var verificationCodeDiv;
var verificationCodeInput;
var checkCodeBtn;
var selectNumberDiv;
var confirmNumberBtn;

$(document).ready(function () {
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        }
    });
    var form = $("#changePasswordForm");
    form.submit(function (e) {
        e.preventDefault();
        onChangePasswordSubmit(form);
    });

    verificationCodeDiv = $("#verificationCodeDiv");
    verificationCodeInput = $("#verificationCodeInput");
    checkCodeBtn = $("#checkCodeBtn");
    checkCodeBtn.click(tryToChangeTwoFactorAuth);
    twoFactorCheckbox = $("#twoFactorAuthCheckbox");
    twoFactorCheckbox.prop('checked', false);
    numberSelect = $("#selectNumber");
    selectNumberDiv = $("#selectNumberDiv");
    confirmNumberBtn = $("#confirmNumberBtn");
    confirmNumberBtn.click(onConfirmNumberBtnClick);
    twoFactorCheckbox.change(onCheckboxChange);
    getClientNumbers();

});

function onChangePasswordSubmit(form) {
    var oldPassword = $("#oldPassword").val();
    var newPassword = $("#newPassword").val();
    var confirmPassword = $("#confirmPassword").val();

    if(newPassword != confirmPassword) {
        notifyError("Entered passwords must be equal.");
    } else {
        $.ajax({
            url: '/DeltaCom/user/changePassword',
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            method: "POST",
            data: {
                "oldPassword" : oldPassword,
                "newPassword" : newPassword,
                "confirmPassword" : confirmPassword
            },
            success: function (data) {
                if(data != '') {
                    notifyError(data);
                }
                form.trigger('reset');
                notifySuccess("Password successfully changed!");
            },
            error: function () {
                notifyError("Changing password error.");
            }
        });
    }
}

function getClientLocations() {
    $.ajax({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        url: '/DeltaCom/user/getLocations',
        contentType: "application/json; charset=utf-8",
        method: "POST",
        success: function (locations) {
            passLocationsToPage(locations);
            addMarkersToMap(locations);
        },
        error: function () {
            notifyError("Can't get your locations.");
        }
    });
}

function passLocationsToPage(locations) {
    var locationsTable = $("#locationsTableBody");
    var tableHTML = '';
    locations.forEach(function (location) {
        tableHTML += '<tr>';
        tableHTML += '<td>' + location.ipAddress + '</td>';
        tableHTML += '<td>' + location.country + '</td>';
        tableHTML += '<td>' + location.city + '</td>';
        tableHTML += '<td>' + new Date(location.enteredDate).toLocaleString() + '</td>';
        tableHTML += '</tr>';
    });
    locationsTable.html(tableHTML);
}

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 3,
        center: {lat: 61.698, lng: 99.505} // Russia location
    });
    $("#map").css("height", 400);
    resizeMap();
    $("a[href='#clientLocations']").on('shown.bs.tab', function(){
        resizeMap();
    });
    getClientLocations();
}

function resizeMap() {
    var newMapCenter = map.getCenter();
    google.maps.event.trigger(map, 'resize');
    map.setCenter(newMapCenter);
}

function addMarkersToMap(locations) {
    locations.forEach(function (location) {
        new google.maps.Marker({
            position: {lat: location.latitude, lng: location.longitude},
            map: map
        });
    });
}

function onCheckboxChange() {
    var checked = twoFactorCheckbox.prop('checked');
    getTwoFactorAuthStatus(function (status) {
        if(checked) {
            if(status != null && status != "") {
                confirmNumberBtn.addClass('hidden');
                hideCheckCodeDiv();
            } else {
                showSelectNumberDiv();
            }
        } else {
            if(status != null && status != "") {
                var selectDisabled = numberSelect.hasClass("disabled");
                if (selectDisabled) {
                    numberSelect.removeClass('disabled');
                    confirmNumber();
                    numberSelect.addClass('disabled');
                } else {
                    confirmNumber();
                }
            } else {
                hideSelectNumberDiv();
                hideCheckCodeDiv();
            }
        }
    });
}

function getClientNumbers() {
    $.ajax({
        url: '/DeltaCom/user/getClientNumbers',
        contentType: "application/json; charset=utf-8",
        method: "POST",
        success: function (numbers) {
            numberSelect.removeAttr('disabled');
            var numbersHTML = '';
            numbers.forEach(function (number) {
                numbersHTML += "<option value='" + number + "'>" + number + "</option>";
            });
            numberSelect.html(numbersHTML);
            getTwoFactorAuthStatus(function (status) {
                if(status != null && status != "") {
                    twoFactorCheckbox.prop('checked', true);
                    numberSelect.val(status);
                    numberSelect.attr('disabled', true);
                    selectNumberDiv.removeClass('hidden');
                    confirmNumberBtn.addClass('hidden');
                } else {
                    twoFactorCheckbox.prop('checked', false);
                }
            });
        },
        error: function () {
            twoFactorCheckbox.prop('checked', false);
            notifyError("Can't get your contracts.");
        }
    });
}

function showSelectNumberDiv() {
    selectNumberDiv.removeClass('hidden');
    confirmNumberBtn.removeClass('hidden');
}

function hideSelectNumberDiv() {
    selectNumberDiv.addClass('hidden');
    confirmNumberBtn.addClass('hidden');
}

function hideCheckCodeDiv() {
    verificationCodeInput.val('');
    verificationCodeDiv.addClass('hidden');
}

function onConfirmNumberBtnClick() {
    confirmNumberBtn.addClass('hidden');
    numberSelect.attr('disabled');
    confirmNumber();
}

function confirmNumber() {
    $.ajax({
        url: '/DeltaCom/user/confirmNumber',
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        method: "POST",
        data: {
            'number' : numberSelect.val()
        },
        success: function (data) {
            notify("Sms with verification code was sent to your phone.");
            verificationCodeDiv.removeClass('hidden');
        },
        error: function (data) {
            if(data.responseText == "time") {
                verificationCodeDiv.removeClass('hidden');
            } else {
                notifyError("Can't send verification sms.");
            }
        }
    });
}

function getTwoFactorAuthStatus(funcOnSuccess) {
    $.ajax({
        url: '/DeltaCom/user/getTwoFactorAuthStatus',
        contentType: "application/json; charset=utf-8",
        method: "POST",
        success: function (data) {
            funcOnSuccess(data);
        },
        error: function () {
            notifyError("Can't get two factor authorization status.");
            funcOnSuccess(undefined);
        }
    });
}

function tryToChangeTwoFactorAuth() {
    numberSelect.removeAttr('disabled');
    $.ajax({
        url: '/DeltaCom/user/changeTwoFactorAuth',
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        method: "POST",
        data: {
            'number' : numberSelect.val(),
            'smsCode' : verificationCodeInput.val()
        },
        success: function () {
            hideCheckCodeDiv();
            var enabled = twoFactorCheckbox.prop('checked');
            if(enabled) {
                numberSelect.prop('disabled');
            } else {
                hideSelectNumberDiv();
            }
            notify("You have successfully " + (enabled ? "enabled" : "disabled") + " two factor authorization.");
        },
        error: function () {
            notifyError("Can't change two factor auth status.");
        }
    });
}

function notify(msg) {
    $.notify({
        message: msg
    });
}

function notifyError(msg) {
    $.notify({
        message: msg
    }, {
        type: 'danger'
    });
}
