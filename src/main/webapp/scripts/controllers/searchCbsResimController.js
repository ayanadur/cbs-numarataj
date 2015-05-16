angular.module('app').controller('SearchCbsResimController', function($scope, $http, $routeParams, $location,  dateFilter, $window, Universal, $document, locationParser, CbsResimResource ) {

	var self = this;
	
	$scope.model = $scope.model || {};
	$scope.model.dosya = {};
	
	$scope.binaId = $routeParams.CbsBinaId;
	
	//ilk çağrıldığında alan setlenir.
	
	$scope.cbsResim = {};
    $scope.cbsResim.cbsBina = {};
    $scope.cbsResim.cbsBina.id = $scope.binaId;
    $scope.cbsResim.kayitTarihi = new Date();
    
	$scope.LastId;

	$scope.performSearch = function() {
        $scope.searchResults = CbsResimResource.queryAll({CbsBinaId:$scope.binaId}, function(){
        	//version kontrolu için son işlem yapılan kaydı bul
        	for (var i=0; i<$scope.searchResults.length;i++) {
        		if($scope.searchResults[i].id == $scope.LastId) {
	                //$scope.cbsResim.id = $scope.searchResults[i].id;
	                //$scope.cbsResim.version = $scope.searchResults[i].version;
        		}
        	}
        });
    };
    
    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cbsResim);
    };
    
    $scope.newKayit = function() {
    	$scope.cbsResim = {};
        $scope.cbsResim.cbsBina = {};
		$scope.cbsResim.cbsBina.id = $scope.binaId;
		$scope.cbsResim.kayitTarihi = new Date();
		
		$scope.filename=null;
		var blah = $('#thumbResimId').attr('src',null);
    };
    
    $scope.get = function(id) {
        var successCallback = function(data){
            self.original = data;
            $scope.cbsResim = data; //new CbsResimResource(self.original);
        };
        var errorCallback = function() {
            //$location.path("/CbsBagBirims/");
        };
        CbsResimResource.get({CbsResimId:id}, successCallback, errorCallback);
    };
    
    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
        	
        	//son işlem yapılan id
            $scope.LastId = locationParser(responseHeaders);
            $scope.displayError = false;

            $scope.performSearch();
            
            $scope.get($scope.LastId);
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        $scope.cbsResim.id = null;
        $scope.cbsResim.cbsBina = {};
		$scope.cbsResim.cbsBina.id = $scope.binaId;
		
        CbsResimResource.save($scope.cbsResim, successCallback, errorCallback);
    };
    
    $scope.update = function() {
    	$scope.LastId = $scope.cbsResim.id;
    	
        var successCallback = function(){
            $scope.displayError = false;
            $scope.performSearch();
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        CbsResimResource.update($scope.cbsResim, successCallback, errorCallback); 
    };
    
    $scope.remove = function(id) {
        var deleteRow = $window.confirm('Resim kaydı silinecek eminmisiniz ?');
        if (!deleteRow) {
        	return;
        }
        var successCallback = function() {
        	
            $scope.displayError = false;
            $scope.performSearch();
            
            $scope.newKayit();
            
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        CbsResimResource.remove({CbsResimId:id}, successCallback, errorCallback);
    };  
    
    $scope.cancel = function() {
        //$location.path("/CbsBinas/edit/"+$scope.binaId);
    };
    
    
    $scope.saveFile = function() {
    	
    	var file = $document.find('input#filename')[0].files[0];
    	
    	var uploadUrl = Universal.uploadUrl;
    	
    	var fd = new FormData();
    	
		fd.append('upload-file', file);
		fd.append('dosya-adi', file.name);
		fd.append('dosya-turu', file.type);
		fd.append('dosya-path', 'bina_id_' + $scope.binaId + '_' + file.name);
		fd.append('bina-id', $scope.binaId);
		fd.append('kayit-tarihi', dateFilter($scope.cbsResim.kayitTarihi,"yyyy-MM-ddTHH:mm:ssZ")); //$scope.cbsResim.kayitTarihi);
		
		$http.post(uploadUrl, fd, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		})
        .success(function(data, status){
        	
        	$scope.LastId = data;
        	
        	$scope.displayError = false;
            $scope.performSearch();
            $scope.get($scope.LastId);
        })
        .error(function(status){
        });
		
    };
    
    $scope.onFileSelect = function(el) {
    	
    	if(!el) {
    		return;
    	}
    	
    	var file = el.files[0];
    	
    	if (file) {
    		
    		//thumbnail
	        var reader = new FileReader();
	        reader.onload = function (e) {
	            var blah = $('#thumbResimId');
	            blah.attr('src', e.target.result);
	            /*
	            var xbase64 = e.target.result;
	            //data:image/jpeg;base64,
	            $scope.cbsResim.blobDosya = xbase64.replace(/^data:image\/(png|jpg|jpeg);base64,/, "");
	            */
	        };
	        reader.readAsDataURL(file);
	        
	        //row set
	    	$scope.cbsResim.dosyaAdi = el.value;
	    	$scope.cbsResim.turu= el.files[0].type;
	    	$scope.cbsResim.path = 'bina_id_' + $scope.binaId + '_' + el.files[0].name;	        
    	}
    	
    };
  
    $scope.getFilePath = function() {
    	
    	var elid = $scope.cbsResim.id;
    	
    	if ((typeof elid != 'undefined') && (elid != null) && ($scope.cbsResim.path != null) ) {
    		return Universal.imageUrl + '/' + $scope.cbsResim.id;
    	}
    };
    
    $scope.performSearch();
       
});