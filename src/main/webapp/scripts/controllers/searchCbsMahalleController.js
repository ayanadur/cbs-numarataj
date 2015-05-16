angular.module('app').controller('SearchCbsMahalleController', function($scope, $http, Universal, CbsMahalleResource , CbsIlceResource) {

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
    
    $scope.ilceId = Universal.ilceId;
    
    $scope.cbsIlceList = CbsIlceResource.queryAll();
    $scope.$watch("search.cbsIlce", function(selection) {
        if ((typeof selection != 'undefined') && (selection != null)) {
        	$scope.ilceId = selection.id;
        	$scope.performSearch();
        };
    });

    $scope.performSearch = function() {
        $scope.searchResults = CbsMahalleResource.queryAll({ilceid:$scope.ilceId}, function(){
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