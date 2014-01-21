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
	
	$scope.model = CvResource.get();
	
	$scope.layoutstate = { };
	
	$scope.layoutSelected = function() {
		Navigation.getState().disabled = false;
	};
	
	$scope.save = function() {		
		var newName = prompt("Ange ett namn för CV:t.", "");
		$scope.model.cv.name = newName;
		CvResource.save(function () {
			alert("CV:t sparades!");
		});					
	};
	
	$scope.update = function() {
		CvResource.update(function () {
			alert("CV:t uppdateras!");
		});
	}
	
	LayoutsResource.get(function(data) {
		$scope.layouts = data;
		$scope.$emit('UNLOAD');
	});
	
	Navigation.onNext(function(success) {
		if($scope.layoutstate.selectedLayoutId) {
			console.log("Selected layout: " + $scope.layoutstate.selectedLayoutId);
			PdfResource.create($scope.model.cv, $scope.layoutstate.selectedLayoutId, function(getUrl) {
				$window.open(getUrl) ;
			});
		} else {
			alert('Välj en layout!');
		}	
	});
		
} ]);