 
app.controller('OrderController', ['$scope','NgTableParams','deliveryNoteService','$http','popUpService','productService','$window','orderService',function($scope,NgTableParams,deliveryNoteService,$http,popUpService,productService,$window,orderService) {
	$scope.heading='Order';
	$scope.isRowAdded=false;
	$scope.isSaved=false;
	$scope.simple=false;
	$scope.isUpdate=false;
	$scope.isNewNote=false;
	$scope.stateGSTType='I/SGST';
	$scope.orderNotes=[];
	$scope.newNotes=[];
	$scope.deletedOrders=[];
	$scope.updatedOrders=[];
	$scope.deletedOrderNotes=[];
	$scope.updatedOrderNotes=[];
	
	$scope.deliveryNotes={
			deliveryNoteID:'',
			deliveryDate : new Date(),
			customerID :'',
			customerName : '',
			grandTotal:0,
			notes :[]
	};
	$scope.orders=[];
	$scope.notes=[];
	$scope.counter=0;
	
	$scope.modes={
			add : false,
			view : true
	};
	$scope.changeMode=function(activeMode)
	{
		angular.forEach($scope.modes,function(value,mode)
		{
			$scope.modes[mode] = mode==activeMode?true:false;
		})
		
		if($scope.modes.add)
		{
			$scope.generateDeliveryNoteID();
		}
		
	}
	
	//This service gets all the products
	$scope.products=productService.query();
	
	//This service gets the Customer ID and Customer Name
	deliveryNoteService.getObject('customer').then(function successCallback(response) 
	{
		$scope.customers=response.data;
	});
	
	$scope.generateDeliveryNoteID=function()
	{
		deliveryNoteService.generateID('order',$scope.deliveryNotes.deliveryDate,$scope.simple)
		.then(function successCallback(response) 
		{
			console.log('success'+response.data);
			$scope.deliveryNotes.deliveryNoteID=response.data.deliveryNoteID;
			
		},function failureCallback(response){
			console.log(response.status);
			$scope.deliveryNotes.deliveryNoteID='Error';
		});
	}
	
	$scope.loadProductByID=function(index)
	{
		var _note=$scope.notes[index];
		_note.isProductSelected=true;
		angular.forEach($scope.products,function(value,key){
			if(value.productID==_note.productID)
				{
					_note.rate=value.rate;
					_note.productDescription=value.productDescription;
					_note.cgst=value.gst/2;
					_note.sgst=value.gst/2;
					console.log(_note.productDescription);
				}
		});
	};
	$scope.loadEditedProductByID=function(index)
	{
		var _note=$scope.orderNotes[index];
		_note.isProductSelected=true;
		angular.forEach($scope.products,function(value,key){
			if(value.productID==_note.productID)
				{
					_note.rate=value.rate;
					_note.productDescription=value.productDescription;
					_note.cgst=value.gst/2;
					_note.sgst=value.gst/2;
					console.log(_note.productDescription);
				}
		});
		var present=_.findWhere($scope.orginalOrderNotes, {productID:_note.productID});
		if(!present)
		{
			$scope.orginalOrderNotes.push(_note);
		}
		reloadOrderNote($scope.orderNotes);
	};
	

	$scope.loadProductByDesc=function(index)
	{
		var _note=$scope.notes[index];
		_note.isProductSelected=true;
		angular.forEach($scope.products,function(value,key){
			if(value.productDescription==_note.productDescription)
				{
					_note.rate=value.rate;
					_note.productID=value.productID;
					_note.cgst=value.gst/2;
					_note.sgst=value.gst/2;
				}
		});
		reloadOrderNote($scope.orderNotes);
	};
	

	$scope.loadEditedProductByDesc=function(index)
	{
		var _note=$scope.orderNotes[index];
		
		_note.isProductSelected=true;
		angular.forEach($scope.products,function(value,key){
			if(value.productDescription==_note.productDescription)
				{
					_note.rate=value.rate;
					_note.productID=value.productID;
					_note.cgst=value.gst/2;
					_note.sgst=value.gst/2;
				}
		});
		var present=_.findWhere($scope.orginalOrderNotes, {productID:_note.productID});
		if(!present)
		{
			$scope.orginalOrderNotes.push(_note);
		}
		reloadOrderNote($scope.orderNotes);
	};
	
	$scope.loadCustomerName=function()
	{
		$scope.isSearch=false;
		$scope.isUpdate=false;
		var _customerID=$scope.deliveryNotes.customerID;
		angular.forEach($scope.customers,function(value,key){
			if(value.customerID==_customerID)
				{
					$scope.deliveryNotes.customerName=value.customerName;
				}
		});
		deliveryNoteService.getStateGSTType($scope.deliveryNotes.customerID).then(function successCallback(response) 
		{
			$scope.stateGSTType=response.data.stateGSTType;
		});
	};
	
	$scope.loadCustomerID=function()
	{
		$scope.isSearch=false;
		$scope.isUpdate=false;
		var _customerName=$scope.deliveryNotes.customerName;
		angular.forEach($scope.customers,function(value,key){
			if(value.customerName==_customerName)
				{
					$scope.deliveryNotes.customerID=value.customerID;
				}
		});
		deliveryNoteService.getStateGSTType($scope.deliveryNotes.customerID).then(function successCallback(response) 
		{
			$scope.stateGSTType=response.data.stateGSTType;
		});
	};
	
	$scope.setUpdatableNotes=function(_note)
	{
		var present=false;
		angular.forEach($scope.updatedOrderNotes,function(value,key){
			if(_note == value)
				{
					present=true;
				}
		});
		if(!present && !_note.isNewNote)
		{
			$scope.updatedOrderNotes.push(_note);
		}
	}
	
	$scope.calculateTotal=function(index)
	{
		
		var _note=$scope.notes[index];
		_note.total=_note.quantity*_note.rate;
		_note.netTotal=_note.total-(_note.total*_note.discount)/100;
		_note.cgstAmount=(_note.netTotal*_note.cgst)/100;
		_note.sgstAmount=(_note.netTotal*_note.sgst)/100;
		_note.netPrice=_note.netTotal+_note.cgstAmount+_note.sgstAmount;
		if($scope.simple)
		{
			$scope.calGrandTotal(_note.deliveryNoteID,_note.netTotal);
		}
		else
		{
			$scope.calGrandTotal(_note.deliveryNoteID,_note.netPrice);
		}
	}
	$scope.editTotal=function(index)
	{
		var _note=$scope.orderNotes[index];
		
		_note.total=_note.quantity * _note.rate;
		_note.netTotal=_note.total-(_note.total*_note.discount)/100;
		_note.cgstAmount=(_note.netTotal*_note.cgst)/100;
		_note.sgstAmount=(_note.netTotal*_note.sgst)/100;
		_note.netPrice=_note.netTotal + _note.cgstAmount + _note.sgstAmount;
		if($scope.simple)
		{
			editGrandTotal(_note.deliveryNoteID,_note.netTotal);
		}
		else
		{
			editGrandTotal(_note.deliveryNoteID,_note.netPrice);
		}
	}
	
	$scope.calculateNetTotal=function(index)
	{
		var _note=$scope.notes[index];
		_note.netTotal=_note.total-(_note.total*_note.discount)/100;
		_note.cgstAmount=(_note.netTotal*_note.cgst)/100;
		_note.sgstAmount=(_note.netTotal*_note.sgst)/100;
		_note.netPrice=_note.netTotal+_note.cgstAmount+_note.sgstAmount;
		if($scope.simple)
		{
			$scope.calGrandTotal(_note.deliveryNoteID,_note.netTotal);
		}
		else
		{
			$scope.calGrandTotal(_note.deliveryNoteID,_note.netPrice);
		}
		
		
	};
	$scope.editNetTotal=function(index)
	{
		var _note=$scope.orderNotes[index];
		$scope.isUpdate=true;
		
		_note.netTotal=_note.total-(_note.total*_note.discount)/100;
		_note.cgstAmount=(_note.netTotal*_note.cgst)/100;
		_note.sgstAmount=(_note.netTotal*_note.sgst)/100;
		_note.netPrice=_note.netTotal+_note.cgstAmount+_note.sgstAmount;
		if($scope.simple)
		{
			editGrandTotal(_note.deliveryNoteID,_note.netTotal);
		}
		else
		{
			editGrandTotal(_note.deliveryNoteID,_note.netPrice);
		}		
	};
	$scope.calGrandTotal=function(orderId,netPrice,isDelete)
	{
			$scope.deliveryNotes.grandTotal=0;
			if($scope.simple)
			{
				angular.forEach($scope.notes,function(value,key){
					$scope.deliveryNotes.grandTotal = $scope.deliveryNotes.grandTotal + value.netTotal;
				});
			}
			else
			{
				angular.forEach($scope.notes,function(value,key){
					$scope.deliveryNotes.grandTotal = $scope.deliveryNotes.grandTotal + value.netPrice;
				});
			}
	}
	var editGrandTotal=function(orderId,netPrice,isDelete)
	{
			angular.forEach($scope.orders,function(order,key)
			{
				if(order.deliveryNoteID == orderId)
				{
					if(isDelete)
					{
						order.grandTotal = order.grandTotal - netPrice;
					}
					else
					{
						order.grandTotal=0;
						angular.forEach($scope.orderNotes,function(value,key)
						{
							if($scope.simple)
							{
								order.grandTotal += value.netTotal;
							}
							else
							{
								order.grandTotal += value.netPrice;
							}
						});
					}
					
				}
			});
			reloadOrderNote($scope.orderNotes);			
	}
	var setupdatableOrders=function()
	{
		var present=_.findWhere($scope.updatedOrders, {deliveryNoteID:$scope.relatedOrder.deliveryNoteID});
		if(!present)
		{
			$scope.updatedOrders.push($scope.relatedOrder);
			reloadOrder($scope.orders);
		}
	}
	$scope.addNewNote=function()
	{
		
		$scope.isRowAdded=true;
		$scope.note={
				productID:'',
				productDescription:'',
				notation:'',
				quantity:0,
				rate:0,
				total:0,
				netTotal:0,
				discount:0,
				cgst:0,
				cgstAmount:0,
				sgst:0,
				sgstAmount:0,
				netPrice:0,
				isProductSelected: false
		};
		
		$scope.notes.push($scope.note);
		$scope.deliveryNotes.notes=$scope.notes;
		reloadNotes($scope.notes);
	}
	$scope.addNewEditNote=function()
	{
		$scope.note={
				productID:'',
				productDescription:'',
				notation:'',
				quantity:0,
				rate:0,
				total:0,
				netTotal:0,
				discount:0,
				cgst:0,
				cgstAmount:0,
				sgst:0,
				sgstAmount:0,
				netPrice:0,
				isProductSelected: false,
				isEditing: true,
		};
		
		var order=$scope.orderNotes[0];
		$scope.note.deliveryNoteID=order.deliveryNoteID;
		
		$scope.orderNotes.push($scope.note);
		$scope.newNotes.push($scope.note);
		$scope.isUpdate=true;
		reloadOrderNote($scope.orderNotes);
	}
	
	$scope.deleteNote=function(index)
	{
		$scope.notes.splice(index, 1);
		if($scope.notes.length==0)
			{
				$scope.isRowAdded=false;
				$scope.isSaved=false;
				$scope.deliveryNotes.grandTotal=0;
			}
		$scope.calGrandTotal();
		reloadNotes($scope.notes);
	}
	var resetAll=function()
	{
		$scope.newNotes=[];
		$scope.updatedOrders=[];
		$scope.deletedOrderNotes=[];
		$scope.updatedOrderNotes=[];
	}
	$scope.fetchOrders=function()
	{
		
		if(($scope.deliveryNotes.fromDate || $scope.deliveryNotes.toDate) && (!$scope.deliveryNotes.fromDate || !$scope.deliveryNotes.toDate))
		{
			popUpService.openInfoBox('Please select proper range(from-to)!',$scope);
		}
		else
		{
			deliveryNoteService.submit('order/fetch/'+$scope.simple,$scope.deliveryNotes)
			.then(function successCallback(response) 
			{
				$scope.orders=response.data;
				if($scope.orders.length==0)
				{
					popUpService.openInfoBox('No Record Found!',$scope);
				}
				else
				{
					$scope.orderTable = new NgTableParams({}, { dataset: $scope.orders});
					$scope.isSearch=true;
				}
				
			},function failureCallback(response){
				popUpService.openInfoBox('Server Error!',$scope);
			});
			
		}
	}
	$scope.CancelOrder=function(order)
	{
		popUpService.openConfirmBox('order',$scope).then(function (success) 
		{
			orderService.remove({_simple:$scope.simple,_Id: order.deliveryNoteID},function()
			{
				_.remove($scope.orders, function(item) {
			        return order === item;
			      });
				reloadOrder($scope.orders);
				popUpService.openInfoBox('Order Deleted Successfully!',$scope);
			},
			function()
			{
				popUpService.openInfoBox('Server Error!',$scope);
			});
		}, function (failure){
			//do nothing
		});
	}
	
	var DeleteOrderNote=function(note)
	{
		if($scope.orderNotes.length==1)
		{
			popUpService.openInfoBox('If you wish to delete the order, Please use delete button at order level!',$scope);
		}
		else
		{
			if(note.isNewNote)
			{
				angular.forEach($scope.newNotes,function(value,key){
					if(note == value)
					{
						$scope.newNotes.splice(key,1);
					}
				});
				$scope.orderNotes.splice(index, 1);
			}
			else
			{
				$scope.deletedOrderNotes.push(note.objid);
				_.remove($scope.orderNotes, function(item) {
			        return note === item;
			      });
				if($scope.simple)
				{
					editGrandTotal(note.deliveryNoteID,note.netTotal,true);
				}
				else
				{
					editGrandTotal(note.deliveryNoteID,note.netPrice,true);
				}
			}
		}
		$scope.isUpdate=true;
		
	}
	
	$scope.UpdateDeliveryNote=function()
	{
		var request={};
		request.strSimple=$scope.simple;
		request.newNotes=$scope.newNotes;
		request.updatedOrders=$scope.updatedOrders;
		request.deletedOrderNotes=$scope.deletedOrderNotes;
		request.updatedOrderNotes=$scope.updatedOrderNotes;
		
		deliveryNoteService.submit('order',request).then(function successCallback(response) 
		{
			resetAll();
			$scope.fetchOrders();
			popUpService.openInfoBox('Order successfully updated!',$scope);
		},
		function failureCallback(response)
		{
			popUpService.openInfoBox('Order failed to update!',$scope);
		});
	}
	$scope.fetchOrderNote=function(index)
	{
		var order=$scope.orders[index];
		var orderId=order.deliveryNoteID;
		var invoiceGenerated=order.invoiceGenerated;
		deliveryNoteService.getObject('order/notes',orderId,$scope.simple)
		.then(function successCallback(response) 
		{
			$scope.orderNotes=response.data;
			$scope.orginalOrderNotes=angular.copy($scope.orderNotes);
			angular.forEach($scope.orderNotes,function(value,key){
				value.invoiceGenerated=invoiceGenerated;
				value.isProductSelected=true;
				value.isNewNote=false;
			});

			reloadOrderNote($scope.orderNotes);
		},function failureCallback(response){
			popUpService.openInfoBox('Server Error!',$scope);
		});
		$scope.relatedOrder=order;
		popUpService.openEditForm($scope,'views/order/EditOrder.html',1400);
	}
	
	$scope.downloadOrderNote=function(index)
	{
		var order=$scope.orders[index];
		// Simple POST request
    	$http({
    	  method: 'POST',
    	  url: '/order/generate/'+$scope.simple,
    	  data: order,
    	  headers:{'Content-type': 'application/json'},
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
    		  popUpService.openInfoBox('Server Error.Unable to download Order!',$scope);
    	  });
	}
	$scope.saveDeliveryNotes=function()
	{
		if($scope.deliveryNotes.customerID)
		{
			// Simple POST request
	    	$http({
	    	  method: 'POST',
	    	  url: '/order/'+$scope.simple,
	    	  data: $scope.deliveryNotes
	    	}).then(function successCallback(response) {
	    	    // this callback will be called asynchronously
	    	    // when the response is available
	    		if(response.data==true)
	    		{
	    			popUpService.openInfoBox('Delivery Note Saved Successfully!',$scope);
	    			reset();
	    		}
	    		else
				{
	    			popUpService.openInfoBox('Unable to save Delivery Note!',$scope);
				}
	    	  }, function errorCallback(response) {
	    		  popUpService.openInfoBox('Server Error!',$scope);
	    	  });
		}
		else
		{
			popUpService.openInfoBox('Please select customer before saving Order!',$scope);
		}
		
	}

	var reset=function()
	{
        $scope.deliveryNotes={
    			deliveryNoteID:'',
    			deliveryDate : new Date(),
    			customerID :'',
    			customerName : '',
    			grandTotal:0,
    			notes :[]
    	};
        $scope.counter=0;
        $scope.notes=[];
        $scope.notes.push();
		$scope.deliveryNotes.notes=$scope.notes;
		$scope.generateDeliveryNoteID();
		reloadNotes($scope.notes);
	}
	
	$scope.downloadDeliveryNote=function()
	{
		$scope.isSaved=false;
        
		// Simple POST request
    	$http({
    	  method: 'POST',
    	  url: '/order/generate/'+$scope.simple,
    	  data: $scope.deliveryNotes,
    	  headers:{'Content-type': 'application/json'},
          responseType : 'arraybuffer',//THIS IS IMPORTANT
    	}).then(function successCallback(response) {
    		
			var pdfFile = new Blob([ response.data ], {
                type : 'application/pdf'
            });
            var pdfUrl = URL.createObjectURL(pdfFile);
            $window.open(pdfUrl);
    	}, function errorCallback(response) {
    		popUpService.openInfoBox('Server Error!',$scope);
    	  });
	}
	
	
	$scope.cancel=function(row, rowForm) 
	{
	      var originalRow = resetRow(row, rowForm);
	      
	      angular.extend(row, originalRow);
    }

	$scope.del=function(row) 
	{
			DeleteOrderNote(row);
	      _.remove($scope.orderNoteTable.settings().dataset, function(item) {
	        return row === item;
	      });
	      $scope.orderNoteTable.reload().then(function(data) {
		        if (data.length === 0 && $scope.orderNoteTable.total() > 0) {
		        	$scope.orderNoteTable.page($scope.orderNoteTable.page() - 1);
		        	$scope.orderNoteTable.reload();
		        }
		      });
    }
	var reloadNotes=function(notes)
	{
		$scope.noteTable = new NgTableParams({}, {
		      filterDelay: 0,
		      dataset: notes
		    });
	}
	var reloadOrderNote=function(orderNotes)
	{
		$scope.orderNoteTable = new NgTableParams({}, {
		      filterDelay: 0,
		      dataset: orderNotes
		    });
	}
	var reloadOrder=function(orders)
	{
		$scope.orderTable = new NgTableParams({}, {
		      filterDelay: 0,
		      dataset: orders
		    });
	}
	var resetRow=function(row, rowForm)
	{
	      row.isEditing = false;
	      rowForm.$setPristine();
	      //$scope.orderNoteTable.untrack(row);
	      return _.findWhere($scope.orginalOrderNotes, {productID:row.productID});
    }

	$scope.save=function(row, rowForm) 
	{
		  $scope.setUpdatableNotes(row);
		  setupdatableOrders();
	      var originalRow = resetRow(row, rowForm);
	      angular.extend(originalRow, row);
    }
}]);
