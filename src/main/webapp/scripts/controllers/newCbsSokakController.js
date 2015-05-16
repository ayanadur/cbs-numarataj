
angular.module('app').controller('NewCbsSokakController', function ($scope, $location, locationParser, CbsSokakResource , CbsMahalleResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cbsSokak = $scope.cbsSokak || {};
    
    $scope.turuList = [
        "CADDE",
        "SOKAK",
        "MEYDAN",
        "BULVAR"
    ];
    
    $scope.kaplamaTuruList = [
        "PARKE",
        "ASFALT",
        "STABLIZE",
        "TOPRAK",
        "CAKIL",
        "BETON",
        "TAS",
        "DIGER"
    ];
    
    $scope.cbsMahalleList = CbsMahalleResource.queryAll(function(items){
        $scope.cbsMahalleSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.adi
            });
        });
    });
    
    $scope.$watch("cbsMahalleSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.cbsSokak.cbsMahalle = {};
            $scope.cbsSokak.cbsMahalle.id = selection.value;
        }
    });
    
    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CbsSokaks/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CbsSokakResource.save($scope.cbsSokak, successCallback, errorCallback);
        
    };
    
    $scope.cancel = function() {
        $location.path("/CbsSokaks");
    };
});