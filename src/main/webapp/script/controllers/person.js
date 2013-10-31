function PersonController($rootScope, $scope, $location) {
	// TODO check if window.scope = $scope is required to make this work (test ng-change instead of onchange)
	
	// Set bread crumb text
	$rootScope.breadcrumb = 
		"<b><a href='#/person'>Person</a> ></b>" +
		"Anpassning > " +
		"Egenskaper > " +
		"Ordmoln > " +
		"Bilder > " +
		"Layout";

	// Set wizard navigation button properties
	$rootScope.previousButtonLabel = "<< Tillbaka";
	$rootScope.hidePrevious = true;
	$rootScope.nextButtonLabel = "Nästa >>";
	$rootScope.nextButtonDisabled = true;
	$rootScope.hideNext = false;
	
	// Capture selected RTF-file
	window.scope = $scope;
	$rootScope.selectedRtfFile = "";
	
	$rootScope.fruit = ['apples','bananas','citrus','dragonfruit','eggs'];
	
	$scope.setSelectedRtfFile = function (element) {
		$scope.$apply(function () {
			$rootScope.selectedRtfFile = element.files[0].name;
			$rootScope.nextButtonDisabled = false;			
		});
	};
	
	$rootScope.next = function () {
		$rootScope.nextButtonDisabled = true;
		// Verify that selected file ends with .rtf
		var fileName = document.getElementById("rtfCvFile").files[0].name;
		if (!fileName.match(/\.rtf/i)) { // Match on ".rtf" case insensitive
			alert("Fel typ av fil vald\n\nVar god ange en RTF-fil.");
			return;
		}
		
		var xhr = new XMLHttpRequest();
		
		// Handle upload complete
		xhr.addEventListener("load", function (event) {
			var responseStatus = event.target.status;
			if (responseStatus == 500) {
				alert("Ett fel uppstod!\n\nInl�sning av CV RTF-fil misslyckades.");
				return;
			}
			
			$rootScope.cv = JSON.parse(event.target.responseText);

			window.location = "#/select-info"
			// 2013-10-17: Added more wizard steps -> window.location = "#/anpassning";
		}, false);
		
		// Handle upload failed
		xhr.addEventListener("error", function (event) {
			alert(event);
			console.error(event);
		}, false);
		  
		// Post RTF-file to server
		xhr.open("POST", "api/rtfparser");
		var form = new FormData(document.getElementById("rtfCvFileForm"));
		xhr.send(form);
		
	};	
}