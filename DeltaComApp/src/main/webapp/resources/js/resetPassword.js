$(document).ready(function () {
    var button = $("#changePassBtn");
    button.click(function () {
        var password = $("#password").val();
        button.addClass('disabled');
        $.ajax({
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            url: "/DeltaCom/resetPassword",
            method: "POST",
            data: {
                "password" : password,
                "token" : $('meta[name="token"]').attr('content')
            },
            headers: {
                'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
            },
            success: function(data, textStatus, xhr) { afterChangePassword(data, textStatus, xhr, button); },
            error: function (data, textStatus, xhr) { afterChangePassword(data, textStatus, xhr, button); }
        });
    });
});

function afterChangePassword(data, textStatus, xhr, button) {
    if(data.responseText == "OK") {
        $.notify({
            message: "Password successfully changed."
        });
        window.location.replace("/");
    } else {
        $.notify({
            message: data.responseText
        }, {
            type: 'danger'
        });
    }
    button.removeClass('disabled');
}