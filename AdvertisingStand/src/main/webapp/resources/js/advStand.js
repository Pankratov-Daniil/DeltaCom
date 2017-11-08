var app = angular.module('advStand', ['ui.bootstrap']);

app.controller('TariffsController', function($scope, $http) {
    $scope.carouselInterval = 2000;
    $scope.carouselActiveSlide = 0;
    $scope.tariffs = [];
    var dataToFunc = {http : $http, scope : $scope};
    var uri = "ws://" + document.location.host + document.location.pathname + "tariffSubscription";
    var webSocket = new WebSocket(uri);

    webSocket.onopen = function (evt) { console.log("Connected to tariffSubscription socket!") };
    webSocket.onmessage = function (evt) { updateTariffs(dataToFunc); };
    updateTariffs(dataToFunc);
});

function updateTariffs(data) {
    data.http.post('/AdvertisingStand/tariffs/getTariffs')
        .then(function (response) {
                response.data.forEach(function (item) {
                    item.price = item.price.toLocaleString('ru-RU', {style: 'currency', currency : 'RUB'});
                });
                data.scope.tariffs = response.data;
                console.log("Tariffs updated!");
            },
            function (response) {
                console.log("Error while getting tariffs: " + response.status);
            });
}
