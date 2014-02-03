'use strict';

angular.module('resources.portraitresource', []).factory('PortraitResource', ['$http', function($http) {
	
	var resourceUrl = 'api/portrait';
	
	var serviceInstance = {
		
		/**
		 * Uploads a portrait image and returns the public location for the file.
		 * 
		 * @param files		: form files
		 * @param success	: callback function to be executed on success. 
		 * 					  function(data) {  }
		 * 					  where data is the url to the uploaded image.
		 */
		create : function (files, name, success) {
			var fd = new FormData();
		    //Take the first selected file
		    fd.append("file", files[0]);
		    fd.append("fileName", name);
		    $http.post(resourceUrl, fd, {
		        withCredentials: true,
		        headers: {'Content-Type': undefined },
		        transformRequest: angular.identity
		    }).success(function(data) { 	    			    	
		    	success(data); 
		    }).error(function() { alert("Det gick inte att ladda upp bilden!") } );
		},
		
		/**
		 * Gets all cover images.
		 * 
		 * @param success	: callback function to be executed on success. 
		 */
		get : function (success) {
			$http({method: 'GET', url: resourceUrl}).
			  success(function(data, status, headers, config) {		
				  success(data);	    
			}).error(function(data, status, headers, config) {
			    alert("Kunde inte ladda bilder. Felmeddelande från server: " + data);
			});
		},
		
		/**
		 * Deletes a cover image.
		 * 
		 * @param id		: id of image to delete.
		 * @param success 	: success callback.
		 */
		deletePortrait : function (id, success) {
			$http({method: 'DELETE', url: resourceUrl + "/" + id }).
				success( function() {
					success();
			}).error(
					function(data, status, headers, config) {
						alert("Radera bild. Felmeddelande från server: " + data);
			});
		}
	};
	
	return serviceInstance;
} ]);