 
app.controller('installmentReceiptController', ['$scope','deliveryNoteService','$http','$window','$location',function($scope,deliveryNoteService,$http,$window,$location) {
	$scope.receipt={
			receiptID: '',
			receiptDate: new Date(),
			customerID: '',
			openingBalance: 0,
			transactionType: 'Cash',
			comment: 'NA',
			closingBalance: 0,
			totalAmtPaid: 0,
			openingAdvanceAmount: 0.0,
			closingAdvanceAmount: 0.0
			};

	$scope.simple=false;
	$scope.isSaveDisabled=true;
	
	
	//This service gets the Customer ID and Customer Name
	deliveryNoteService.getObject('customer').then(function successCallback(response) 
	{
		$scope.customers=response.data;
	});
	
	$scope.loadCustomerName=function()
	{
		var _customerID=$scope.receipt.customerID;
		angular.forEach($scope.customers,function(value,key){
			if(value.customerID==_customerID)
				{
					$scope.receipt.customerName=value.customerName;
				}
		});
		$scope.generateReceipt();
	};
	
	$scope.loadCustomerID=function()
	{
		var _customerName=$scope.receipt.customerName;
		angular.forEach($scope.customers,function(value,key){
			if(value.customerName==_customerName)
				{
					$scope.receipt.customerID=value.customerID;
				}
		});
		$scope.generateReceipt();
	};
	$scope.calTotalAmtPaid=function()
	{
		$scope.receipt.closingBalance=0;
		$scope.receipt.clearAmount=0;
		$scope.receipt.totalAmtPaid=parseInt($scope.receipt.totalAmtPaid);
		var totalAmtPaid=$scope.receipt.totalAmtPaid+$scope.receipt.openingAdvanceAmount;
		angular.forEach($scope.receipt.invoices,function(invoice,key){
			if(invoice.previousBalance<totalAmtPaid)
			{
				invoice.invoiceAmtPaid=invoice.previousBalance;
			}
			else
			{
				invoice.invoiceAmtPaid=totalAmtPaid;
			}
		totalAmtPaid-=invoice.invoiceAmtPaid;
		invoice.currentBalance=invoice.previousBalance - invoice.invoiceAmtPaid;
		$scope.receipt.closingBalance+=invoice.currentBalance;
		$scope.receipt.clearAmount+=invoice.invoiceAmtPaid;
			
		});
		$scope.receipt.closingAdvanceAmount=totalAmtPaid;
		$scope.isSaveDisabled=false;
	};
	$scope.generateReceipt=function()
	{
		$scope.receipt.openingBalance=0;
		$scope.receipt.closingBalance=0;
		$scope.receipt.totalAmtPaid=0;
		deliveryNoteService.getObject('invoice',$scope.receipt.customerID,$scope.simple).then(function successCallback(response) 
		{ 
			var count=0;
			
			var receipt=response.data;
			
			$scope.receipt.invoices=receipt.invoices;
			$scope.receipt.openingAdvanceAmount=receipt.openingAdvanceAmount;
			if($scope.receipt.invoices.length==0)
			{
				$window.alert('No Record Found!');
			}
			
			angular.forEach($scope.receipt.invoices,function(value,key){
				value.sno=++count;
				if(!value.previousBalance)
				{
					value.previousBalance=value.netAmount;
				}
				$scope.receipt.openingBalance+=value.previousBalance;
			});
			
		});
	
	};
	
	$scope.saveReceipt=function()
	{
    	$http({
    	  method: 'POST',
    	  url: '/receipt/save/'+$scope.simple,
    	  data: $scope.receipt
    	}).then(function successCallback(response) {
    		if(response.data==true)
    		{
    			$scope.isSaved=true;
    			$window.alert('Receipt Saved Successfully!');
    		}
    		else
			{
    			$scope.isSaved=false;
    			$window.alert('Unable to Save Receipt!');
			}
    	  }, function errorCallback(response) {
    		  $window.alert('Server Error!');
    	  });
	}
	
	$scope.generateReceiptID=function()
	{
		deliveryNoteService.generateID('receipt',$scope.receipt.receiptDate,$scope.simple)
		.then(function successCallback(response) 
		{
			$scope.receipt.receiptID=response.data.receiptID;
			
		},function failureCallback(response){
			$window.alert('Server Error!');
		});
		if($scope.receipt.customerID.length)
			{
				$scope.generateReceipt();
			}
	};
	$scope.generateReceiptID();	
	
	$scope.downloadReceipt=function()
	{
    	$http({
    	  method: 'POST',
    	  url: '/receipt/download/'+$scope.simple,
    	  data: $scope.receipt,
    	  responseType : 'arraybuffer',//THIS IS IMPORTANT
    	}).then(function successCallback(response) {
			var pdfFile = new Blob([ response.data ], {
                type : 'application/pdf'
            });
            var pdfUrl = URL.createObjectURL(pdfFile);
            $window.open(pdfUrl);
    	  }, function errorCallback(response) {
    		  $window.alert('Server Error!');
    	  });
	}
	$scope.resetReceipt=function()
	{
		$scope.receipt={
				receiptID: '',
				receiptDate: new Date(),
				customerID: '',
				openingBalance: 0,
				comment: '',
				closingBalance: 0,
				totalAmtPaid: 0,
				openingAdvanceAmount: 0.0,
				closingAdvanceAmount: 0.0
				};
		$scope.isSaved=false;
		$scope.generateReceiptID();
	}
	$scope.checkNegativeVal=function(value)
	{
		if(value.indexOf('-')!=-1)
		{
			$scope.error='Negative values are not allowed!';
			$scope.enableCalculate=false;
		}
		else
		{
			$scope.error='';
			$scope.enableCalculate=true;
		}
	}
}]);
