function AdminNewLayoutController($rootScope, $scope, $http, $location) {
	// Set bread crumb text
	$rootScope.breadcrumb = "";
	
	// Set wizard navigation button properties
	$rootScope.hidePrevious = true;
	$rootScope.hideNext = true;
	
	$scope.base64 = function () {
		$location.path("admin/base64");
	};
	
	$scope.createLayout = function () {
		// Verify that selected file ends with .xsl
		var fileName = document.getElementById("xslFile").files[0].name;
		if (!fileName.match(/\.xsl/i)) { // Match on ".xsl" case insensitive
			alert("Fel typ av fil vald\n\nVar god ange en XSL-fil.");
			return;
		}
		
		var xhr = new XMLHttpRequest();
		
		// Handle upload complete
		xhr.addEventListener("load", function (event) {
			var responseStatus = event.target.status;
			if (responseStatus == 500) {
				alert("Ett fel uppstod!\n\nLayouten kunde inte skapas."); // TODO write better text here
				return;
			}
			
			console.log(event.target.responseText);
			window.location = "#/admin/layouter"; // TODO Why does not "$location.path('anpassning');" work here
		}, false);
		
		// Handle upload failed
		xhr.addEventListener("error", function (event) {
			alert(event);
			console.error(event);
		}, false);
		  
		// Post RTF-file to server
		xhr.open("POST", "api/layouts");
		var fd = new FormData(document.getElementById("newLayoutForm"));
		xhr.send(fd);
	};
	
	$scope.backToAdmin = function () {
		$location.path("admin/layouter");
	};
}
