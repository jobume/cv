function CloudController($rootScope, $scope, $location, $http, $window) {
	// Set bread crumb text
	$rootScope.breadcrumb = 
		"<a href='#/person'>Person</a> > " +
		"Anpassning > " +
		"Egenskaper > " +
		"<b>Ordmoln</b> > " +
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
		$location.path('add-info');
	};

	$rootScope.next = function () {
		$rootScope.nextButtonDisabled = true;
		$location.path('select-images');		
	};
	
	window.scope = $scope;
	
	/*
	$scope.tagFonts = ['myriad','minion','trebuchet'];
	$scope.tagFont = $scope.tagFonts[0];
	$scope.tagSizes = ['SMALL','MEDIUM','LARGE'];
	$scope.tagSize = $scope.tagSizes[0];
	*/
	
	$scope.addTag = function() {
		tagName = $scope.tagName;
		/*
		tagBold = $scope.tagBold;
		tagUpperCase = $scope.tagUpperCase;
		tagItalic = $scope.tagItalic;
		tagSize = $scope.tagSize;
		tagFont= $scope.tagFont;
		*/
		// $rootScope.cv.tags.push({tagName: tagName, bold: tagBold, upperCase: tagUpperCase, italic: tagItalic, size: tagSize, font: tagFont});
		$rootScope.cv.tags.push({tagName: tagName});
	};
	
	$scope.deleteTag = function(index) {
		$rootScope.cv.tags.splice(index, 1);
	}

}