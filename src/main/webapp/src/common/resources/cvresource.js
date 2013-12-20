'use strict';

angular.module('resources.cvresource', []).factory('CvResource', ['$http', function($http) {
	
	// var resourceUrl = 'api/rtfparser';
	var resourceUrl = 'http://localhost:8081/cv-converter/api/rtfparser'
	var model = { cv : {} };
	var serviceInstance = {
		
		/**
		 * Creates a new CV model from a rtf document.
		 * 
		 * @param files		: form files
		 * @param success	: callback function to be executed on success
		 */
		create : function(files, success) {					
			var fd = new FormData();
			fd.append("rtfCvFile", files[0]);
			$http.post(resourceUrl, fd, {
				withCredentials : true,
				headers : {'Content-Type' : undefined},
				transformRequest : angular.identity
			}).success(function(data) {				
				model.cv = data;
				success();
			}).error(function(err) {
				alert("Det uppstod ett fel vid konverteringen! Felmeddelande från server: " + err);
			});
		},
		
		/**
		 * Gets the model backed by this resource.
		 *
		 */
		get : function() {
			return model;
		},
		
		/**
		 * Updates the model backed by this resource.
		 */
		update : function(success) {
			$http.put(resourceUrl, model.cv).
			  success(function(data, status, headers, config) {
				  model.cv = data;
				  success();
			  }).
			  error(function(data, status, headers, config) {
			    alert("Ett fel uppstod när egenskaperna lades till. " + data);
			});
		}
	};
	return serviceInstance;
} ]);