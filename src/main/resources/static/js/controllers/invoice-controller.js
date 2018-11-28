app.controller('invoiceController', ['$scope','$http','deliveryNoteService','$window','customerService',function($scope,$http,deliveryNoteService,$window,customerService) {
	$scope.heading='Invoice';
	
	reset=function()
	{
		$scope.invoice={
				invoiceID: '',
				invoiceDate: new Date(),
				customerID: '',
				customerName: '',
				notes: []
				};
		$scope.invoices=[];
		$scope.invoiceNotes=[]
		$scope.isSaved=false;
		$scope.simple=false;
		$scope.isInvoiceGenerated=false;
		$scope.createMode=true;
		$scope.viewMode=false;
		$scope.editMode=false;
		$scope.isSearch=false;
	}
	reset();
	$scope.changeMode=function(mode)
	{
		if(mode=='create')
		{
			$scope.createMode=true;
			$scope.viewMode=false;
			$scope.editMode=false;
		}
		else if(mode=='view')
		{
			$scope.createMode=false;
			$scope.viewMode=true;
			$scope.editMode=false;
		}
		else if(mode=='edit')
		{
			$scope.createMode=false;
			$scope.viewMode=true;
			$scope.editMode=false;
		}
	}
	//This service gets the Customer ID and Customer Name
	deliveryNoteService.getObject('customer').then(function successCallback(response) 
	{
		$scope.customers=response.data;
	});
	
	$scope.fetchInvoice=function()
	{
		deliveryNoteService.getObject('invoice',$scope.invoice.customerID,$scope.simple).then(function successCallback(response) 
		{ 
			var count=0;
			var resp=response.data;
			$scope.invoices=resp.invoices;
			if($scope.invoices.length==0)
			{
				$window.alert('No Record Found!');
				clearInvoiceSearch();
			}
			else
			{
				$scope.isSearch=true;
				angular.forEach($scope.invoices,function(value,key){
					value.sno=++count;
				});
			}			
		});
	}
	clearInvoiceSearch=function()
	{
		$scope.invoices=[];
		$scope.invoiceNotes=[];
		$scope.isSearch=false;
		$scope.isView=false;
	}
	$scope.loadCustomerName=function()
	{
		var _customerID=$scope.invoice.customerID;
		angular.forEach($scope.customers,function(value,key){
			if(value.customerID==_customerID)
				{
					$scope.invoice.customerName=value.customerName;
					$scope.invoice.openingBalance = value.openingBalance;
				}
		});
		
	};
	
	$scope.loadCustomerID=function()
	{
		var _customerName=$scope.invoice.customerName;
		angular.forEach($scope.customers,function(value,key){
			if(value.customerName==_customerName)
				{
					$scope.invoice.customerID=value.customerID;
					$scope.invoice.openingBalance = value.openingBalance;
				}
		});
		
	};
	
	$scope.fetchInvoiceNotes=function(index)
	{
		var inv=$scope.invoices[index];
		deliveryNoteService.getObject('invoice/generate',inv.invoiceID,$scope.simple)
		.then(function successCallback(response) 
		{
			var count=0;
			$scope.invoiceNotes=response.data;
			if($scope.invoiceNotes.length==0)
			{
				$window.alert('No Record Found!');
			}
			else
			{
				$scope.isView=true;
				angular.forEach($scope.invoiceNotes,function(value,key){
					value.sno=++count;
				});
			}
			
		},function failureCallback(response){
			console.log(response.status);
			
		});
		
	}
	
	$scope.downloadInvoiceNotes=function(index)
	{
		var invDonwload=$scope.invoices[index];
		// Simple POST request
    	$http({
    	  method: 'POST',
    	  url: '/invoice/download/'+$scope.simple,
    	  data: invDonwload,
    	  responseType : 'arraybuffer',//THIS IS IMPORTANT
    	}).then(function successCallback(response) {
    	    // this callback will be called asynchronously
    	    // when the response is available
			var pdfFile = new Blob([ response.data ], {
                type : 'application/pdf'
            });
            var pdfUrl = URL.createObjectURL(pdfFile);
            $window.open(pdfUrl);
            //var printwWindow = $window.open(pdfUrl);
            //printwWindow.print();
    		
    	  }, function errorCallback(response) {
    	    // called asynchronously if an error occurs
    	    // or server returns response with an error status.
    		  $window.alert('Server Error!');
    	  });
	}
	
	$scope.generateInvoice=function()
	{
		deliveryNoteService.generateInvoice($scope.invoice,$scope.simple).then(function successCallback(response) 
		{ 
			var count=0;
			var grossAmount=0;
			var cgstAmount=0;
			var sgstAmount=0;
			$scope.invoice.notes=response.data;
			if($scope.invoice.notes.length==0)
			{
				$window.alert('No Record Found!');
			}
			else
			{
				$scope.isSaved=false;
				$scope.isInvoiceGenerated=true;
				angular.forEach($scope.invoice.notes,function(value,key){
					value.sno=++count;
					grossAmount = grossAmount + value.netTotal;
					cgstAmount = cgstAmount + value.cgstAmount;
					sgstAmount = sgstAmount + value.sgstAmount;
				});
				$scope.invoice.grossAmount = grossAmount;
				$scope.invoice.cgstAmount = cgstAmount;
				$scope.invoice.sgstAmount = sgstAmount;
				$scope.invoice.gstAmount = $scope.invoice.cgstAmount + $scope.invoice.sgstAmount;
				$scope.invoice.netAmount=$scope.invoice.gstAmount + $scope.invoice.grossAmount;
				$scope.invoice.closingBalance = $scope.invoice.netAmount - Number($scope.invoice.openingBalance);
				if($scope.invoice.closingBalance < 0){
					$scope.invoice.closingAdvanceAmount = Math.abs($scope.invoice.closingBalance);
				}
			}
			
		});
	}
	
	$scope.generateInvoiceID=function()
	{
		deliveryNoteService.generateID('invoice',$scope.invoice.invoiceDate,$scope.simple)
		.then(function successCallback(response) 
		{
			$scope.invoice.invoiceID=response.data.invoiceID;
			
		},function failureCallback(response){
			$window.alert('Server Error While Generating Invoice Id!');
		});
	}
	
	$scope.generateInvoiceID();
	
	$scope.saveInvoice=function()
	{
		// Simple POST request
    	$http({
    	  method: 'POST',
    	  url: '/invoice/'+$scope.simple,
    	  data: $scope.invoice
    	}).then(function successCallback(response) {
    	    // this callback will be called asynchronously
    	    // when the response is available
    		if(response.data==true)
    		{
    			$scope.isSaved=true;
    			$window.alert('Invoice Save Successfully!');    			
    		}
    		else
			{
    			$scope.isSaved=false;
    			$window.alert('Unable to Save Invoice!');
			}
    	  }, function errorCallback(response) {
    		  $window.alert('Server Error!');
    	  });
	}

	$scope.downloadInvoice=function()
	{
		// Simple POST request
    	$http({
    	  method: 'POST',
    	  url: '/invoice/download/'+$scope.simple,
    	  data: $scope.invoice,
    	  responseType : 'arraybuffer',//THIS IS IMPORTANT
    	}).then(function successCallback(response) {
    	    // this callback will be called asynchronously
    	    // when the response is available
			var pdfFile = new Blob([ response.data ], {
                type : 'application/pdf'
            });
            var pdfUrl = URL.createObjectURL(pdfFile);
            $window.open(pdfUrl);
            reset();
    	  }, function errorCallback(response) {
    		  $window.alert('Server Error!');
    	  });
	}
	
}]);
