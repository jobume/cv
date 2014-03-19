'use strict';

var MAX_DESC_LENGTH = 570;

angular.module('adjustments', [ 'ngRoute', 'resources.cvresource', 'services.navigation' ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/anpassa', {
	    templateUrl:'src/app/adjustments/adjustments.tpl.html',
	    controller:'AdjustmentsController'
	    }
	  );
	}])
	
.controller('AdjustmentsController', ['$scope', 'CvResource','Navigation', 
                                      function($scope, CvResource, Navigation) {
	
	$scope.model = CvResource.get();
	
	Navigation.onNext(function (success, state) {
		if($scope.adjustmentForm.$valid) {
			CvResource.update(function () {
				success();
			}, function () {
				state.disabled = false;
				state.waitingfornext = false;
			});			
		} else {
			state.disabled = false;
			state.waitingfornext = false;
			alert("Det finns fel i formuläret!")
		}
	});
	
} ])

.directive('laxlength', function() {
	  return {
	    require: 'ngModel',
	    link: function(scope, elm, attrs, ctrl) {
	      var validator = function(viewValue) {
	    	if (viewValue && viewValue.length > MAX_DESC_LENGTH) {
	    	  ctrl.$setValidity('laxlength', false);
			  return viewValue;
		    } else {
	          ctrl.$setValidity('laxlength', true);
	          return viewValue;
		    }
		  };
		  ctrl.$parsers.unshift(validator);
		  ctrl.$formatters.unshift(validator);
	    }
	  };
});