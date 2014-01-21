'use strict';

angular.module('admin', [ 'ngRoute', 'services.navigation', 'resources.layoutsresource', 'resources.base64resource' ])


.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/admin', {
		  	redirectTo : '/admin/layouter'
	    }).when('/admin/layouter', {
	    	templateUrl : 'src/app/admin/layouts.tpl.html',
			controller : 'LayoutsController'
	    }).when('/admin/ny', {
	    	templateUrl : 'src/app/admin/newlayout.tpl.html',
			controller : 'NewLayoutController'
	    }).when('/admin/layouter/:id', {
	    	templateUrl : 'src/app/admin/editlayout.tpl.html',
			controller : 'EditLayoutController'
	    }).when('/admin/base64', {
	    	templateUrl : 'src/app/admin/base64.tpl.html',
			controller : 'Base64Controller'
	    }).when('/admin/coverimage', {
	    	templateUrl : 'src/app/admin/coverimage.tpl.html',
			controller : 'CoverImageController'
	    });
	}])

.controller('LayoutsController', ['$scope', '$location', 'LayoutsResource', 'Navigation',
                                      function($scope, $location, LayoutsResource, Navigation) {
	
	LayoutsResource.get(function(data) {
		$scope.layouts = data;
	});
	
	$scope.createLayout = function(id) {
		$location.path('/admin/ny');
	};
	
	$scope.deleteLayout = function(id) {
		var answer = prompt("Bekräfta radering av layout.\n" +
				"Skriv \"RADERA\" (med små eller stora bokstäver):", "");
		if (answer.toLowerCase() === "radera") {
			LayoutsResource.deleteLayout(id, function() {				
				$location.path("/admin");
			});			
		}
	};
	
	$scope.editLayout = function(id) {
		$location.path("/admin/layouter/" + id);
	};	
}])

.controller('NewLayoutController', ['$scope', '$location', 'LayoutsResource','Navigation',
                                      function($scope, $location, LayoutsResource, Navigation) {
	
	// Disable createLayout-button
	$scope.ready = false;
	
	$scope.prepare = function(files) {
		$scope.files = files;
		$scope.ready = true;
		$scope.$apply();
	};
	
	$scope.createLayout = function() {		
		LayoutsResource.create($scope.files, $scope.layoutName, function(data) {			
			$location.path('/admin');
		});
	};
		
}])

.controller('EditLayoutController', ['$scope', '$location', '$routeParams', 'LayoutsResource','Navigation',
                                      function($scope, $location, $routeParams, LayoutsResource, Navigation) {
	
	var id = $routeParams.id;
	
	LayoutsResource.getById(id, function(data) {
		$scope.layout = data;
	});
	
	$scope.update = function() {
		var layout = JSON.stringify($scope.layout);
		LayoutsResource.update(id, layout, function() {
			$location.path('/admin');
		});
	};
		
}])

.controller('Base64Controller', ['$scope', '$location', 'Base64Resource',
                                      function($scope, $location, Base64Resource) {
	
	$scope.prepare = function(files) {
		$scope.files = files;
		$scope.ready = true;
		$scope.$apply();
	};
	
	$scope.base64Encode = function() {		
		Base64Resource.create($scope.files, function(data) {			
			$scope.base64 = data;
		});
	};
	
		
}])

.controller('CoverImageController', ['$scope', '$location', 'CoverImageResource',
                                      function($scope, $location, CoverImageResource) {
	
	// Load portrait images into scope
	CoverImageResource.get(function(data) {
		$scope.coverimages = data;
	});
	
	$scope.deleteCoverImage = function(id){
		CoverImageResource.deleteCoverImage(id, function() {
			CoverImageResource.get(function(data) {
				$scope.coverimages = data;
			});
		});
	}
	
	$scope.prepare = function(files) {
		$scope.files = files;		
		$scope.$apply();
	};
	
	$scope.upload = function() {			
		CoverImageResource.create($scope.files, function() {						
			CoverImageResource.get(function(data) {
				$scope.coverimages = data;
			});
		});
	};
	
		
}]);