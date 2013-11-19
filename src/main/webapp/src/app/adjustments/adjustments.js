'use strict';

angular.module('adjustments', [ 'ngRoute', 'resources.cvresource', 'services.navigation' ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/anpassa', {
	    templateUrl:'src/app/adjustments/adjustments.tpl.html',
	    controller:'AdjustmentsController'
	    }
	  );
	}])
	
.controller('AdjustmentsController', ['$scope', 'CvResource', 'SelectionService', 
                                      function($scope, CvResource, SelectionService) {
	
	$scope.model = CvResource.get();
	
	$scope.selectedEngagements = 0;
	$scope.allEngagementsSelected = false;
	$scope.selectedTechs = 0;
	$scope.allTechsSelected = false;
	
	$scope.selectEngagements = function () {
		$scope.selectedEngagements = 
			SelectionService.selectAll($scope.model.cv.engagements, $scope.allEngagementsSelected);
	};
	
	$scope.selectEngagement = function (engagement) {
		engagement.important = !engagement.important;
		$scope.selectedEngagements =
			SelectionService.countSelected($scope.model.cv.engagements);
	};
	
	$scope.countSelectedEngagements = function() {
		$scope.selectedEngagements = 
			SelectionService.countSelected($scope.model.cv.engagements);
	}
	
	$scope.selectTechs = function () {
		$scope.selectedTechs = 
			SelectionService.selectAll($scope.model.cv.technologies, $scope.allTechsSelected);
	};
	
	$scope.selectTech = function (tech) {
		tech.important = !tech.important;
		$scope.selectedTechs = 
			SelectionService.countSelected($scope.model.cv.technologies);
	};
	
	$scope.countSelectedTechs = function () {
		$scope.selectedTechs = 
			SelectionService.countSelected($scope.model.cv.technologies);
	};
	
	$scope.getSelectedEngagements = function () {
		return $scope.selectedEngagements;
	};
	
	$scope.getSelectedTechs = function () {
		return $scope.selectedTechs;
	};
	
	$scope.countSelectedEngagements();
	$scope.countSelectedTechs();
	
} ])

.factory('SelectionService', function() {
	
	var serviceInstance = {
		countSelected : function(elems) {
			var selectedCount = 0;
			for (var i = 0; i < elems.length; i++) {
				if (elems[i].important) {
					selectedCount++;
				}
			}
			return selectedCount;
		},
		selectAll : function(elems, checked) {
			for(var i=0; i<elems.length; i++) {
				elems[i].important = checked;
			}
			if (checked ) {
				return elems.length;
			} else {
				return 0;
			}
		}		
	};
	return serviceInstance;
});