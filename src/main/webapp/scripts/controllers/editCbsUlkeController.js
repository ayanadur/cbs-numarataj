

angular.module('app').controller('EditCbsUlkeController', function($scope, $routeParams, $location, CbsUlkeResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cbsUlke = new CbsUlkeResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CbsUlkes");
        };
        CbsUlkeResource.get({CbsUlkeId:$routeParams.CbsUlkeId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cbsUlke);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cbsUlke.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CbsUlkes");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CbsUlkes");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cbsUlke.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});