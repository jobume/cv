function LayoutController($rootScope, $scope, $location, $http, $window) {
	// Set bread crumb text
	$rootScope.breadcrumb = 
		"<a href='#/person'>Person</a> > " +
		"Anpassning > " +
		"Egenskaper > " +
		"Ordmoln > " +
		"Bilder > " +
		"<b>Layout</b>";
	
	// Set wizard navigation button properties
	$rootScope.previousButtonLabel = "<< Tillbaka";
	$rootScope.previousButtonDisabled = false;
	$rootScope.hidePrevious = false;
	$rootScope.nextButtonLabel = "Generera PDF";
	$rootScope.nextButtonDisabled = false;
	$rootScope.hideNext = false;
	
	// Get layouts
	$scope.loaderIndicator = "Läser layouter...";
	$http.get("api/layouts").success(function (data) {
		$scope.layouts = data;
		$scope.loaderIndicator = "";
	});
	
	$rootScope.previous = function () {
		// 2013-10-17: Added more wizard steps $location.path('anpassning');
		$location.path('select-images');
	};
	
	$rootScope.next = function () {
		
		if ($scope.selectedLayoutHref) {
			$rootScope.nextButtonDisabled = true;
			var postUrl = 'api/pdf/' + $scope.selectedLayoutHref ;			
			
			$http.post(postUrl, $rootScope.cv).
			  success(function(data, status, headers, config) {
			    var getUrl = 'api/pdf/' + data ;
				$window.open(getUrl) ;
				$rootScope.nextButtonDisabled = false;
			  }).
			  error(function(data, status, headers, config) {
				$rootScope.nextButtonDisabled = false;  
			    alert("Ett fel uppstod vid konvertering. Felmeddelande från server: " + data);
			});
		} else {
			alert("Ange en layout!");
		}
	};
	
	// TODO check first layout by default
	
//	var firstIsChecked = false;
//	$scope.checkFirst = function () {
//		if (!firstIsChecked) {
//			firstIsChecked = true;
//			return true;
//		} else {
//			return false;
//		}
//	};
//	$scope.checkFirst = true;
	
}
