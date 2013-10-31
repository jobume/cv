function SelectImagesController($rootScope, $scope, $location, $http, $window) {
	// Set bread crumb text
	$rootScope.breadcrumb = 
		"<a href='#/person'>Person</a> > " +
		"Anpassning > " +
		"Egenskaper > " +
		"Ordmoln > " +
		"<b>Bilder</b> > " +
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
		if($rootScope.cv.coverImage) {
			$rootScope.nextButtonDisabled = true;
			$location.path('layout');
		} else {
			alert('Ange en siluettbild!');
		}
	};
	
	$http({method: 'GET', url: 'api/coverimage'}).
	  success(function(data, status, headers, config) {		
	    $scope.coverimages = data;	    
	  }).
	  error(function(data, status, headers, config) {
	    alert("Kunde inte ladda bilder. Felmeddelande från server: " + data);
	  });
	
	window.scope = $scope;
	
	$scope.uploadFile = function(files) {		
		var fd = new FormData();
	    //Take the first selected file
	    fd.append("file", files[0]);
	    
	    $http.post("api/portrait", fd, {
	        withCredentials: true,
	        headers: {'Content-Type': undefined },
	        transformRequest: angular.identity
	    }).success(function(data) { 	    	
	    	$rootScope.cv.profile.portrait = data; 
	    }).error(function() { alert("Det gick inte att ladda upp bilden!") } );
	};
}