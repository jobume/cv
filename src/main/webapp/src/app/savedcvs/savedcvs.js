'use strict';

angular.module('savedcvs', [ 'ngRoute', 'resources.cvresource'])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/admin/savedcvs', {
		  templateUrl:'src/app/savedcvs/savedcvs.tpl.html',
		  controller:'CvController'
	    }).when('/savedcvs', {
	    	redirectTo : '/admin/savedcvs'
	    });
	}])

.controller('CvController', ['$scope', '$location', '$routeParams', 'CvResource','Navigation',
                                      function($scope, $location, $routeParams, CvResource, Navigation) {
	
	$scope.model = CvResource.get();	
		
	CvResource.list(function(data) {		
		$scope.cvlist = data;
	});
	
	$scope.editCv = function(id) {
		CvResource.getCv(id, function() {
			$scope.model = CvResource.get();
			$location.path("/anpassa");
		});
	}
	
	$scope.createPdf = function(id) {
		CvResource.getCv(id, function() {
			$scope.model = CvResource.get();
			$location.path("/layout");
		});
	}
	
	$scope.deleteCv = function(id) {
		CvResource.deleteCv(id, function() {			
			$location.path("/savedcvs");
		});
	}
			
}]);