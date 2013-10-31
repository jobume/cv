function AdminEditLayoutController($rootScope, $scope, $http, $location, $routeParams) {
	// Set bread crumb text
	$rootScope.breadcrumb = "";
	
	// Set wizard navigation button properties
	$rootScope.hidePrevious = true;
	$rootScope.hideNext = true;
	
	$scope.inputType = 'textarea'; // TODO check if setting a radiobox like this helps solve the problem with selecting the first item in layouts for pdf generation

	var id = $routeParams.id;
	
	$http.get("api/layouts/" + id).success(function(data) {
		$scope.layout = data;
	}).error(function(data, status, headers, config) {
		// TODO fix some error handling here
		console.error(data);
		console.error(status);
	});
		
	$scope.updateLayout = function () {
		console.log(JSON.stringify($scope.layout)); // TODO remove
		$http({
			method: "PUT",
			url: "api/layouts/" + id,
			data: JSON.stringify($scope.layout),
			headers: {"Content-Type": "application/json"}
		}).success(function(data) {
			$scope.backToAdmin();
		});
	};
	
	$scope.deleteLayout = function (id) {
		var answer = prompt("Bekräfta radering av layout.\n" +
				"Skriv \"RADERA\" (med små eller stora bokstäver):", "");
		if (answer.toLowerCase() === "radera") {
			$http.delete('api/layouts/' + id);
			$scope.backToAdmin();
		}
	};
	
	$scope.backToAdmin = function () {
		$location.path("admin/layouter");
	};
		
}
