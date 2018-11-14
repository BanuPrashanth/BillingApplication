app.controller('reportController', ['$scope','deliveryNoteService','$window','$http',function($scope,deliveryNoteService,$window,$http) {
	$scope.simple=false;
	$scope.activeMode='';
	$scope.report={
			fromDate: new Date(),
			toDate: new Date()
	};
	
	$scope.transactionTypes=['Sales','Receipt','Sales/Receipt'];
	$scope.modes={
			sales : false,
			stockWise : false,
			partyWise : false,
			receipt : false
	};
	
	$scope.changeMode=function(activeMode)
	{
		$scope.activeMode=activeMode;
		angular.forEach($scope.modes,function(value,mode){
			$scope.modes[mode] = mode==activeMode?true:false;
		})
		if($scope.modes.partyWise)
		{
			$scope.fetchCustomer();
		}
		if($scope.modes.sales || $scope.modes.stockWise)
		{
			$scope.fetchProducts();
		}
	}
	
	$scope.fetchProducts=function()
	{
		//This service gets all the products
		deliveryNoteService.getObject('product').then(function successCallback(response) 
		{
			$scope.products=response.data;
		});
	}
	$scope.fetchCustomer=function()
	{
		//This service gets the Customer ID and Customer Name
		deliveryNoteService.getObject('customer').then(function successCallback(response) 
		{
			$scope.customers=response.data;
		});
	}
	$scope.loadCustomerName=function()
	{
		var customerID=$scope.report.customerID;
		angular.forEach($scope.customers,function(customer,key){
			if(customer.customerID==customerID)
				{
					$scope.report.customerName=customer.customerName;
				}
		});
	};
	
	$scope.loadCustomerID=function()
	{
		var customerName=$scope.report.customerName;
		angular.forEach($scope.customers,function(customer,key){
			if(customer.customerName==customerName)
				{
					$scope.report.customerID=customer.customerID;
				}
		});
		
	};
	$scope.loadProductByID=function()
	{
		$scope.isProductSelected=true;
		angular.forEach($scope.products,function(product,key){
			if(product.productID==$scope.report.productID)
				{
				$scope.report.productDescription=product.productDescription;
				}
		});
	};
	$scope.loadProductByDesc=function()
	{
		$scope.isProductSelected=true;
		angular.forEach($scope.products,function(product,key){
			if(product.productDescription==$scope.report.productDescription)
				{
				$scope.report.productID=product.productID;
				}
		});
	};
	
	$scope.generateReport=function()
	{
		if($scope.modes.receipt)
		{
			$scope.report.totalAmountReceived=0;
			deliveryNoteService.submit('/receipt/'+$scope.simple,$scope.report)
			.then(function successCallback(response){
				$scope.report.receipts=response.data;
				
				if($scope.report.receipts.length==0)
					{
					  $window.alert('No Record Found');
					}
				else
					{
					var count=0;
					angular.forEach($scope.report.receipts,function(value,key){
						value.sno = ++count;
						$scope.report.totalAmountReceived+=value.totalAmtPaid;
					})
					}
			},function failureCallback(){
				$window.alert('Server Error');
			})
		}
		else if($scope.modes.sales)
		{
			deliveryNoteService.submit('/invoice/report/'+$scope.simple,$scope.report)
			.then(function successCallback(response){
				$scope.report.invoices=response.data.invoices;
				$scope.report.variants=response.data.variants;
				$scope.report.listofVariantInstancesSum=response.data.listofVariantInstancesSum;
				if($scope.report.invoices.length==0)
					{
					  $window.alert('No Record Found');
					}
				else
					{
						var count=0;
						angular.forEach($scope.report.invoices,function(value,key){
							value.sno = ++count;
						});
					}
			},function failureCallback(){
				$window.alert('Server Error');
			})
		}
		else if($scope.modes.stockWise)
		{
			if(!$scope.report.productID)
			{
				$window.alert('Please select product!');
			}
			else
			{
				deliveryNoteService.submit('/reports/stocks/'+$scope.simple,$scope.report)
				.then(function successCallback(response){
					$scope.report.stocks=response.data;
					if($scope.report.stocks.length==0)
					{
					  $window.alert('No Record Found');
					}
				else
					{
					var count=0;
					$scope.report.totalQuantity=0;
					$scope.report.totalAmount=0;
					angular.forEach($scope.report.stocks,function(stock,key){
						stock.sno = ++count;
						$scope.report.totalQuantity +=stock.quantity;
						$scope.report.totalAmount +=stock.amount;
					})
					}
				},function failureCallback(){
					$window.alert('Server Error');
				})
			}
		}
		else if($scope.modes.partyWise)
		{
			if(!$scope.report.customerID)
			{
				$window.alert('Please select Customer!');
			}
			else if(!$scope.report.transactionType)
			{
				$window.alert('Please select Transaction Type!');
			}
			else
			{
				deliveryNoteService.submit('/reports/partyWise/'+$scope.simple,$scope.report)
				.then(function successCallback(response){
					$scope.report.parties=response.data;
					if($scope.report.parties.length==0)
					{
					  $window.alert('No Record Found');
					}
					else
					{
						var count=0;
						$scope.report.closingBalance=0;
						angular.forEach($scope.report.parties,function(value,key){
							value.sno=++count;
							$scope.report.closingBalance +=value.transactionBalance;
							//value.transactionType=$scope.report.transactionType;
						});
					}
					
				},function failureCallback(){
					$window.alert('Server Error');
				})
			}
			
		}
		
	}
	$scope.exportReport=function()
	{
		
		$http({
	    	  method: 'POST',
	    	  url: '/reports/'+$scope.activeMode,
	    	  data: $scope.report,
	    	  responseType : 'arraybuffer',//THIS IS IMPORTANT
	    	}).then(function successCallback(response) {
	    	   
				var data = new Blob([ response.data ], {
	                type : 'application/ms-excel'
	            });
	            var disposition = response.headers('Content-Disposition');
                var filename = getFileNameFromHttpResponse(response);
                download(data,filename);
                //window.saveAs(data, filename);
	            //$window.open(excelUrl);
	            //$window.location=excelUrl;
	    		
	    	  }, function errorCallback(response) {
	    		  $window.alert('Server Error');
	    	  });
	}
	
	function getFileNameFromHttpResponse(httpResponse) {
	      var contentDispositionHeader = httpResponse.headers('Content-Disposition');
	      var result = contentDispositionHeader.split(';')[1].trim().split('=')[1];
	      return result.replace(/"/g, '');
	  }
	function download(data, filename)
	{
	        var a = document.createElement('a');
	        a.href = window.URL.createObjectURL(data);
	        a.download = filename;
	        a.click();
	}
}]);