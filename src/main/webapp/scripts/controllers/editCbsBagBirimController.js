angular.module('app').controller('EditCbsBagBirimController', function($scope, $routeParams, $location, CbsBagBirimResource , CbsBinaResource) {
    
	var self = this;
	
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cbsBagBirim = new CbsBagBirimResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CbsBagBirims/");
        };
        CbsBagBirimResource.get({CbsBagBirimId:$routeParams.CbsBagBirimId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cbsBagBirim);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cbsBagBirim.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CbsBagBirims");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CbsBagBirims");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cbsBagBirim.$remove(successCallback, errorCallback);
    };
    
    $scope.kullanimTuruList = [
        "TICARET",  
        "KONUT",  
        "DEPO",  
        "RESMI_KURUM",  
        "EGITIM",  
        "SAGLIK",  
        "DINI",  
        "ENDUSTRI",  
        "DERNEK",  
        "MUZE",  
        "SOSYO_KULTUREL",  
        "BOS"  
    ];
    $scope.kapiTuruList = [
        "ANA",  
        "TALI",  
        "DIGER"  
    ];
    
    $scope.get();
});