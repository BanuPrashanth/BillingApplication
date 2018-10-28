
var app = angular.module('productPlannerApp', ['ngRoute','ngResource','ngLoadingSpinner','ngAnimate', 'ui.bootstrap','ngDialog','ngTable']);

app.config(function($routeProvider){
    $routeProvider
        .when('/login',{
        	
            templateUrl: '/views/LoginPage.html',
            controller: 'loginController'
        })
        .when('/product',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/product/Product.html',
            controller: 'productController'
        })
        .when('/user',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/user/User.html',
            controller: 'userController'
        })
        .when('/deliveryNote',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/order/Order.html',
            controller: 'OrderController'
        })
        .when('/customer',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/customer/Customer.html',
            controller: 'customerController'
        })
        .when('/owner',{
            templateUrl: '/views/OwnerInfo.html',
            controller: 'ownerController'
        })
        .when('/invoice',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/InvoiceGeneration.html',
            controller: 'invoiceController'
        })
        .when('/installment',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/InstallmentReceipt.html',
            controller: 'installmentReceiptController'
        })
        .when('/reports',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/Reports.html',
            controller: 'reportController'
        })
        .when('/dashboard',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/DashBoard.html',
            controller: 'dashboardController'
        })
        .otherwise(
            { redirectTo: '/'}
        );
    
    //$locationProvider.html5Mode(true);
});