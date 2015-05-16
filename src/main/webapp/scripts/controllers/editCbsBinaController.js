
angular.module('app').controller('EditCbsBinaController', function($scope, $routeParams, $location, $resource, $window, CbsBinaResource , CbsSokakResource, CbsMahalleResource, CbsIlceResource) {
    
	var self = this;
	
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
    	
        var successCallback = function(data){
        	
            self.original = data;
            $scope.cbsBina = new CbsBinaResource(self.original);
            
            CbsIlceResource.queryAll( {}, function(items) {

            	$scope.cbsIlceSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id,
                        adi : item.adi
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.adi
                    };
                    if($scope.cbsBina.cbsSokak.cbsMahalle.cbsIlce && item.id == $scope.cbsBina.cbsSokak.cbsMahalle.cbsIlce.id) {
                        $scope.cbsIlceSelection = labelObject;
                        $scope.cbsBina.cbsSokak.cbsMahalle.cbsIlce = wrappedObject;
                        self.original.cbsSokak.cbsMahalle.cbsIlce = $scope.cbsBina.cbsSokak.cbsMahalle.cbsIlce;
                    }
                    return labelObject;
                });
            });
            /*
            CbsMahalleResource.queryAll( {ilceid:$scope.cbsBina.cbsSokak.cbsMahalle.cbsIlce.id}, function(items) {

            	$scope.cbsMahalleSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.adi
                    };
                    if($scope.cbsBina.cbsSokak.cbsMahalle && item.id == $scope.cbsBina.cbsSokak.cbsMahalle.id) {
                        $scope.cbsMahalleSelection = labelObject;
                        $scope.cbsBina.cbsSokak.cbsMahalle = wrappedObject;
                        self.original.cbsSokak.cbsMahalle = $scope.cbsBina.cbsSokak.cbsMahalle;
                    }
                    return labelObject;
                });
            });   
            */
            
            /*
            CbsSokakResource.queryAll( {mahid:$scope.cbsBina.cbsSokak.cbsMahalle.id}, function(items) {

            	$scope.cbsSokakSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.adi
                    };
                    if($scope.cbsBina.cbsSokak && item.id == $scope.cbsBina.cbsSokak.id) {
                        $scope.cbsSokakSelection = labelObject;
                        $scope.cbsBina.cbsSokak = wrappedObject;
                        self.original.cbsSokak = $scope.cbsBina.cbsSokak;
                    }
                    return labelObject;
                });
            });  
            */           
            
        };

        var errorCallback = function() {
            $location.path("/CbsBinas");
        };
        
        CbsBinaResource.get({CbsBinaId:$routeParams.CbsBinaId}, successCallback, errorCallback);
        
    };
    
    $scope.$watch("cbsIlceSelection", function(selection) {
    	if ((typeof selection != 'undefined') && (selection != null)) {
    	    $scope.cbsMahalleList = CbsMahalleResource.queryAll({ilceid:selection.value}, function(items){
    	        $scope.cbsMahalleSelectionList = $.map(items, function(item) {
    	        	/*
    	            return ( {
    	                value : item.id,
    	                text : item.adi
    	            });
    	            */
                    var wrappedObject = {
                            id : item.id,
                            adi : item.adi
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.adi
                    };
                    if($scope.cbsBina.cbsSokak.cbsMahalle && item.id == $scope.cbsBina.cbsSokak.cbsMahalle.id) {
                        $scope.cbsMahalleSelection = labelObject;
                        $scope.cbsBina.cbsSokak.cbsMahalle = wrappedObject;
                        self.original.cbsSokak.cbsMahalle = $scope.cbsBina.cbsSokak.cbsMahalle;
                    }
                    return labelObject;
    	        });
    	    });
        }
    });
    
    $scope.$watch("cbsMahalleSelection", function(selection) {
    	if ((typeof selection != 'undefined') && (selection != null)) {
    	    $scope.cbsSokakList = CbsSokakResource.queryAll({mahid:selection.value}, function(items){
    	        $scope.cbsSokakSelectionList = $.map(items, function(item) {
    	        	/*
    	            return ( {
    	                value : item.id,
    	                text : item.adi
    	            });
    	            */
                    var wrappedObject = {
                            id : item.id,
                            adi : item.adi
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.adi
                    };
                    if($scope.cbsBina.cbsSokak && item.id == $scope.cbsBina.cbsSokak.id) {
                        $scope.cbsSokakSelection = labelObject;
                        $scope.cbsBina.cbsSokak = wrappedObject;
                        self.original.cbsSokak = $scope.cbsBina.cbsSokak;
                    }
                    return labelObject;
    	        });
    	    });
        }
    });
    
    $scope.$watch("cbsSokakSelection", function(selection) {
    	if ((typeof selection != 'undefined') && (selection != null)) {
            var wrappedObject = {
                    id : selection.value,
                    adi : selection.text
            };
    		$scope.cbsBina.cbsSokak = wrappedObject;
    	}
    });
    
    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cbsBina);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cbsBina.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CbsBinas");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CbsBinas");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        
        var deleteRow = $window.confirm('Bina kaydı silinecek eminmisiniz ?');
        if (deleteRow) {
        	$scope.cbsBina.$remove(successCallback, errorCallback);
        }
    };
    
    $scope.kapiTuruList = [
        "ANA",  
        "TALI",  
        "TAHSIS",  
        "DIGER"  
    ];
    $scope.zeminKullanimTuruList = [
        "TICARET",  
        "KONUT",  
        "DEPO",  
        "RESMI_KURUM",  
        "EGITIM",  
        "SAGLIK",  
        "DINI",  
        "ENDUSTRI",  
        "VAKIF",  
        "MUZE",  
        "SOSYO_KULTUREL",  
        "BOS"  
    ];
    $scope.yapiTipiList = [
        "APARTMAN",  
        "ISMERKEZI",  
        "MUSTAKIL",  
        "TARIHI",  
        "EKYAPI"  
    ];
    $scope.yapiDurumuList = [
        "KULLANIMDA",  
        "KULLANIM_DISI",  
        "YAPIM_ASAMASI",  
        "YIKILMIS"  
    ];
    $scope.yapiFizikiDurumuList = [
        "IYI",  
        "ORTA",  
        "HARAP"  
    ];
    $scope.catiDurumuList = [
        "VAR",  
        "YOK",  
        "FILIZLI"  
    ];
    $scope.cepheMalzemesiList = [
        "BOYALI",  
        "CAM",  
        "SIVALI",  
        "SIVASIZ",  
        "MOZAIK",  
        "TAS_KAPLAMA"  
    ];
    $scope.otoparkTuruList = [
        "YOK",  
        "ACIK",  
        "KAPALI"  
    ];
    $scope.asansorList = [
        "VAR",  
        "YOK"  
    ];
    $scope.yanginMerdiveniList = [
        "VAR",  
        "YOK"  
    ];
    $scope.siginakList = [
        "VAR",  
        "YOK"  
    ];
    
    $scope.goCbsBagBirims = function(bbid) {
        $location.path("/CbsBagBirims/"+bbid);
    };

    $scope.goCbsResims = function(bbid) {
        $location.path("/CbsResims/"+bbid);
    };
    
    $scope.get();
});