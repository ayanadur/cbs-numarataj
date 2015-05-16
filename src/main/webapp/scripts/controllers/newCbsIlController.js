
angular.module('app').controller('NewCbsIlController', function ($scope, $location, locationParser, CbsIlResource , CbsUlkeResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cbsIl = $scope.cbsIl || {};
    
    $scope.cbsUlkeList = CbsUlkeResource.queryAll(function(items){
        $scope.cbsUlkeSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.adi
            });
        });
    });
    $scope.$watch("cbsUlkeSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.cbsIl.cbsUlke = {};
            $scope.cbsIl.cbsUlke.id = selection.value;
        }
    });
    
    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CbsIls/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CbsIlResource.save($scope.cbsIl, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CbsIls");
    };
});