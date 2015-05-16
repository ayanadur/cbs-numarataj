

angular.module('app').controller('EditCbsSokakController', function($scope, $routeParams, $location, CbsSokakResource , CbsMahalleResource) {
    
	var self = this;
    
	$scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cbsSokak = new CbsSokakResource(self.original);
            CbsMahalleResource.queryAll(function(items) {
                $scope.cbsMahalleSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.adi
                    };
                    if($scope.cbsSokak.cbsMahalle && item.id == $scope.cbsSokak.cbsMahalle.id) {
                        $scope.cbsMahalleSelection = labelObject;
                        $scope.cbsSokak.cbsMahalle = wrappedObject;
                        self.original.cbsMahalle = $scope.cbsSokak.cbsMahalle;
                    }
                    return labelObject;
                });
            });
            
        };
        var errorCallback = function() {
            $location.path("/CbsSokaks");
        };
        CbsSokakResource.get({CbsSokakId:$routeParams.CbsSokakId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cbsSokak);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cbsSokak.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CbsSokaks");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CbsSokaks");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cbsSokak.$remove(successCallback, errorCallback);
    };
    
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
    $scope.$watch("cbsMahalleSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.cbsSokak.cbsMahalle = {};
            $scope.cbsSokak.cbsMahalle.id = selection.value;
        }
    });
    
    $scope.get();
});