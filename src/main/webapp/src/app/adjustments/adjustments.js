'use strict';

angular.module('adjustments', [ 'ngRoute', 'resources.cvresource', 'services.navigation' ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/anpassa', {
	    templateUrl:'src/app/adjustments/adjustments.tpl.html',
	    controller:'AdjustmentsController'
	    }
	  );
	}])
	
.controller('AdjustmentsController', ['$scope', 'CvResource', 'SelectionService','Navigation', 
                                      function($scope, CvResource, SelectionService, Navigation) {
	
	$scope.model = CvResource.get();
	
	$scope.selectedEngagements = 0;
	$scope.allEngagementsSelected = false;
	$scope.selectedTechs = 0;
	$scope.allTechsSelected = false;
	var MAX_DESC_LENGTH = 570;
	
	Navigation.onNext(function (success, state) {
		var valid = false;
		if($scope.model.cv.profile.title && $scope.model.cv.profile.title.length > 0) {
			valid = true;
		} else {
			valid = false;
			alert('Titel är obligatoriskt!');
		}
		if(valid && $scope.model.cv.description && $scope.model.cv.description.length <= MAX_DESC_LENGTH) {
			valid = true;
		} else {
			valid = false;
			alert('Maxlängd för Beskrivning är ' + MAX_DESC_LENGTH);
		}
		if(valid) {
			success();
		} else {
			state.disabled = false;
		}
	});
	
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