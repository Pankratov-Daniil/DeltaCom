var map;

$(document).ready(function () {
    var form = $("#changePasswordForm");
    form.submit(function (e) {
        e.preventDefault();
        var oldPassword = $("#oldPassword").val();
        var newPassword = $("#newPassword").val();
        var confirmPassword = $("#confirmPassword").val();

        if(newPassword != confirmPassword) {
            $.notify({
                message: "Entered passwords must be equal."
            }, {
                type: 'danger'
            });
        } else {
            $.ajax({
                headers: {
                    'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
                },
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
                error: function (data) {
                    notifyError("Changing password error.");
                }
            });
        }
    });
});

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
    var tableHTML = '<tr>';
    locations.forEach(function (location) {
        tableHTML += '<td>' + location.ipAddress + '</td>';
        tableHTML += '<td>' + location.country + '</td>';
        tableHTML += '<td>' + location.city + '</td>';
        tableHTML += '<td>' + new Date(location.enteredDate).toLocaleString() + '</td>';
    });
    tableHTML += '</tr>';
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
        var marker = new google.maps.Marker({
            position: {lat: location.latitude, lng: location.longitude},
            map: map
        });
    });
}

function notifyError(msg) {
    $.notify({
        message: msg
    }, {
        type: 'danger'
    });
}
