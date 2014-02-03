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
	
	$scope.loading = true;
	
	$scope.model = CvResource.get();	
	
	CvResource.list(function(data) {		
		$scope.cvlist = data;
		$scope.loading = false;
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
			$location.path("/bilder");
		});
	}
	
	$scope.editAsNew = function(id) {
		CvResource.getCv(id, function() {
			$scope.model = CvResource.get();
			
			var newName = prompt("Ange ett namn för CV:t.", "");
			if(newName != '' && newName != null) {
				$scope.model.cv.name = newName;
				$scope.model.cv.id = -1;			
				CvResource.save(function () { });
				$location.path("/anpassa");
			}
		});
	}
	
	$scope.deleteCv = function(id) {
		CvResource.deleteCv(id, function() {			
			$location.path("/savedcvs");
		});
	}
			
}]);