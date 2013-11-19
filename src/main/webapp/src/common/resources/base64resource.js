'use strict';

angular.module('resources.base64resource', []).factory('Base64Resource', ['$http', function($http) {
	
	var resourceUrl = 'api/base64';
	
	var serviceInstance = {
		
		/**
		 * Uploads a file and returns it as a base64-encoded string
		 * 
		 * @param files		: form files
		 * @param success	: callback function to be executed on success. 
		 * 					  function(data) {  }
		 * 					  where data is the base64 encoded string.
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
		    }).error(function() { alert("Det gick inte att base64-koda filen.") } );
		}
	};
	
	return serviceInstance;
} ]);