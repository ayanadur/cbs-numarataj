angular.module('app').factory('CbsSokakResource', function($resource){
	//console.log("CbsSokak Factory " + $resource);
    var resource = $resource('rest/cbssokaks/:CbsSokakId',{CbsSokakId:'@id'},
    						{'queryAll':{method:'GET',isArray:true},
    						 'query'   :{method:'GET',isArray:false},
    						 'update':{method:'PUT'}
    						});
    return resource;
});