angular.module('app').factory('CbsBinaResource', function($resource){
    var resource = $resource('rest/cbsbinas/:CbsBinaId',{CbsBinaId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});

angular.module('app').factory('CbsFileUploadService', function ($resource) {
    var resource = $resource(
        '/file/upload/:frm', {
            id: '@id',
            frm: '@frm'
        }, {
            save: {
                method: 'POST'
            }
        }
    );

    return resource;
});