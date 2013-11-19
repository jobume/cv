'use strict';

angular.module('qualities', [ 'ngRoute', 'resources.cvresource', 'services.navigation' ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/egenskaper', {
	    templateUrl:'src/app/qualities/qualities.tpl.html',
	    controller:'QualitiesController'
	    }
	  );
	}])
	
.controller('QualitiesController', ['$scope', 'CvResource', 'Navigation', 
                                      function($scope, CvResource, Navigation) {
	
	$scope.model = CvResource.get();
	
	Navigation.onNext(function(success) {
		CvResource.update(success);
	});
	
	$scope.addQuality = function() {
		var qualityName = $scope.qualityName;
		var noDuplicates = true;
		for(var i=0; i< $scope.model.cv.personalQualities.length; i++) {
			if( $scope.model.cv.personalQualities[i] == qualityName) {
				noDuplicates = false;
				break;
			}
		}
		if(noDuplicates) {
			$scope.model.cv.personalQualities.push(qualityName);
		}
	}
	
	$scope.deleteQuality = function(index) {
		$scope.model.cv.personalQualities.splice(index, 1);
	}
	
} ]);