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
		}
	};
	
	return serviceInstance;
} ]);