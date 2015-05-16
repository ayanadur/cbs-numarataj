
angular.module('app').controller('NewCbsUlkeController', function ($scope, $location, locationParser, CbsUlkeResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cbsUlke = $scope.cbsUlke || {};
    //alert("new ülke");
    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CbsUlkes/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CbsUlkeResource.save($scope.cbsUlke, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CbsUlkes");
    };
});