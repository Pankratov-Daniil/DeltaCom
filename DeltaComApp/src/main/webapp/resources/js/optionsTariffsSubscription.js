var tariffsStompClient = null;
var optionsStompClient = null;

var SUBSCRIPTIONS = {
    TARIFFS : {id : 0},
    OPTIONS : {id : 1}
};

$(document).ready(function () {
    subscribeToOptions();
    subscribeToTariffs();
});



function subscribeToOptions() {
    subscribe("/subscrToOptions", "/topic/options", optionsStompClient, SUBSCRIPTIONS.OPTIONS);
}

function subscribeToTariffs() {
    subscribe("/subscrToTariffs", "/topic/tariffs", tariffsStompClient, SUBSCRIPTIONS.TARIFFS);
}

function unsubscribeTariffs() {
    tariffsStompClient.disconnect();
}

function unsubscribeOptions() {
    optionsStompClient.disconnect();
}

function subscribe(uri, topic, stompClient, subscriptionType) {
    var socket = new SockJS("/DeltaCom" + uri);
    stompClient = Stomp.over(socket);

    var connectCallback = function() {
        //alert("connected!");
        stompClient.subscribe(topic, function(data){
            var parsedData = JSON.parse(data.body);
            if (subscriptionType == SUBSCRIPTIONS.OPTIONS && typeof options !== "undefined") {
                options = prepareOptions(parsedData);
            } else {
                if (subscriptionType == SUBSCRIPTIONS.TARIFFS && typeof tariffs !== "undefined") {
                    tariffs = parsedData;
                }
            }
        });
    };

    var errorCallback = function(error) {
        //alert(error);
    };

    var headers = {};
    headers['X-CSRF-TOKEN'] = $('meta[name="_csrf"]').attr('content');
    stompClient.connect(headers, connectCallback, errorCallback);
}
