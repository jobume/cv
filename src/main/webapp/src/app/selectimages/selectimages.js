'use strict';

var uploader = angular.module(
		'selectimages', [ 'ngRoute','services.navigation', 
		                  'resources.cvresource', 'resources.portraitresource', 'resources.coverimageresource' ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/bilder', {
	    templateUrl:'src/app/selectimages/selectimages.tpl.html',
	    controller:'SelectImagesController'
	    }
	  );
	}])
	
.controller('SelectImagesController', 
		['$scope', 'CvResource', 'Navigation', 'PortraitResource', 'CoverImageResource',  
		 function($scope, CvResource, Navigation, PortraitResource, CoverImageResource) {
	 
	$scope.$on('LOAD',function(){$scope.loading=true});
	$scope.$on('UNLOAD',function(){$scope.loading=false});
	$scope.$emit('LOAD');
	
	$scope.loadingportrait = false;
	
	$scope.model = CvResource.get();	
	
	$scope.imageSelected = function() {
		Navigation.getState().disabled = false;
	};
	
	if ($scope.model.cv.coverImage) {
		Navigation.getState().disabled = false;
	}	
	
	// Load portrait images into scope
	CoverImageResource.get(function(data) {
		$scope.coverimages = data;
		$scope.$emit('UNLOAD');
	});
	
	/**
	 * This method is called on onchange() and we must therefore call $scope.$apply()
	 * 
	 * @param files file list
	 */
	$scope.uploadFile = function(files) {
		Navigation.getState().disabled = true;
		$scope.loadingportrait = true;
		PortraitResource.create(files, function (data) {
			$scope.model.cv.profile.portrait = data;
			Navigation.getState().disabled = false;
			$scope.loadingportrait = false;
		});
		$scope.$apply();
	}


} ]);