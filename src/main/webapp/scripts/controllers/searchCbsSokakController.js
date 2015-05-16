angular.module('app').controller('SearchCbsSokakController', function($scope, $http, Universal, CbsIlceResource, CbsMahalleResource, CbsSokakResource) {

	$scope.search={};
	$scope.currentPage = 0;
	$scope.pageSize= 10;
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
	
	$scope.turuList = [
	   "CADDE",
	   "SOKAK",
	   "MEYDAN",
	   "BULVAR",
	   "KUMEEVLER",
	   "KOYSOKAGI"
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
   
    $scope.cbsIlceList = CbsIlceResource.queryAll({ilid:Universal.ilId});
    $scope.mahid = 0;
    
    $scope.$watch("cbsIlceSelection", function(selection) {
        if ((typeof selection != 'undefined') && (selection != null)) {
        	$scope.cbsMahalleList = CbsMahalleResource.queryAll({ilceid:selection.id});
        };
    });
    
    $scope.$watch("cbsMahalleSelection", function(selection) {
        if ((typeof selection != 'undefined') && (selection != null)) {
        	$scope.mahid = selection.id;
        	$scope.performSearch();
        };
    });
    
    $scope.performSearch = function() {
    	$scope.searchResults = CbsSokakResource.queryAll({mahid:$scope.mahid}, function(){
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

    $scope.performSearch();
});