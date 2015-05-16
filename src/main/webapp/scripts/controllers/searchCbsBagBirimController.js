angular.module('app').controller('SearchCbsBagBirimController', function($scope,  $window, $routeParams, $resource, $location, locationParser, CbsBagBirimResource ) {

	var self = this;
	
	//ilk çağrıldığında alan setlenir.
	$scope.binaId = $routeParams.CbsBinaId;
	$scope.LastId;
	
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
    
    $scope.performSearch = function() {
        $scope.searchResults = CbsBagBirimResource.queryAll({CbsBinaId:$scope.binaId}, function(){
        	
        	//version kontrolu için son işlem yapılan kaydı bul
        	for (var i=0; i<$scope.searchResults.length;i++) {
        		if($scope.searchResults[i].id == $scope.LastId) {
	                $scope.cbsBagBirim.id = $scope.searchResults[i].id;
	                $scope.cbsBagBirim.version = $scope.searchResults[i].version;
        		}
        	}
        });
    };
    
    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cbsBagBirim);
    };
    
    $scope.newKayit = function() {
    	$scope.cbsBagBirim = {};
        $scope.cbsBagBirim.cbsBina = {};
		$scope.cbsBagBirim.cbsBina.id = $scope.binaId;
    };
    
    $scope.get = function(id) {
        var successCallback = function(data){
            self.original = data;
            $scope.cbsBagBirim = data; //new CbsBagBirimResource(self.original);
        };
        var errorCallback = function() {
            //$location.path("/CbsBagBirims/");
        };
        CbsBagBirimResource.query({CbsBagBirimId:id}, successCallback, errorCallback);
    };
    
    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
        	
        	//son işlem yapılan id
            $scope.LastId = locationParser(responseHeaders);
            $scope.displayError = false;

            $scope.performSearch();
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        $scope.cbsBagBirim.id = null;
        $scope.cbsBagBirim.cbsBina = {};
		$scope.cbsBagBirim.cbsBina.id = $scope.binaId;
		
        CbsBagBirimResource.save($scope.cbsBagBirim, successCallback, errorCallback);
    };
    
    $scope.update = function() {
    	$scope.LastId = $scope.cbsBagBirim.id;
    	
        var successCallback = function(){
            $scope.displayError = false;
            $scope.performSearch();
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        CbsBagBirimResource.update($scope.cbsBagBirim, successCallback, errorCallback); 
    };
    
    $scope.remove = function(id) {
        var deleteRow = $window.confirm('Bagımsız birim kaydı silinecek eminmisiniz ?');
        if (!deleteRow) {
        	return;
        }
        var successCallback = function() {
            $scope.displayError = false;
            $scope.performSearch();
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        CbsBagBirimResource.remove({CbsBagBirimId:id}, successCallback, errorCallback);
    };  
    
    $scope.cancel = function() {
        $location.path("/CbsBinas/edit/"+$scope.binaId);
    };
    
    $scope.performSearch();
       
});