function AdjustmentController($rootScope, $scope, $location, $http) {
	// Set bread crumb text
	$rootScope.breadcrumb = "<a href='#/person'>Person</a> > <b>Anpassning</b> > <span style='color:gray'>Layout</span>";
	
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
		if($rootScope.cv.coverImage) {
			$rootScope.nextButtonDisabled = true;
			$location.path('layout');
		} else {
			alert('Ange en siluettbild!');
		}
	};
	
	$http({method: 'GET', url: '/api/coverimage'}).
	  success(function(data, status, headers, config) {		
	    $scope.coverimages = data;	    
	  }).
	  error(function(data, status, headers, config) {
	    alert("Kunde inte ladda bilder. Felmeddelande från server: " + data);
	  });
	
	window.scope = $scope;
	
	$scope.tagFonts = ['myriad','minion','trebuchet'];
	$scope.tagFont = $scope.tagFonts[0];
	$scope.tagSizes = ['SMALL','MEDIUM','LARGE'];
	$scope.tagSize = $scope.tagSizes[0];
	
	$scope.addTag = function() {
		tagName = $scope.tagName;
		tagBold = $scope.tagBold;
		tagUpperCase = $scope.tagUpperCase;
		tagItalic = $scope.tagItalic;
		tagSize = $scope.tagSize;
		tagFont= $scope.tagFont;
		
		$rootScope.cv.tags.push({tagName: tagName, bold: tagBold, upperCase: tagUpperCase, italic: tagItalic, size: tagSize, font: tagFont});
	};
	
	$scope.deleteTag = function(index) {
		$rootScope.cv.tags.splice(index, 1);
	}
	
	$scope.addQuality = function() {
		qualityName = $scope.qualityName;
		$rootScope.cv.personalQualities.push(qualityName);
	}
	
	$scope.deleteQuality = function(index) {
		$rootScope.cv.personalQualities.splice(index, 1);
	}
	
	
	$scope.uploadFile = function(files) {
		$rootScope.nextButtonDisabled = true;
		var fd = new FormData();
	    //Take the first selected file
	    fd.append("file", files[0]);
	    
	    $http.post("/api/portrait", fd, {
	        withCredentials: true,
	        headers: {'Content-Type': undefined },
	        transformRequest: angular.identity
	    }).success(function(data) { 	    	
	    	$rootScope.cv.profile.portraitUrl = data;
	    	$rootScope.nextButtonDisabled = false;
	    }).error(function() { alert("Det gick inte att ladda upp bilden!") } );
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
}