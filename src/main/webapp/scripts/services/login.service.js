(function () {
 
    angular
        .module('app')
        .controller('LoginController', LoginController);
 
    LoginController.$inject = ['$rootScope', '$location', 'AuthenticationService'];
    function LoginController($rootScope, $location, AuthenticationService) {
    	
        var vm = this;
        
        vm.login = login;
        
        (function initController() {
            // reset login status
            AuthenticationService.ClearCredentials();
        })();
 
        function login() {
        	
            vm.dataLoading = true;
            AuthenticationService.Login(vm.username, vm.password, function (data, status) {
            	
            	console.log(data);
            	
                if (status == 200) {
                    AuthenticationService.SetCredentials(vm.username, vm.password, data.givenname, data.sn);
                    console.log($rootScope.globals.currentUser);
                    $location.path('/');
                } else {
                    //FlashService.Error(response.message);
                    vm.dataLoading = false;
                }
            });
        };
    }
 
})();