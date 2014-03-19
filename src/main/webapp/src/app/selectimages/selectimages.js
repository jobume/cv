'use strict';

var defaultLayout = -1;

var uploader = angular.module(
		'selectimages', [ 'ngRoute','services.navigation', 
		                  'resources.cvresource', 'resources.portraitresource', 
		                  'resources.coverimageresource', 'resources.pdfresource' ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/bilder', {
	    templateUrl:'src/app/selectimages/selectimages.tpl.html',
	    controller:'SelectImagesController'
	    }
	  );
	}])
	
.controller('SelectImagesController', 
		['$scope', '$window', 'CvResource', 'Navigation', 'PortraitResource', 'CoverImageResource',  'PdfResource',
		 function($scope, $window, CvResource, Navigation, PortraitResource, CoverImageResource, PdfResource) {
	
	// Indicates cover images are loading
	$scope.loading=true;	
	
	// Indicates portrait is being loaded
	$scope.loadingportrait = false;
	
	// Indicates PDF is being generated
	$scope.generating = false;
	
	// Indicates CV is being saved to database
	$scope.saving = false;
	
	$scope.model = CvResource.get();	
	
	checkState();
	
	$scope.imageSelected = function() {
		checkState();		
	};
	
	// CoverImage and Portrait must be set, and must have a url
	function checkState() {		
		if ($scope.model.cv.coverImage && 
			$scope.model.cv.coverImage.id && 
			$scope.model.cv.profile.portrait && 
			$scope.model.cv.profile.portrait.url) {
			Navigation.getState().disabled = false			
		} else {
			Navigation.getState().disabled = true;
		}
	}
	
	// Load portrait images into scope	
	CoverImageResource.get(function(data) {
		$scope.coverimages = data;
		$scope.loading = false;
	});
	
	/**
	 * This method is called on onchange() and we must therefore call $scope.$apply()
	 * 
	 * @param files file list
	 */
	$scope.uploadPortrait = function(files) {
		Navigation.getState().disabled = true;
		$scope.loadingportrait = true;
		if($scope.model.cv.id > 0) {
			createPortrait(files);
		} else {
			alert("Error. No CV id was set!");
		}/*else {
			CvResource.save( function () {
				createPortrait(files);
			});
		}*/
		
		$scope.$apply();
		
	}
	
	var createPortrait = function (files) {
		console.log("Saving portrait with scope id: " + $scope.model.cv.id);
		PortraitResource.create(files, $scope.model.cv.id, function (data) {
			$scope.model.cv.profile.portrait = data;
			checkState();
			$scope.loadingportrait = false;
		});
	} ;
	
	var createPdf = function () {
		$scope.saving = false;
		PdfResource.create($scope.model.cv, defaultLayout, function(getUrl) {
			$scope.generating = false;
			$window.open(getUrl) ;
			Navigation.getState().disabled = false;
			Navigation.getState().waitingfornext = false;
		}, function () {
			$scope.generating = false;
			Navigation.getState().disabled = false;
			Navigation.getState().waitingfornext = false;
		});
	} ;
	
	Navigation.onNext(function(success, state) {	
				
		if($scope.model.cv.id > 0) {
			$scope.saving = true;
			CvResource.update(createPdf, function () {
				state.disabled = false;
				state.waitingfornext = false;
				$scope.generating = false;
				$scope.saving = false;
			});
		} else {
			state.waitingfornext = false;
			alert("Error! No CV id set.");
		} /*else {
			$scope.saving = true;			
			CvResource.save(createPdf);
		}*/
		
		$scope.generating = true;
		
					
	});
	
	


} ]);