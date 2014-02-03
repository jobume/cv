'use strict';

angular.module('resources.cvresource', []).factory('CvResource', ['$http', function($http) {
	
	/** Url to the parsers that parses a rtf cv to a json cv. */
	var parserUrl = 'api/rtfparser';
	
	/** Url to the persistence service that saves and updates existing jsoncv:s. */
	var resourceUrl = 'api/jsoncv';
	
	var model = { cv : {} };
	
	// var model = { cv : {"name":"Jonas é Paro","id":0,"contentLanguage":"SWEDISH","printDate":"2013-08-26","profile":{"name":"Jonas é Paro","title":null,"dateOfBirth":"05-02-1982","portrait":{"name":null,"url":null},"phoneNumber":null,"firstName":"Jonas","lastName":"é Paro"},"description":"Jonas är en driven systemutvecklare med huvudinriktning inom Java. Han har arbetat med Java sedan 2008, både i produktbolag och som konsult. Han har arbetat med flertalet olika tekniska plattformar och känner sig bekväm med att snabbt sätta sig in i nya tekniska miljöer.\r\nSom person är Jonas är noggrann och kreativ och föredrar att se motgångar som utmaningar snarare än problem.","engagements":[{"date":"2012","name":"Coop","description":"Utveckling av ett system för inläsning av loggfiler samt automatisk rapportering av feltyper. Skapade funktionalitet för rapportering av vanligt förekommande fel","shortDescription":"Utveckling av ett system för inläsning av loggfiler samt automatisk rapportering av feltyper. Skapade funktionalitet för rapportering av vanligt förekommande fel","important":false,"duration":1},{"date":"2011 - 2012","name":"Vattenfall","description":"Utveckling av ett rapporteringssystem för underlag till energikostnadsprojektioner. Arbetet innefattade export och import av stora datamängder till excelblad, utveckling av gränssnitt för manipulering och korrigering av data samt utveckling av automatiserade tester för webbgränssnitt.","shortDescription":"Utveckling av ett rapporteringssystem för underlag till energikostnadsprojektioner. Arbetet innefattade export och import av stora datamängder till excelblad, utveckling av gränssnitt för manipulering och korrigering av data samt utveckling av automatiserade tester för webbgränssnitt.","important":true,"duration":2},{"date":"2011","name":"UC","description":"Utveckling av CM-verktyg för automatiserad driftsättning i komplex miljö. Uppdraget innefattade koordinering av driftsättningar samt ansvar för prestandatester och disaster-tester.","shortDescription":"Utveckling av CM-verktyg för automatiserad driftsättning i komplex miljö. Uppdraget innefattade koordinering av driftsättningar samt ansvar för prestandatester och disaster-tester.","important":false,"duration":7},{"date":"2011","name":"Vattenfall","description":"Framtagning av stöd för import av stora excelfiler samt lagring av exceldata i databas. Extremt stora datamängder ställde höga krav på implementation av importfunktion.\r\nTools: Java, Oracle DB, Weblogic Server, Spring MVC, Apache POI","shortDescription":"Framtagning av stöd för import av stora excelfiler samt lagring av exceldata i databas. Extremt stora datamängder ställde höga krav på implementation av importfunktion.\r\nTools: Java, Oracle DB, Weblogic Server, Spring MVC, Apache POI","important":false,"duration":1},{"date":"2010 - 2011","name":"UC","description":"Utveckling av en tjänst för kreditupplysningar via SMS. Den nya tjänsten var en ersättningstjänst för befintligt system som byggde på telefonväxel. Uppdraget innefattade även utveckling av administrationsgränssnitt för tjänsten.\r\nVerktyg/Teknologier:\r\nJava, Junit, AspectJ, Spring MVC, Oracle DB, Weblogic Server, Bamboo,\r\nDevelopment of a mobile credit information service. The service replaced an older system that was based on telephony. The assignment also included the development of an administration GUI for the system.\r\nTools: Java, Junit, AspectJ, Spring MVC, Oracle DB, Weblogic Server, Bamboo","shortDescription":"Utveckling av en tjänst för kreditupplysningar via SMS. Den nya tjänsten var en ersättningstjänst för befintligt system som byggde på telefonväxel. Uppdraget innefattade även utveckling av administrationsgränssnitt för tjänsten.\r\nVerktyg/Teknologier:\r\nJava, Junit, AspectJ, Spring MVC, Oracle DB, Weblogic Server, Bamboo,\r\nDevelopment of a mobile credit information service. The service replaced an older system that was based on telephony. The assignment also included the development of an administration GUI for the system.\r\nTools: Java, Junit, AspectJ, Spring MVC, Oracle DB, Weblogic Server, Bamboo","important":false,"duration":3},{"date":"2008 - 2010","name":"Bozoka.com AB","description":"Utveckling och driftsättning av en plattform för mobila betallösningar. Uppdraget innefattade både Front-end-utveckling och back-end-utveckling. Lösningen var ett distribuerat system med höga krav på transaktionshastighet och integritet.\r\nVerktyg/Teknologier: Java, Spring, Jini, Hibernate, MySql, Cruise control, Nagios, JUnit, HTML/Css, JQuery, Liferay, Jetty, Apache\r\nDevelopment and maintenance of a platform for mobile payment solution. Both front-end and back-end development. The developed system was distributed and involved high demands on transaction speed and transaction integrity.\r\nTools: Java, Spring, Jini, Hibernate, MySql, Cruise control, Nagios, JUnit, HTML/Css, JQuery, Liferay, Jetty, Apache","shortDescription":"Utveckling och driftsättning av en plattform för mobila betallösningar. Uppdraget innefattade både Front-end-utveckling och back-end-utveckling. Lösningen var ett distribuerat system med höga krav på transaktionshastighet och integritet.\r\nVerktyg/Teknologier: Java, Spring, Jini, Hibernate, MySql, Cruise control, Nagios, JUnit, HTML/Css, JQuery, Liferay, Jetty, Apache\r\nDevelopment and maintenance of a platform for mobile payment solution. Both front-end and back-end development. The developed system was distributed and involved high demands on transaction speed and transaction integrity.\r\nTools: Java, Spring, Jini, Hibernate, MySql, Cruise control, Nagios, JUnit, HTML/Css, JQuery, Liferay, Jetty, Apache","important":false,"duration":32}],"professionalKnowledge":[],"technologies":[],"industryKnowledge":[],"certifications":[],"foreignLanguages":[],"courses":[{"date":"2011-09","name":"Springsource - Java and Hibernate","location":""}],"organisations":[],"employments":[],"educations":[{"date":"1998-08 - 2001-06","name":"3-årig Naturvetenskapligt Gymnasium","location":"Östra Gymnasiet, Umeå"},{"date":"2003-08 - 2008-04","name":"Civilingenjör – Interaktion och Design","location":""},{"date":"","name":"Umeå universitet","location":""}],"coverImage":{"name":null,"url":null},"tags":[],"personalQualities":[],"office":"Umeå"} 
	//	};
	
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
			$http.post(parserUrl, fd, {
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
		 * Generates a tag cloud for the model backed by this resource.
		 */
		generateCloud : function(success) {
			$http.put(resourceUrl, model.cv).
			  success(function(data, status, headers, config) {
				  model.cv = data;
				  success();
			  }).
			  error(function(data, status, headers, config) {
			    alert("Ett fel uppstod när egenskaperna lades till. " + data);
			});
		},
		
		/**
		 * Persists a cv in the database
		 */
		save : function(success) {
			$http.post(resourceUrl, model.cv).success(function(data) {				
				model.cv = data;
				success();
			}).error(function(err) {
				alert("Det uppstod ett fel när CVt sparades! Felmeddelande från server: " + err);
			});
		},
		
		/**
		 * Updates the persistent model backed by this resource.
		 */
		update : function(success) {
			$http.put(resourceUrl + "/" + model.cv.id, model.cv).
			  success(function(data, status, headers, config) {
				  model.cv = data;
				  success();
			  }).
			  error(function(data, status, headers, config) {
			    alert("Ett fel uppstod när egenskaperna lades till. " + data);
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
		}, 
		
		/**
		 * Deletes a persisted cv from the database.
		 */
		deleteCv : function(id, success) {
			$http({method: 'DELETE', url: resourceUrl + "/" + id}).
			  success(function() {
			    success();
			});
		}
	};
	
	return serviceInstance;
} ]);