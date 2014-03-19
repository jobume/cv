'use strict';

angular.module('resources.cvresource', []).factory('CvResource', ['$http', function($http) {
	
	/** Url to the parsers that parses a rtf cv to a json cv. */
	var parserUrl = 'api/rtfparser';
	
	/** Url to the persistence service that saves and updates existing jsoncv:s. */
	var resourceUrl = 'api/jsoncv';
	
	var model = { cv : {} };
	
	var serviceInstance = {
		
		/**
		 * Creates a new CV model from a rtf document.
		 * 
		 * @param files		: form files.
		 * @param success	: callback function to be executed on success.
		 * @param error		: callback function to be executed on error.
		 */
		create : function(files, success, error) {					
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
				if(error) { error(); }
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
		 * Generates a tag cloud for the model backed by this resource.
		 * 
		 * @param success	: callback function on success.
		 * @param error		: callback function on error.
		 */
		generateCloud : function(success, error) {
			$http.put(resourceUrl, model.cv).
			  success(function(data, status, headers, config) {
				  model.cv = data;
				  success();
			  }).
			  error(function(data, status, headers, config) {
				if(error) { error(); }
			    alert("Ett fel uppstod när egenskaperna lades till. " + data);
			});
		},
		
		/**
		 * Persists a cv in the database
		 * 
		 * @param success	: callback function on success.
		 */
		save : function(success) {
			$http.post(resourceUrl + "/json", model.cv).success(function(data) {				
				model.cv = data;
				success();
			}).error(function(err) {
				alert("Det uppstod ett fel när CVt sparades! Felmeddelande från server: " + err);
			});
		},
		
		/**
		 * Updates the persistent model backed by this resource.
		 * 
		 * @param success	: callback function on success.
		 * @param error		: callback function on error.
		 */
		update : function(success, error) {
			$http.put(resourceUrl + "/" + model.cv.id, model.cv).
			  success(function(data, status, headers, config) {
				  model.cv = data;
				  success();
			  }).
			  error(function(data, status, headers, config) {
				if(error) { error(); }
			    alert("Ett fel uppstod när CV:et skulle uppdateras. " + data);
			});
		},
		
		/**
		 * Gets a persisted cv from the database.
		 */
		getCv : function(id, success) {
			$http.get(resourceUrl + "/" + id).success(function (data) {
				model.cv = data;
				model.cv.id = id;
				success();
			});
		},
		
		/**
		 * Lists all persisted cvs.
		 */
		list : function(success) {						
			$http.get(resourceUrl).success(function (data) {				
				success(data);
			});
			//var data =  [ { name : "Jonas", ts : "0", office : "Ume", id : 1}, { name : "Tobias", ts : "1", office : "Ume", id : 1} ];
			//success(data);
		}, 
		
		/**
		 * Deletes a persisted cv from the database.
		 */
		deleteCv : function(id, success) {
			$http({method: 'DELETE', url: resourceUrl + "/" + id}).
			  success(function() {
			    success();
			});
		},
		
		/**
		 * Change name of cv with id : id
		 */
		changeName : function(id, name, success, error) {
			$http.put(resourceUrl + "/partial/" + id, name).
			  success(function(data, status, headers, config) {
				  success();
			  }).
			  error(function(data, status, headers, config) {
				if(error) { error(); }
			    alert("Ett fel uppstod när namnet ändrades. " + data);
			});
		},
		
		purge : function() {
			model.cv = {};
		}
	};
	
	return serviceInstance;
} ]);