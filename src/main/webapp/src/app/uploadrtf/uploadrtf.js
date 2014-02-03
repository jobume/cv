'use strict';

var uploader = angular.module('uploadrtf', [ 'ngRoute', 'resources.cvresource','services.navigation' ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/laddaupp', {
	    templateUrl:'src/app/uploadrtf/uploadrtf.tpl.html',
	    controller:'UploadRtfController'
	    }
	  );
	}])
	
.controller('UploadRtfController', ['$scope', 'CvResource', 'Navigation', function($scope, CvResource, Navigation) {
	
	$scope.model = CvResource.get();	
	
	// Set callback function on next button
	Navigation.onNext(function(success) {
		CvResource.create($scope.files, function () {
			success();
			$scope.model.cv.name = $scope.model.cv.profile.name;
			$scope.model.cv.office = "Umeå";
		});		
	});
	
	/**
	 * This method is called on onchange() and we must therefore call $scope.$apply()
	 * 
	 * @param files file list
	 */
	$scope.prepare = function(files) {
		$scope.files = files;
		Navigation.getState().disabled = false;
		$scope.$apply();
	}


} ]);