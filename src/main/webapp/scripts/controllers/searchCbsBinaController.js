angular.module('app').controller('SearchCbsBinaController', function($scope, $http, $location, $resource, CbsIlceResource, CbsMahalleResource, CbsSokakResource, CbsBinaResource) {

	  $scope.totalItems = 64;
	  $scope.currentPage = 4;

	  $scope.setPage = function (pageNo) {
	    $scope.currentPage = pageNo;
	  };

	  $scope.pageChanged = function() {
	    $log.log('Page changed to: ' + $scope.currentPage);
	  };

	  $scope.maxSize = 5;
	  $scope.bigTotalItems = 175;
	  $scope.bigCurrentPage = 1;
	  //
    $scope.search={};
    $scope.currentPage = 0;
    $scope.pageSize= 25;
    $scope.searchResults = [];
    $scope.filteredResults = [];
    $scope.pageRange = [];
    
    $scope.numberOfPages = function() {
        var result = Math.ceil($scope.filteredResults.length/$scope.pageSize);
        var max = (result == 0) ? 1 : result;
        $scope.pageRange = [];
        for(var ctr=0;ctr<max;ctr++) {
            $scope.pageRange.push(ctr);
        }
        return max;
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
    
    $scope.cbsIlceList = CbsIlceResource.queryAll();
    
    $scope.$watch("cbsIlceSelection", function(selection) {
        if ((typeof selection != 'undefined') && (selection != null)) {
        	$scope.searchResults = {};
        	$scope.cbsMahalleList = CbsMahalleResource.queryAll({ilceid:selection.id});
        };
    });
    
    $scope.$watch("cbsMahalleSelection", function(selection) {
        if ((typeof selection != 'undefined') && (selection != null)) {
        	$scope.searchResults = {};
        	$scope.cbsSokakList = CbsSokakResource.queryAll({mahid:selection.id});
        };
    });
    
    $scope.$watch("cbsSokakSelection", function(selection) {
        if ((typeof selection != 'undefined') && (selection != null)) {
        	$scope.csid = selection.id;
        	$scope.searchResults = CbsBinaResource.queryAll({csid:selection.id});
        	$scope.numberOfPages();
        };
    });
    
    $scope.performSearch = function() {
        $scope.searchResults = CbsBinaResource.queryAll({csid:$scope.csid}, function(){
            $scope.numberOfPages();
        });
    };
    
    $scope.previous = function() {
       if($scope.currentPage > 0) {
           $scope.currentPage--;
       }
    };
    
    $scope.next = function() {
       if($scope.currentPage < ($scope.numberOfPages() - 1) ) {
           $scope.currentPage++;
       }
    };
    
    $scope.setPage = function(n) {
       $scope.currentPage = n;
    };

    $scope.getBB = function(bid) {
    	$location.path('/CbsBagBirims/'+bid);
     };
     $scope.getResimler = function(bid) {
     	$location.path('/CbsResims/'+bid);
      };   
    //$scope.performSearch();
});