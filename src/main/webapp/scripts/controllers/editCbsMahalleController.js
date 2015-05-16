

angular.module('app').controller('EditCbsMahalleController', function($scope, $routeParams, $location, CbsMahalleResource , CbsIlceResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cbsMahalle = new CbsMahalleResource(self.original);
            CbsIlceResource.queryAll(function(items) {
                $scope.cbsIlceSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.adi
                    };
                    if($scope.cbsMahalle.cbsIlce && item.id == $scope.cbsMahalle.cbsIlce.id) {
                        $scope.cbsIlceSelection = labelObject;
                        $scope.cbsMahalle.cbsIlce = wrappedObject;
                        self.original.cbsIlce = $scope.cbsMahalle.cbsIlce;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/CbsMahalles");
        };
        CbsMahalleResource.get({CbsMahalleId:$routeParams.CbsMahalleId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cbsMahalle);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cbsMahalle.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CbsMahalles");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CbsMahalles");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cbsMahalle.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("cbsIlceSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.cbsMahalle.cbsIlce = {};
            $scope.cbsMahalle.cbsIlce.id = selection.value;
        }
    });
    
    $scope.get();
});