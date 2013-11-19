'use strict';

angular.module('resources.coverimageresource', []).factory('CoverImageResource', ['$http', function($http) {
	
	var resourceUrl = 'api/coverimage';
	
	var serviceInstance = {
		
		/**
		 * Uploads a cover image and returns the public location for the file.
		 * 
		 * @param files		: form files
		 * @param success	: callback function to be executed on success. 
		 * 					  function(data) {  }
		 * 					  where data is the url to the uploaded image.
		 */
		create : function (files, success) {
			var fd = new FormData();
		    //Take the first selected file
		    fd.append("file", files[0]);		    
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
		deleteCoverImage : function (id, success) {
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