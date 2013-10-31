function AddInfoController($rootScope, $scope, $location, $http, $window) {
	// Set bread crumb text
	$rootScope.breadcrumb = 
		"<a href='#/person'>Person</a> > " +
		"Anpassning > " +
		"<b>Egenskaper</b> > " +
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
		$location.path('select-info');
	};

	
	$rootScope.next = function () {
		$rootScope.nextButtonDisabled = true;
		var putUrl = 'api/rtfparser';			
		
		$http.put(putUrl, $rootScope.cv).
		  success(function(data, status, headers, config) {
			  $rootScope.cv = data;
		  }).
		  error(function(data, status, headers, config) {
		    alert("Ett fel uppstod när egenskaperna lades till. " + data);
		});
		$location.path('cloud');		
	};
	
	window.scope = $scope;
	
	$scope.addQuality = function() {
		qualityName = $scope.qualityName;
		$rootScope.cv.personalQualities.push(qualityName);
	}
	
	$scope.deleteQuality = function(index) {
		$rootScope.cv.personalQualities.splice(index, 1);
	}
	
}