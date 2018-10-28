
app.controller('homeController', function($rootScope) {
	$rootScope.isLoggedIn=false;
	this.logout=function()
	{
		$rootScope.isLoggedIn=false;
	}
});

app.controller('loginController', ['$scope','$rootScope','$location','$http','$window',function($scope,$rootScope,$location,$http,$window) {
	$scope.heading='Login In';
	$scope.login=function(user)
    {
    	// Simple POST request
    	$http({
    	  method: 'POST',
    	  url: '/user/validate',
    	  data: user
    	}).then(function successCallback(response) {
    	    // this callback will be called asynchronously
    	    // when the response is available
    		if(response.data==true)
    		{
    			$rootScope.isLoggedIn=true;
    			$rootScope.username=user.username;
    			$location.path('/dashboard');
    		}
    		else
    		{
    			$scope.loginError='Invalid User Credentials!';
    		}
    	  }, function errorCallback(response) {
    	    // called asynchronously if an error occurs
    	    // or server returns response with an error status.
    		  $window.alert('Server Error!');
    		  $scope.loginError=undefined;
    	  });
    	
    }
	$scope.reset = function(loginform) {
        if (loginform) {
        	loginform.$setPristine();
        	loginform.$setUntouched();
        	$scope.user={};
        	$scope.loginError=undefined;
        }
    };
        
}]);


app.controller('ownerController', ['$scope',function($scope) {
	$scope.heading='Owner info page yet to be prepared';
}]);

app.controller('dashboardController', ['$scope',function($scope) {
	$scope.heading='User DashBoard';
}]);

app.filter('isEmpty', function () {
    var bar;
    return function (obj) {
        for (bar in obj) {
            if (obj.hasOwnProperty(bar)) {
                return false;
            }
        }
        return true;
    };
});
