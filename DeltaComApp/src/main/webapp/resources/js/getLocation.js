onStart();

function onStart() {
    var location = undefined;
    console.log("Getting user location...");
    $.getJSON("https://freegeoip.net/json/", function(data) {
        location = data;
        if(location != undefined) {
            console.log('Got location: ' + location);
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                url: "/DeltaCom/setLocation",
                method: "POST",
                async: false,
                beforeSend: function (request) {
                    request.setRequestHeader($('meta[name="_csrf_header"]').attr('content'), $('meta[name="_csrf"]').attr('content'));
                },
                data: {
                    "latitude": location.latitude,
                    "longitude" : location.longitude,
                    "city" : location.city,
                    "country" : location.country_name,
                    "ip" : location.ip
                }
            });
        }
    }).always(function () {
        window.location.replace("/DeltaCom/process-login");
    });
}
