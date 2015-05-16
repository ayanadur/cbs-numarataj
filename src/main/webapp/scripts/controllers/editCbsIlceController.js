

angular.module('app').controller('EditCbsIlceController', function($scope, $routeParams, $location, CbsIlceResource , CbsIlResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cbsIlce = new CbsIlceResource(self.original);
            CbsIlResource.queryAll(function(items) {
                $scope.cbsIlSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.adi
                    };
                    if($scope.cbsIlce.cbsIl && item.id == $scope.cbsIlce.cbsIl.id) {
                        $scope.cbsIlSelection = labelObject;
                        $scope.cbsIlce.cbsIl = wrappedObject;
                        self.original.cbsIl = $scope.cbsIlce.cbsIl;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/CbsIlces");
        };
        CbsIlceResource.get({CbsIlceId:$routeParams.CbsIlceId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cbsIlce);
    };

    $scope.save = function() {
        var successCallback = function(){
            //$scope.get();
        	$location.path("/CbsIlces");
            $scope.displayError = false;
           
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cbsIlce.$update(successCallback, errorCallback);
        
    };

    $scope.cancel = function() {
        $location.path("/CbsIlces");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CbsIlces");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cbsIlce.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("cbsIlSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.cbsIlce.cbsIl = {};
            $scope.cbsIlce.cbsIl.id = selection.value;
        }
    });
    
    $scope.get();
});