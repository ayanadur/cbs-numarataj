angular.module('app').factory('CbsResimResource', function($resource){
    var resource = $resource('rest/cbsresims/:CbsResimId',{CbsResimId:'@id'},
    		{'queryAll':{method:'GET',isArray:true},
    		 'query':{method:'GET',isArray:false},
    		 'update':{method:'PUT'}});
    return resource;
});