

angular.module('app').controller('EditCbsIlController', function($scope, $routeParams, $location, CbsIlResource , CbsUlkeResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cbsIl = new CbsIlResource(self.original);
            CbsUlkeResource.queryAll(function(items) {
                $scope.cbsUlkeSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.adi
                    };
                    if($scope.cbsIl.cbsUlke && item.id == $scope.cbsIl.cbsUlke.id) {
                        $scope.cbsUlkeSelection = labelObject;
                        $scope.cbsIl.cbsUlke = wrappedObject;
                        self.original.cbsUlke = $scope.cbsIl.cbsUlke;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/CbsIls");
        };
        CbsIlResource.get({CbsIlId:$routeParams.CbsIlId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cbsIl);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cbsIl.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CbsIls");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CbsIls");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cbsIl.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("cbsUlkeSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.cbsIl.cbsUlke = {};
            $scope.cbsIl.cbsUlke.id = selection.value;
        }
    });
    
    $scope.get();
});