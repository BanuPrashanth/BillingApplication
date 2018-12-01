
app.controller('customerController', ['$scope','stateService','customerService','popUpService','NgTableParams',function($scope,stateService,customerService,popUpService,NgTableParams) {
	
	$scope.customers=[];
	$scope.deletedCustomers=[];
	$scope.updatedCustomers=[];
	$scope.modes={
			add : false,
			view : true
	};
	$scope.generateCustomerID=function()
	{
		var _customer=customerService.get({_Id:'generateId'},
		function successCallback()
		{
			$scope.customer.customerID=_customer.customerID;
		},
		function failureCallback()
		{
			popUpService.openInfoBox('Error generating Customer Id!',$scope);
		});
	}

	var customerInit=function()
	{
		$scope.customer={
				customerName: '',
				address: '',
				phone: undefined,
				gst: undefined,
				panNumber: '',
				customerSPOC: '',
				openingBalance: 0,
				state: '',
				startDate: new Date()
		}
		$scope.generateCustomerID();
	}
	customerInit();
	$scope.changeMode=function(activeMode)
	{
		angular.forEach($scope.modes,function(value,mode){
			$scope.modes[mode] = mode==activeMode?true:false;
		})
		if($scope.modes.add)
		{
			$scope.generateCustomerID();
		}
		else if($scope.modes.view)
		{
			getCustomerList();
		}
	}
	
	//Service returns a promise object, 
	//So it is better to use 'then' in controller and not in service.
	//State should get loaded along with the controller
	stateService.getStates().then(function successCallback(response) 
	{
		$scope.states=response.data;
	});
	
	$scope.addCustomer=function(customer)
	{
		if(!customer.balanceType){
			customer.openingBalance=-customer.openingBalance;
		}
		customerService.save(customer,function()
		{
			customerInit();
			popUpService.openInfoBox('Customer Added Successfully!',$scope);
		},
		function()
		{
			popUpService.openInfoBox('Server Error!',$scope);
		});
	}
	
	var getCustomerList=function()
	{
		var customers=customerService.query(function(){
			$scope.tableCustomer = new NgTableParams({}, { dataset: customers});
		});
	}
	getCustomerList();
	$scope.deleteCustomer=function(customerID)
	{
		popUpService.openConfirmBox('customer',$scope).then(function (success) 
		{
			customerService.remove({_Id: customerID},function()
			{
				getCustomerList();
				popUpService.openInfoBox('Customer Deleted Successfully!',$scope);
			},
			function()
			{
				popUpService.openInfoBox('Server Error!',$scope);
			});
		}, function (failure){
			//do nothing
		});
	}
	
	$scope.updateCustomer=function(customer)
	{
		customerService.update(customer,function()
		{
			popUpService.openInfoBox('Customer Updated Successfully!',$scope);
		},
		function()
		{
			popUpService.openInfoBox('Server Error!',$scope);
		});
	}
	$scope.openEditCustomerForm=function(customer)
    {
    	$scope.editCustomer=customer;
    	popUpService.openEditForm($scope,'views/customer/EditCustomers.html',600);
    }
	$scope.updateCustomers=function()
	{
		if($scope.deletedCustomers.length==0 && $scope.updatedCustomers.length==0)
		{
			$window.alert('No customer has been modified!');
		}
		else
		{
			var payload={
					deletedCustomers: $scope.deletedCustomers,
					updatedCustomers: $scope.updatedCustomers
			};
			// Simple POST request
	    	$http({
	    	  method: 'POST',
	    	  url: '/customer/update',
	    	  data: payload
	    	}).then(function successCallback(response) {
	    	    // this callback will be called asynchronously
	    	    // when the response is available
	    		$window.alert('Customer Updated Successfully!');
	    		$scope.deletedCustomers=[];
	    		$scope.updatedCustomers=[];
	    	  }, function errorCallback(response) {
	    		  $window.alert('Server Error!');
	    	  });
		}
	}
	
    $scope.today = function () {
        $scope.customer.startDate = new Date();
    };
    $scope.mindate = new Date();
    $scope.dateformat="dd/MM/yyyy";
    $scope.today();
    $scope.showcalendar = function ($event) {
        $scope.showdp = true;
    };
    $scope.showdp = false;
}]);
 