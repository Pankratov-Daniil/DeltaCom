$(document).ready(function () {
    var button = $("#changePassBtn");
    var form = $("#changePassForm");
    form.submit(function (e) {
        var password = $("#password").val();
        e.preventDefault();
        button.addClass('disabled');
        $.ajax({
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            url: "/DeltaCom/resetPassword",
            method: "POST",
            beforeSend: function(request) {
                request.setRequestHeader($('meta[name="_csrf_header"]').attr('content'), $('meta[name="_csrf"]').attr('content'));
            },
            data: {
                "password" : password,
                "token" : $('meta[name="token"]').attr('content')
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