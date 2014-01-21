'use strict';

angular.module('resources.layoutsresource', []).factory('LayoutsResource', ['$http', function($http) {
	
	var resourceUrl = 'api/layouts';
	
	var serviceInstance = {
		
		/**
		 * Reads all layouts from database.
		 * 
		 * @param success	: callback function to be executed on success. 
		 * 					  function(data) {  }
		 * 					  where data is an array of layouts
		 */
		get : function (success) {
			$http.get(resourceUrl).success(function (data) {
				success(data);
			});
		},
		
		/**
		 * Reads one layout from database.
		 * 
		 * @param id		: id of the layout to get.
		 * @param success	: success callback.
		 */
		getById : function(id, success) {
			$http.get(resourceUrl + "/" + id).success(function (data) {
				success(data);
			});
		},
		
		/**
		 * Creates a new layout from an uploaded xsl file.
		 * 
		 * @param files		: the file containing xsl data.
		 * @paran name		: the name of the style sheet.
		 * @param success	: success callback.
		 */
		create : function (files, name, success) {
			var fd = new FormData();
			fd.append("xslStylesheet", files[0]);
			fd.append("name", name);
			$http.post(resourceUrl, fd, {
				withCredentials : true,
				headers : {'Content-Type' : undefined},
				transformRequest : angular.identity
			}).success(function(data) {				
				success(data);
			}).error(function(err) {
				alert("Det gick inte att skapa en ny layout!" + err);
			});
		},
		
		/**
		 * Updates an existing layout in the database.
		 * 
		 * @param id		: id of the layout.
		 * @param layout	: the layout data as a json string. 
		 * @param success	: success callback.
		 */
		update : function (id, layout, success) {
			$http.put(resourceUrl + "/" + id, layout, {
				withCredentials : true,
				headers : {'Content-Type' : 'application/json'},
				transformRequest : angular.identity
			}).success(function(data) {				
				success(data);
			}).error(function(err) {
				alert("Det gick inte att uppdatera layouten!" + err);
			});
		},
		
		/**
		 * Deletes one layout from the database.
		 * 
		 * @param id		: id of the layout to delete.		 
		 */
		deleteLayout : function (id, success) {
			
			$http({method: 'DELETE', url: resourceUrl + "/" + id}).
			  success(function() {
			    success();
			});
			
			// IE does not seem to like the use of the reserved word delete
			//$http.delete(resourceUrl + "/" + id).success(function() {
			//	success();
			//});
		} 
	
	};
	
	return serviceInstance;
} ]);