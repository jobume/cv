'use strict';

var MAX_TAGS = 8;

angular.module('wordcloud', [ 'ngRoute', 'resources.cvresource', 'services.navigation' ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/ordmoln', {
	    templateUrl:'src/app/wordcloud/wordcloud.tpl.html',
	    controller:'WordCloudController'
	    }
	  );
	}])
	
.controller('WordCloudController', ['$scope', 'CvResource', 'Navigation', 
                                      function($scope, CvResource, Navigation) {
	
	$scope.model = CvResource.get();
	
	$scope.generateCloud = function() {
		CvResource.generateCloud(function () {
			console.log("New cloud generated...");
		});
	}
	
	Navigation.onNext(function (success, state) {
		CvResource.update(function () {
			success();
		}, function () {
			state.waitingfornext = false;
			state.disabled = false;
		});					
	});
	
	$scope.addTag = function() {
		var tagName = $scope.tagName;
		if(tagName.length > 0) {
			var noDuplicates = true;
			for(var i=0; i< $scope.model.cv.tags.length; i++) {
				if( $scope.model.cv.tags[i].tagName == tagName) {
					noDuplicates = false;
					break;
				}
			}
			
			if(noDuplicates) {
				if($scope.model.cv.tags.length < MAX_TAGS) {
					$scope.model.cv.tags.push({tagName: tagName});
				} else {
					alert("Du kan maximalt lägga till " + MAX_TAGS + " ord i ordmolnet.")
				}			
			}
			
			$scope.tagName = "";
		}
	};
	
	$scope.deleteTag = function(index) {
		$scope.model.cv.tags.splice(index, 1);
	}
	
} ]);