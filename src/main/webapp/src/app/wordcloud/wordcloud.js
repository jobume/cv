'use strict';

angular.module('wordcloud', [ 'ngRoute', 'resources.cvresource', 'services.navigation' ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/ordmoln', {
	    templateUrl:'src/app/wordcloud/wordcloud.tpl.html',
	    controller:'WordCloudController'
	    }
	  );
	}])
	
.controller('WordCloudController', ['$scope', 'CvResource', 
                                      function($scope, CvResource) {
	
	$scope.model = CvResource.get();
	
	$scope.addTag = function() {
		var tagName = $scope.tagName;
		
		var noDuplicates = true;
		for(var i=0; i< $scope.model.cv.tags.length; i++) {
			if( $scope.model.cv.tags[i].tagName == tagName) {
				noDuplicates = false;
				break;
			}
		}
		
		if(noDuplicates) {
			$scope.model.cv.tags.push({tagName: tagName});
		}
		
		$scope.tagName = "";
	};
	
	$scope.deleteTag = function(index) {
		$scope.model.cv.tags.splice(index, 1);
	}
	
} ]);