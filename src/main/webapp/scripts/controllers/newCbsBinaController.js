
angular.module('app').controller('NewCbsBinaController', function ($scope, $location, $resource, locationParser, CbsBinaResource , CbsSokakResource, CbsMahalleResource) {
    
	$scope.disabled = false;
	
    $scope.$location = $location;
    $scope.cbsBina = $scope.cbsBina || {};
    
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
    
    $scope.cbsIlceList = {};
    $scope.cbsMahalleList = {};
    $scope.cbsSokakList = {};
    
    $scope.cbsIlceList = $resource('rest/cbsilces', {}).query(function(items){
        $scope.cbsIlceSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.adi
            });
        });
    });
    
    $scope.$watch("cbsIlceSelection", function(selection) {
    	if ((typeof selection != 'undefined') && (selection != null)) {
    	    $scope.cbsMahalleList = $resource('rest/cbsmahalles/cbsilce/:ilceid', {ilceid:'@ilceid'}).query({ilceid:selection.value}, function(items){
    	        $scope.cbsMahalleSelectionList = $.map(items, function(item) {
    	            return ( {
    	                value : item.id,
    	                text : item.adi
    	            });
    	        });
    	    });
        }
    });
    
    var CustomResource = $resource('rest/cbssokaks/cbsmahalle/:mahid', {mahId:'@mahid'}, {'queryAll':{method:'GET',isArray:true}});
    
    $scope.$watch("cbsMahalleSelection", function(selection) {
    	if ((typeof selection != 'undefined') && (selection != null)) {
    	    $scope.cbsSokakList = CustomResource.queryAll({mahid:selection.value}, function(items){
    	        $scope.cbsSokakSelectionList = $.map(items, function(item) {
    	            return ( {
    	                value : item.id,
    	                text : item.adi
    	            });
    	        });
    	    });
        }
    });
    
    $scope.$watch("cbsSokakSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.cbsBina.cbsSokak = {};
            $scope.cbsBina.cbsSokak.id = selection.value;
        }
    });

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            alert("Kayıt başarılı ID : " + id);
            $location.path('/CbsBinas/edit/'+id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CbsBinaResource.save($scope.cbsBina, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CbsBinas");
    };
});