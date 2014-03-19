'use strict';

angular.module('resources.pdfresource', []).factory('PdfResource', ['$http', function($http) {
	
	var resourceUrl = 'api/pdf';
	
	var serviceInstance = {
		
		/**
		 * Creates a pdf from a cv.
		 * 
		 * @param cv		: cv to convert to pdf.
		 * @param layoutId	: id of the layout to use.
		 * @param success	: callback function to be executed on success. 
		 * 					  function(data) {  }
		 * 					  where data is the url to the created pdf.
		 * @param error		: callback function for error.
		 */
		create : function (cv, layoutId, success, error) {
			var postUrl = resourceUrl + "/" + layoutId;
			$http.post(postUrl, cv).success(function(data) { 	    			    	
		    	var getUrl = resourceUrl + "/" + data;
		    	success(getUrl); 
		    }).error(function(data, status, headers, config) {
		    	if(error) { error (); }
		    	alert("Ett fel uppstod vid konvertering. Felmeddelande från server: " + data); 
		    });
		}
	
	};
	
	return serviceInstance;
} ]);