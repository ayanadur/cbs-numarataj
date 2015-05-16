
angular.module('app').controller('NewCbsBagBirimController', function ($scope, $location, locationParser, CbsBagBirimResource , CbsBinaResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cbsBagBirim = $scope.cbsBagBirim || {};
    
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
    
    $scope.cbsBinaList = CbsBinaResource.queryAll(function(items){
        $scope.cbsBinaSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.binaAdi
            });
        });
    });
    $scope.$watch("cbsBinaSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.cbsBagBirim.cbsBina = {};
            $scope.cbsBagBirim.cbsBina.id = selection.value;
        }
    });

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CbsBagBirims/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CbsBagBirimResource.save($scope.cbsBagBirim, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CbsBagBirims");
    };
});