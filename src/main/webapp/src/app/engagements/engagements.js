'use strict';

var MAX_JOB_DESC_LENGTH = 200;

var MAX_IMPORTANT_ENGAGEMENTS = 5;

angular.module('engagements', [ 'ngRoute', 'resources.cvresource', 'services.navigation' ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/uppdrag', {
	    templateUrl:'src/app/engagements/engagements.tpl.html',
	    controller:'EngagementsController'
	    }
	  );
	}])
	
.controller('EngagementsController', ['$scope', 'CvResource', 'SelectionService','Navigation', 
                                      function($scope, CvResource, SelectionService, Navigation) {
	
	$scope.model = CvResource.get(); 
	
	$scope.selectedEngagements = 0;
	$scope.allEngagementsSelected = false;
	$scope.disableEngagements = false;
	
	Navigation.onNext(function (success, state) {
		if($scope.adjustmentForm.$valid) {
			success();
		} else {
			state.disabled = false;
			alert("Det finns fel i formuläret!")
		}
	});
	
	$scope.selectEngagements = function () {
		$scope.selectedEngagements = 
			SelectionService.selectAll($scope.model.cv.engagements, $scope.allEngagementsSelected);
	};
	
	$scope.selectEngagement = function (engagement) {
		$scope.selectedEngagements =
			SelectionService.countSelected($scope.model.cv.engagements);
	};
	
	$scope.countSelectedEngagements = function() {
		$scope.selectedEngagements = 
			SelectionService.countSelected($scope.model.cv.engagements);
		if($scope.selectedEngagements == MAX_IMPORTANT_ENGAGEMENTS) {
			$scope.disableEngagements = true;
		} else {
			$scope.disableEngagements = false;
		}
	}
	
	
	$scope.getSelectedEngagements = function () {
		return $scope.selectedEngagements;
	};
	
	
	$scope.countSelectedEngagements();
	
	
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
})

.directive('jobdescription', function() {
	  return {
	    require: 'ngModel',
	    link: function(scope, elm, attrs, ctrl) {
	      var jobvalidator = function(viewValue) {
		    	var valOfImportant =  scope.engagementForm.important.$viewValue;
		    	var engagement = scope.engagementForm.engagement;
		    	var description = engagement.$viewValue;
		    	if (valOfImportant && description.length > MAX_JOB_DESC_LENGTH) {
		    	  engagement.$setValidity('jobdescription', false);
				  return viewValue;
		        } else {
		          engagement.$setValidity('jobdescription', true);
		          return viewValue;
		        }
	      };
	      scope.$watch(attrs.ngModel, jobvalidator)
	    }
	  };
});