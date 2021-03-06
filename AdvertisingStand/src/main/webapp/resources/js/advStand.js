var app = angular.module('advStand', ['ngAnimate']);

app.controller('TariffsController', function($scope, $http, $interval, $timeout) {
    $scope.tariffSwapInterval = 8000;
    $scope.tariffs = [];
    $scope.index = 0;
    $scope.isStart = true;
    $scope.iter = 0;
    var dataToFunc = {http : $http, scope : $scope, timeout: $timeout};
    var uri = "ws://" + document.location.host + "/tariffSubscription";
    var webSocket = new WebSocket(uri);

    webSocket.onopen = function (evt) { console.log("Connected to tariffSubscription socket!") };
    webSocket.onmessage = function (evt) { updateTariffs(dataToFunc); };
    updateTariffs(dataToFunc);

    $interval(function() {
        $scope.iter = ($scope.iter + 1) % $scope.tariffs.length;
        $scope.index = -1;
        $timeout(function () {
            if($scope.tariffs.length > 0) {
                $scope.index = $scope.tariffs[$scope.iter].id;
            }
        }, 500);
    }, $scope.tariffSwapInterval);
});

function updateTariffs(data) {
    data.http.post('/AdvertisingStand/tariffs/getTariffs')
        .then(function (response) {
                response.data.forEach(function (item) {
                    item.price = item.price.toLocaleString('ru-RU', {style: 'currency', currency : 'RUB'});
                });
                data.scope.tariffs = response.data;
                data.scope.index = response.data.length > 0 ? response.data[0].id : -1;
                data.scope.iter = 0;
                data.scope.isStart = true;
                data.timeout(function () {
                    data.scope.isStart = false;
                }, 601);
                console.log("Tariffs updated!");
            },
            function (response) {
                console.log("Error while getting tariffs: " + response.status);
            });
}
