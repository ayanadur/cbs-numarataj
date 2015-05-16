'use strict';

angular.module('app',['ngRoute','ngResource', 'ngCookies', 'angular-loading-bar'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
      .when('/login',{templateUrl:'views/Login/login.html',controller:'LoginController', controllerAs: 'vm'})
      .when('/CbsBagBirims/:CbsBinaId',{templateUrl:'views/CbsBagBirim/search.html',controller:'SearchCbsBagBirimController'})
      .when('/CbsBagBirims/new',{templateUrl:'views/CbsBagBirim/detail.html',controller:'NewCbsBagBirimController'})
      .when('/CbsBagBirims/edit/:CbsBagBirimId,:CbsBinaId',{templateUrl:'views/CbsBagBirim/detail.html',controller:'EditCbsBagBirimController'})
      .when('/CbsBinas',{templateUrl:'views/CbsBina/search.html',controller:'SearchCbsBinaController'})
      .when('/CbsBinas/new',{templateUrl:'views/CbsBina/detail.html',controller:'NewCbsBinaController'})
      .when('/CbsBinas/edit/:CbsBinaId',{templateUrl:'views/CbsBina/detail.html',controller:'EditCbsBinaController'})
      .when('/CbsIls',{templateUrl:'views/CbsIl/search.html',controller:'SearchCbsIlController'})
      .when('/CbsIls/new',{templateUrl:'views/CbsIl/detail.html',controller:'NewCbsIlController'})
      .when('/CbsIls/edit/:CbsIlId',{templateUrl:'views/CbsIl/detail.html',controller:'EditCbsIlController'})
      .when('/CbsIlces',{templateUrl:'views/CbsIlce/search.html',controller:'SearchCbsIlceController'})
      .when('/CbsIlces/new',{templateUrl:'views/CbsIlce/detail.html',controller:'NewCbsIlceController'})
      .when('/CbsIlces/edit/:CbsIlceId',{templateUrl:'views/CbsIlce/detail.html',controller:'EditCbsIlceController'})
      .when('/CbsMahalles',{templateUrl:'views/CbsMahalle/search.html',controller:'SearchCbsMahalleController'})
      .when('/CbsMahalles/new',{templateUrl:'views/CbsMahalle/detail.html',controller:'NewCbsMahalleController'})
      .when('/CbsMahalles/edit/:CbsMahalleId',{templateUrl:'views/CbsMahalle/detail.html',controller:'EditCbsMahalleController'})
      .when('/CbsSokaks',{templateUrl:'views/CbsSokak/search.html',controller:'SearchCbsSokakController'})
      .when('/CbsSokaks/new',{templateUrl:'views/CbsSokak/detail.html',controller:'NewCbsSokakController'})
      .when('/CbsSokaks/edit/:CbsSokakId',{templateUrl:'views/CbsSokak/detail.html',controller:'EditCbsSokakController'})
      .when('/CbsUlkes',{templateUrl:'views/CbsUlke/search.html',controller:'SearchCbsUlkeController'})
      .when('/CbsUlkes/new',{templateUrl:'views/CbsUlke/detail.html',controller:'NewCbsUlkeController'})
      .when('/CbsUlkes/edit/:CbsUlkeId',{templateUrl:'views/CbsUlke/detail.html',controller:'EditCbsUlkeController'})
      
      .when('/CbsResims/:CbsBinaId',{templateUrl:'views/CbsResim/search.html',controller:'SearchCbsResimController'})
      
      .otherwise({
        redirectTo: '/'
      });
  }])
  
  .run(function($rootScope, $location, $cookieStore, $http) {
	  
      // keep user logged in after page refresh
      $rootScope.globals = $cookieStore.get('globals') || {};
      if ($rootScope.globals.currentUser) {
          $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
      }

      $rootScope.$on('$locationChangeStart', function (event, next, current) {
          // redirect to login page if not logged in and trying to access a restricted page
          var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
          var loggedIn = $rootScope.globals.currentUser;
          if (restrictedPage && !loggedIn) {
              $location.path('/login');
          }
      });
    
  })
  
  .constant('Universal', {
	  imageUrl: 'rest/cbsresims/media',
	  uploadUrl: 'rest/cbsresims/upload-file',
	  key: 'PoppIYvQ8rN_87FiMmXAjs0okAG7JGmI',
	  ilceId : 41,
	  ilId : 48
  })
  
  .controller('LandingPageController', function LandingPageController($rootScope, $scope, $location) {
  })
  
  .controller('NavController', function NavController($rootScope, $scope, $location) {
	  
    $scope.matchesRoute = function(route) {
        var path = $location.path();
        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
    };
        
  })

.config(['$httpProvider', function($httpProvider) {
    //initialize get if not there
    if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};    
    }    

    // Answer edited to include suggestions from comments
    // because previous version of code introduced browser-related errors

    //disable IE ajax request caching
    $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
    // extra
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
}]);