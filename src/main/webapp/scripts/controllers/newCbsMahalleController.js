
angular.module('app').controller('NewCbsMahalleController', function ($scope, $location, locationParser, CbsMahalleResource , CbsIlceResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cbsMahalle = $scope.cbsMahalle || {};
    
    $scope.cbsIlceList = CbsIlceResource.queryAll(function(items){
        $scope.cbsIlceSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.adi
            });
        });
    });
    $scope.$watch("cbsIlceSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.cbsMahalle.cbsIlce = {};
            $scope.cbsMahalle.cbsIlce.id = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CbsMahalles/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CbsMahalleResource.save($scope.cbsMahalle, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CbsMahalles");
    };
});