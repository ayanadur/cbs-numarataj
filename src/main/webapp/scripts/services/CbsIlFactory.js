angular.module('app').factory('CbsIlResource', function($resource){
    var resource = $resource('rest/cbsils/:CbsIlId',{CbsIlId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});