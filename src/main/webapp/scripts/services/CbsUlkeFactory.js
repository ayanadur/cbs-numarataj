angular.module('app').factory('CbsUlkeResource', function($resource){
    var resource = $resource('rest/cbsulkes/:CbsUlkeId',{CbsUlkeId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});