var app = angular.module('cvconverter', ['ui']);

app.config(
	['$routeProvider', function ($routeProvider) {
		$routeProvider.when('/person', {
			templateUrl : 'person.html',
			controller : PersonController
		}).when('/anpassning', {
			templateUrl : 'anpassning.html',
			controller : AdjustmentController
		}).when('/layout', {
			templateUrl : 'layout.html',
			controller : LayoutController
			
		}).when('/admin/layouter', {
			templateUrl : 'admin-layouter.html',
			controller : AdminLayoutsController
		}).when('/admin/ny', {
			templateUrl : 'admin-ny-layout.html',
			controller : AdminNewLayoutController
		}).when('/admin/layouter/:id', {
			templateUrl : 'admin-edit-layout.html',
			controller : AdminEditLayoutController
		}).when('/admin/base64', {
			templateUrl : 'admin-base64.html',
			controller : AdminBase64Controller
			
		}).when('/admin', {
			redirectTo : '/admin/layouter'
				
		}).when('/add-info', {
			templateUrl : 'add-info.html',
			controller : AddInfoController		
		}).when('/select-info', {
			templateUrl : 'select-info.html',
			controller : SelectInfoController
		}).when('/cloud', {
			templateUrl : 'cloud.html',
			controller : CloudController		
		}).when('/select-images', {
			templateUrl : 'select-images.html',
			controller : SelectImagesController		
		
		}).otherwise({
			redirectTo : '/person'
		});
	}]
);


