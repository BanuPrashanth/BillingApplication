app.factory('stateService',['$http',function($http){
	return {
		getStates: function()
		{
			// Simple GET request
			return $http({
			  method: 'GET',
			  url: 'api/states',
			})
		}
	}
	
}]);

app.factory('productService',function($resource){
	return $resource('/product/:_Id',{_Id: '@_Id'},
		{
			'update': { method:'PUT' }
		}
	);
})

app.factory('userService',function($resource){
	return $resource('/user/:_Id',{_Id: '@_Id'},
		{
			'update': { method:'PUT' }
		}
	);
})

app.factory('customerService',function($resource){
	return $resource('/customer/:_Id',{_Id: '@_Id'},
		{
			'update': { method:'PUT' }
		}
	);
})

app.factory('orderService',function($resource){
	return $resource('/order/:_simple/:_Id',{_simple: '@_simple', _Id: '@_Id'},
		{
			'update': { method:'PUT' }
		}
	);
})

app.factory('popUpService',['ngDialog',function(ngDialog){
	return {
		openConfirmBox: function(message,_scope)
		{
			_scope.message=message;
			return ngDialog.openConfirm({
				template: 'views/popUp/ConfirmBox.html',
			    scope: _scope
			})
		},
		openEditForm: function(_scope,view,_width)
		{
			ngDialog.openConfirm(
	    	{
	    		template: view,
	    		scope: _scope,  //Pass the scope object if you need to access in the template
	    		width:_width
	  		}).then(function(){
	  			//success
	  		},function (){
	  			//failure
	  		})
		},
		openInfoBox: function(message,_scope)
		{
			_scope.message=message;
			ngDialog.openConfirm({
				template: 'views/popUp/InfoBox.html',
			    scope: _scope
			})
		}
	}
}])

app.factory('deliveryNoteService',['$http',function($http){
	return {
		getObject: function(objectType,id,simple)
		{
			var strURL='/'+objectType;
			if(id)
			{
				strURL +='/'+id;
			}
			if(simple!=undefined)
			{
				strURL +='/'+simple;
			}
			// Simple GET request
			return $http({
			  method: 'GET',
			  url: strURL
			})
		},
		getStateGSTType: function(customerID)
		{
			return $http({
				method: 'GET',
				url: '/api/get/stateGSTType/'+customerID
			})
		},
		generateInvoice: function(invoice,simple){
			return $http({
				method: 'POST',
				url: '/invoice/generate/'+simple,
				data: invoice
			})
		},
		generateID: function(objectType,date,simple)
		{
			var strURl=strURl='/'+objectType+'/generate/Id';
			if(date!=null && date!='')
			{
				strURl=strURl+'/'+date+'/'+simple;
			}
			
			return $http({
				method: 'GET',
				url: strURl
			})
		},
		submit: function(strURl,strData)
		{
			return $http({
				method: 'POST',
				url: strURl,
				data: strData
			})
		},
		update: function(strURl,strData)
		{
			return $http({
				method: 'PUT',
				url: strURl,
				data: strData
			})
		},
		deleteObject: function(strURl)
		{
			return $http({
				method: 'DELETE',
				url: strURl
			})
		}
	}
	
}]);



app.factory('downloadService',function getStream(params){
	return {
		download: function()
		{
			console.log("RUNNING");
		    var deferred = $q.defer();

		    $http({
		        url:'/api/deliveryNote/generate',
		        method:"POST",
		        data:params,
		        headers:{'Content-type': 'application/json'},
		        responseType : 'arraybuffer',//THIS IS IMPORTANT
		       })
		       .success(function (data) {
		           console.debug("SUCCESS");
		           deferred.resolve(data);
		       }).error(function (data) {
		            console.error("ERROR");
		            deferred.reject(data);
		       });

		    return deferred.promise;
		}
	}
    
   });
