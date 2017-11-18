sendPreAuthCode();

function sendPreAuthCode() {
    var showSendSmsMessage = function() {
        $.notify({
            message: "We have sent sms to your phone."
        });
    };

    $.ajax({
        contentType: "application/json; charset=utf-8",
        url: "/DeltaCom/sendPreAuthCode",
        method: "POST",
        beforeSend: function(request) {
            request.setRequestHeader($('meta[name="_csrf_header"]').attr('content'), $('meta[name="_csrf"]').attr('content'));
        },
        success: showSendSmsMessage,
        error: function (data) {
            if(data != undefined && data.responseText == "time") {
                showSendSmsMessage();
            } else {
                $.notify({
                    message: "Something gone wrong and sms didn't send. Try again later."
                }, {
                    type: 'danger'
                });
            }
        }
    });
}