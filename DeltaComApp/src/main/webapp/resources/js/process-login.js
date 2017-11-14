$(document).ready(function () {
    var form = $("#sendPasswordLinkForm");
    form.submit(function (e) {
        var button = $("#resetPassBtn");
        button.addClass('disabled');
        e.preventDefault();
        $.ajax({
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            url: form.attr('action'),
            method: "POST",
            data: {
                "email" : $("#sendPassEmail").val()
            },
            headers: {
                'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
            },
            success: function(data, textStatus, xhr) { afterSentPassword(data, textStatus, xhr, form, button); },
            error: function (data, textStatus, xhr) { afterSentPassword(data, textStatus, xhr, form, button); }
        });
    });
});

function afterSentPassword(data, textStatus, xhr, form, button) {
    if(data == "OK") {
        $.notify({
            message: "Reset password link successfully sent."
        });
        form[0].reset();
        $("#backToLoginLink").trigger('click');
    } else {
        $.notify({
            message: data.responseText
        }, {
            type: 'danger'
        });
    }
    button.removeClass('disabled');
}