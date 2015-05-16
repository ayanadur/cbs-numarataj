angular.module('app').factory('CbsMahalleResource', function($resource){
    var resource = $resource('rest/cbsmahalles/:CbsMahalleId',{CbsMahalleId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});