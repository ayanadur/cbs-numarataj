angular.module('app').factory('CbsBagBirimResource', function($resource){
    var resource = $resource('rest/cbsbagbirims/:CbsBagBirimId',{CbsBagBirimId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});