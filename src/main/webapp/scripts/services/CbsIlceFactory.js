angular.module('app').factory('CbsIlceResource', function($resource){
    var resource = $resource('rest/cbsilces/:CbsIlceId',{CbsIlceId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});