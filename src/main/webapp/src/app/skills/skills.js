'use strict';

var MAX_IMPORTANT_TECHS = 5;

angular.module('skills', [ 'ngRoute', 'resources.cvresource', 'services.navigation' ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/teknik', {
	    templateUrl:'src/app/skills/skills.tpl.html',
	    controller:'SkillsController'
	    }
	  );
	}])
	
.controller('SkillsController', ['$scope', 'CvResource', 'SelectionService','Navigation', 
                                      function($scope, CvResource, SelectionService, Navigation) {
	
	$scope.model = CvResource.get(); 
	
	$scope.selectedTechs = 0;
	$scope.allTechsSelected = false;
	$scope.disableTechs = false;
	
	Navigation.onNext(function (success, state) {
		if($scope.adjustmentForm.$valid) {
			success();
		} else {
			state.disabled = false;
			alert("Det finns fel i formuläret!")
		}
	});
	
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
		if($scope.selectedTechs == MAX_IMPORTANT_TECHS) {
			$scope.disableTechs = true;
		} else {
			$scope.disableTechs = false;
		}
	};
	
	$scope.getSelectedTechs = function () {
		return $scope.selectedTechs;
	};
	
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