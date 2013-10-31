function AdminLayoutsController($rootScope, $scope, $http, $location) {
	// Set bread crumb text
	$rootScope.breadcrumb = "";
	
	// Set wizard navigation button properties
	$rootScope.hidePrevious = true;
	$rootScope.hideNext = true;
	
	// Get layouts
	$http.get("api/layouts").success(function(data) {
		$scope.layouts = data;
	});
	
	$scope.editLayout = function (id) {
		$location.path("admin/layouter/" + id);
	};
	
	$scope.deleteLayout = function (id) {
		var answer = prompt("Bekräfta radering av layout.\n" +
				"Skriv \"RADERA\" (med små eller stora bokstäver):", "");
		if (answer.toLowerCase() === "radera") {
			$http.delete('api/layouts/' + id);
			for (var i = 0; i < $scope.layouts.length; i++) {
				if ($scope.layouts[i].id === id) {
					$scope.layouts.splice(i, 1);
					break;
				}
			}
		}
	};
	
	$scope.createLayout = function () {
		$location.path("admin/ny");
	};
	
	$scope.backToStart = function () {
		$location.path("person");
	};
	
}
