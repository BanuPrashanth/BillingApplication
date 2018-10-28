 
app.controller('productController', ['$scope','ngDialog','NgTableParams','productService','popUpService',function($scope,ngDialog,NgTableParams,productService,popUpService) {
			
	$scope.modes={
			add : false,
			view : true
	};
	
	$scope.productStatus=['Active','Inactive'];
	
	$scope.generateProductID=function()
	{
		var _productId=productService.get({_Id:'generateId'},
		function successCallback()
		{
			$scope.product.productID=_productId.productID;
		},
		function failureCallback()
		{
			popUpService.openInfoBox('Error generating Product Id!',$scope);
		});
	}
	
	var productInit=function()
	{
		$scope.product=
		{
				status: 'Active',
				effectiveDate: new Date()
		}
		$scope.generateProductID();
	}
	
	productInit();
	
	$scope.changeMode=function(activeMode)
	{
		angular.forEach($scope.modes,function(value,mode)
		{
			$scope.modes[mode] = mode==activeMode?true:false;
		})
		
		if($scope.modes.add)
		{
			$scope.generateProductID();
		}
		else if($scope.modes.view)
		{
			getProductList();
		}
	}
	
	$scope.addProduct=function(product)
	{
		productService.save(product,function()
		{
			productInit();
			popUpService.openInfoBox('Product Added Successfully!',$scope);
		},
		function()
		{
			popUpService.openInfoBox('Server Error!',$scope);
		});
	}
	
	var getProductList=function()
	{
		var products=productService.query(function(){
			$scope.tableParams = new NgTableParams({}, { dataset: products});
		});
	}
	getProductList();
	
	$scope.deleteProduct=function(productID)
	{
		popUpService.openConfirmBox('product',$scope).then(function (success) 
		{
			productService.remove({_Id: productID},function()
			{
				getProductList();
				popUpService.openInfoBox('Product Deleted Successfully!',$scope);
			},
			function()
			{
				popUpService.openInfoBox('Server Error!',$scope);
			});
		}, function (failure){
			//do nothing
		});
	}
	
	$scope.updateProduct=function(product)
	{
		productService.update(product,function()
		{
			popUpService.openInfoBox('Product Updated Successfully!',$scope);
		},
		function()
		{
			popUpService.openInfoBox('Server Error!',$scope);
		});
	}
	
	$scope.reset = function(productform) {
        if (productform) {
        	productform.$setPristine();
        	productform.$setUntouched();
        	$scope.product={
        			effectiveDate: new Date()
        	}
        	$scope.successMsg=undefined;
        	$scope.errorMsg=undefined;
        	$scope.generateProductID();
        }
    };
    
    //Calendar logic with disabled past dates
    $scope.today = function () {
        $scope.product.effectiveDate = new Date();
    };
    $scope.mindate = new Date();
    $scope.dateformat="dd/MM/yyyy";
    $scope.today();
    $scope.showcalendar = function ($event) {
        $scope.showdp = true;
    };
    $scope.showdp = false;
    //End
    $scope.openEditProductForm=function(product)
    {
    	$scope.editProduct=product;
    	popUpService.openEditForm($scope,'views/product/EditProducts.html',600);
    }
    
}]);
