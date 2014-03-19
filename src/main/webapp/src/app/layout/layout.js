'use strict';

angular.module('layout', [ 'ngRoute', 'services.navigation', 
                              'resources.cvresource','resources.layoutsresource','resources.pdfresource' ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/layout', {
	    templateUrl:'src/app/layout/layout.tpl.html',
	    controller:'LayoutController'
	    }
	  );
	}])
	
.controller('LayoutController', ['$scope', '$window', 'CvResource', 'Navigation','LayoutsResource','PdfResource',
                                      function($scope, $window, CvResource, Navigation, LayoutsResource, PdfResource) {
	
	$scope.$on('LOAD',function(){$scope.loading=true});
	$scope.$on('UNLOAD',function(){$scope.loading=false});
	$scope.$emit('LOAD');
	$scope.generating = false;
	$scope.saving = false;
	
	$scope.model = CvResource.get();
	
	$scope.layoutstate = { };
	
	$scope.layoutSelected = function() {
		Navigation.getState().disabled = false;
	};
	
	$scope.save = function() {		
		var newName = prompt("Ange ett namn för CV:t.", "");
		$scope.model.cv.name = newName;
		$scope.saving = true;
		CvResource.save(function () {
			alert("CV:t sparades!");
			$scope.saving = false;
		});					
	};
	
	$scope.update = function() {
		$scope.saving = true;
		CvResource.update(function () {
			alert("CV:t uppdateras!");
			$scope.saving = false;
		});
	}
	
	LayoutsResource.get(function(data) {
		$scope.layouts = data;
		$scope.$emit('UNLOAD');
	});
	
	Navigation.onNext(function(success) {
		if($scope.layoutstate.selectedLayoutId) {
			console.log("Selected layout: " + $scope.layoutstate.selectedLayoutId);
			$scope.generating = true;
			PdfResource.create($scope.model.cv, $scope.layoutstate.selectedLayoutId, function(getUrl) {
				$scope.generating = false;
				$window.open(getUrl) ;
				Navigation.getState().disabled = false;
			});
		} else {			
			alert('Välj en layout!');
		}	
	});
		
} ]);