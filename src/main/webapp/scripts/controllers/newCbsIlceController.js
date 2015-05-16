
angular.module('app').controller('NewCbsIlceController', function ($scope, $location, locationParser, CbsIlceResource , CbsIlResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cbsIlce = $scope.cbsIlce || {};
    
    $scope.cbsIlList = CbsIlResource.queryAll(function(items){
        $scope.cbsIlSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.adi
            });
        });
    });
    
    $scope.$watch("cbsIlSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.cbsIlce.cbsIl = {};
            $scope.cbsIlce.cbsIl.id = selection.value;
        }
    });
    
    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CbsIlces/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CbsIlceResource.save($scope.cbsIlce, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CbsIlces");
    };
});