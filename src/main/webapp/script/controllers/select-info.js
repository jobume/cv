function SelectInfoController($rootScope, $scope, $location, $http, $window) {
	
	// Set bread crumb text
	$rootScope.breadcrumb = 
		"<a href='#/person'>Person</a> > " +
		"<b>Anpassning</b> > " +
		"Egenskaper > " +
		"Ordmoln > " +
		"Bilder > " +
		"Layout";
	
	// Set wizard navigation button properties
	$rootScope.previousButtonLabel = "<< Tillbaka";
	$rootScope.previousButtonDisabled = false;
	$rootScope.hidePrevious = false;
	$rootScope.nextButtonLabel = "Nästa >>";
	$rootScope.nextButtonDisabled = false;
	$rootScope.hideNext = false;

	$rootScope.previous = function () {		
		$location.path('person');
	};

	$rootScope.next = function () {
		$rootScope.nextButtonDisabled = true;
		$location.path('add-info');		
	};
	
	$scope.columnCheckBox = false;
	
	// Add the 'important' attribute to engagements and set the first 5 as important
	for (var i = 0; i < $rootScope.cv.engagements.length; i++) {
		if (i < 5) {
			$rootScope.cv.engagements[i].important = true;
		} else {
			$rootScope.cv.engagements[i].important = false;
		}
	}
	
	// Count selected engagements
	$scope.selectedEngagements = 0;
	$scope.countSelectedEngagements = function () {
		$scope.selectedEngagements = 0;
		for (var i = 0; i < $rootScope.cv.engagements.length; i++) {
			if ($rootScope.cv.engagements[i].important) {
				$scope.selectedEngagements++;
			}
		}
	};
	$scope.countSelectedEngagements();
	
	$scope.toggleRowCheckboxes = function () {
		for (var i = 0; i < $rootScope.cv.engagements.length; i++) {
			$rootScope.cv.engagements[i].important = $scope.columnCheckBox;
		}
		$scope.countSelectedEngagements();
	};

	$scope.toggleColumnCheckbox = function (rowCheckBoxSelected) {
		// Uncheck column checkbox if row checkbox is unchecked
		if (!rowCheckBoxSelected) {
			$scope.columnCheckBox = false;
		}
		
		$scope.countSelectedEngagements();
	};
	
	$scope.toggleCheckbox = function (rowIndex) {
		$rootScope.cv.engagements[rowIndex].important = !$rootScope.cv.engagements[rowIndex].important;
		$scope.toggleColumnCheckbox($rootScope.cv.engagements[rowIndex].important);
	};
	
	$scope.allChecked = false;
	
	$scope.checkAll = function () {
		for(var i=0; i<$rootScope.cv.technologies.length; i++) {
			$rootScope.cv.technologies[i].important = $scope.allChecked;
		}
	}
	
}