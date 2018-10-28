app.controller('userController', ['$scope','NgTableParams','userService','popUpService',function($scope,NgTableParams,userService,popUpService)
{	
	$scope.users=[];
	
	var userInit=function()
	{
		$scope.user={
				createDate: new Date(),
				status: 'Active',
				userType: 'Normal'
		};
	}
	userInit();
	$scope.modes={
			add : false,
			view : true
	};
	
	$scope.userStatus=['Active','Inactive'];
	$scope.userTypes=['Normal','Admin','Super Admin'];
	
	$scope.changeMode=function(activeMode)
	{
		angular.forEach($scope.modes,function(value,mode){
			$scope.modes[mode] = mode==activeMode?true:false;
		})
		if($scope.modes.view)
		{
			getUserList();
		}
	}
	getUserList=function()
	{
		var users=userService.query(function(){
			$scope.tableParams = new NgTableParams({}, { dataset: users});
		});
	}
	getUserList();
	
	$scope.deleteUser=function(userID)
	{
		popUpService.openConfirmBox('user',$scope).then(function (success) 
		{
			userService.remove({_Id: userID},function()
			{
				getUserList();
				popUpService.openInfoBox('User Deleted Successfully!',$scope);
			},
			function()
			{
				popUpService.openInfoBox('Server Error!',$scope);
			});
		}, function (failure){
			//do nothing
		});		
	}
	
	$scope.update=function(user)
	{
		userService.update(user,function()
		{
			popUpService.openInfoBox('User Updated Successfully!',$scope);
		},
		function()
		{
			popUpService.openInfoBox('Server Error!',$scope);
		});
	}
	
	$scope.addUser=function(user)
	{
		userService.save(user,function()
		{
			userInit();
			popUpService.openInfoBox('User Added Successfully!',$scope);
		},
		function()
		{
			popUpService.openInfoBox('Server Error!',$scope);
		});
	}
	
	$scope.reset = function(userform) {
        if (userform) {
        	userform.$setPristine();
        	userform.$setUntouched();
        	$scope.user={
        			createDate: new Date()
        	};
        	$scope.errorMsg=undefined;
        }
    };
    
    $scope.openEditUserForm=function(user){
    	$scope.editUser=user;
    	popUpService.openEditForm($scope,'views/user/EditUsers.html',600);
    }
    
    $scope.today = function () {
        $scope.user.createDate = new Date();
    };
    $scope.mindate = new Date();
    $scope.dateformat="dd/MM/yyyy";
    $scope.today();
    $scope.showcalendar = function ($event) {
        $scope.showdp = true;
    };
    $scope.showdp = false;

}]);
