'use strict';

var app = angular.module('cv', ['ngRoute','ui', 
                                        'uploadrtf', 'adjustments', 'engagements', 'skills', 'qualities', 
                                        'wordcloud', 'selectimages', 'layout', 'admin', 'savedcvs',
                                        'services.navigation', 'resources.cvresource'])

.controller('AppController', ['$rootScope', '$scope', '$location', 'Navigation', 'CvResource', 
                              function($rootScope, $scope, $location, Navigation, CvResource) {
		
	$scope.navigation = Navigation.getState();
	
	$scope.model = CvResource.get();
	
	$scope.start = function() {
		CvResource.purge();
		$location.path("/start");
	}
	
	$scope.next = function() {
		Navigation.next();
	}
		
	$scope.previous = function() {
		Navigation.previous();
	}
	
	$scope.back = function () {
		var previousPath = Navigation.getState().steps[Navigation.getState().selectedIndex].path;		
		$location.path(previousPath);
	};

} ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.otherwise({
			redirectTo : '/laddaupp'
	  });
}]);
