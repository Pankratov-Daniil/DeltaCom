var dataVersion = -100;
var app = angular.module('advStand', ['ui.bootstrap']);

app.controller('TariffsController', function($scope, $http, $interval) {
    $scope.carouselInterval = 2000;
    $scope.carouselActiveSlide = 0;
    $scope.tariffs = [];
    var dataToFunc = {http : $http, scope : $scope};
    updateTariffs(dataToFunc);
    $interval(updateTariffs, 20000, 0, true, dataToFunc);
});

function updateTariffs(data) {
    data.http.post('/AdvertisingStand/tariffs/getDataVersion').
        then(function onDataSuccess(resp) {
                if(dataVersion != resp.data) {
                    dataVersion = resp.data;
                    data.http.post('/AdvertisingStand/tariffs/getTariffs').
                        then(function onSuccess(response) {
                        data.scope.tariffs = response.data;
                        }, function onErr(response) {
                            //alert(response.status);
                        })
                }
            },
            function onDataErr(resp) {
                //alert(resp.status);
            }
        );
}
