'use strict';

angular.module('adjustments', [ 'ngRoute', 'resources.cvresource', 'services.navigation' ])

.config(['$routeProvider', function ($routeProvider) {
	  $routeProvider.when('/anpassa', {
	    templateUrl:'src/app/adjustments/adjustments.tpl.html',
	    controller:'AdjustmentsController'
	    }
	  );
	}])
	
.controller('AdjustmentsController', ['$scope', 'CvResource', 'SelectionService','Navigation', 
                                      function($scope, CvResource, SelectionService, Navigation) {
	
	$scope.model = {"cv":{"contentLanguage":"SWEDISH","printDate":"2013-06-27","profile":{"name":"Robert Olsson","title":null,"dateOfBirth":"01-04-1969","portrait":{"name":null,"url":"","localUrl":""},"phoneNumber":null,"firstName":"Robert","lastName":"Olsson"},"description":"Robert är en mycket erfaren systemutvecklare som huvudsakligen ägnar sig åt OpenSource-baserad systemutveckling i C/S- och webb-miljö. Han har framförallt jobbat med Java och MySQL men han har även utvecklat mobila system baserat på C# och C++. Robert är en driven utvecklare som tycker om utmaningar, är flexibel och är lätt att sammarbeta med. Han gillar att ta ansvar och har i sina uppdrag haft både rollen som projektledare och som serviceledare.","engagements":[{"date":"2007 - 2012","name":"Siemens Financial Services","description":"Uppdraget är ett förvaltningsåtagande för delar av Siemens egenutvecklade affärssystem för leasingaffärer. I projektet har Robert rollen som Serviceledare och Systemutvecklare. Robert har även deltagit i fler utvecklingsuppdrag vid sidan om förvaltningsåtagandet. Systems webgränssnitt är utvecklat i Lasso. Serverdelen är i huvudsak Java men även några delar skrivna i Lasso. De delar som är skrivna i java hanterar främst kommunkation via webservices mot externa system som SAP och Transact. Webgränssnittet driftas i en Lasso Professional server och en apache server. De delar som är skrivna i java driftas i en JBoss Application Server. Databasen som används är MySql.","important":false,"duration":70},{"date":"2006","name":"Umeå Universitet, LADOK-enheten","description":"Utvecklare i projektet Ping version 2. Jobbade med presentationslagret (användargränssnittet, AJAX-kommunikationen samt förbättringar i användningen av ramverket Struts).","important":false,"duration":3},{"date":"2004 - 2006","name":"Enlight AB","description":"","important":false,"duration":23},{"date":"1999 - 2004","name":"Spreadskill AB","description":"","important":false,"duration":58},{"date":"1997 - 1999","name":"Svenska Test AB","description":"","important":false,"duration":26}],"professionalKnowledge":[{"name":"Rational Rose","level":"KNOWLEDGEABLE","important":null,"englishLevelName":"Knowledgeable","swedishLevelName":"Baskunskap"},{"name":"Systems Architecture","level":"KNOWLEDGEABLE","important":null,"englishLevelName":"Knowledgeable","swedishLevelName":"Baskunskap"},{"name":"Simple Object Access Protocol (SOAP)","level":"KNOWLEDGEABLE","important":null,"englishLevelName":"Knowledgeable","swedishLevelName":"Baskunskap"},{"name":"Web Services","level":"KNOWLEDGEABLE","important":null,"englishLevelName":"Knowledgeable","swedishLevelName":"Baskunskap"}],"technologies":[{"name":"IBM DB2 Universal Database","level":"KNOWLEDGEABLE","important":null,"englishLevelName":"Knowledgeable","swedishLevelName":"Baskunskap"},{"name":"MySQL","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"PostgreSql","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Microsoft ADO .NET (ActiveX Data Objects .NET)","level":"KNOWLEDGEABLE","important":null,"englishLevelName":"Knowledgeable","swedishLevelName":"Baskunskap"},{"name":"Microsoft Visual C#","level":"KNOWLEDGEABLE","important":null,"englishLevelName":"Knowledgeable","swedishLevelName":"Baskunskap"},{"name":"BEA WebLogic","level":"KNOWLEDGEABLE","important":null,"englishLevelName":"Knowledgeable","swedishLevelName":"Baskunskap"},{"name":"Jarkarta Tomcat","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"JBoss","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Linux","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Hypertext Markup Language (HTML)","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Java 2 Enterprise Edition (J2EE)","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Java","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Javascript","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Perl","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"PHP","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Python","level":"KNOWLEDGEABLE","important":null,"englishLevelName":"Knowledgeable","swedishLevelName":"Baskunskap"},{"name":"Apache Ant","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Eclipse IDE","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Enterprise Java Beans (EJB)","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"JUnit","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Sun Netbeans","level":"KNOWLEDGEABLE","important":null,"englishLevelName":"Knowledgeable","swedishLevelName":"Baskunskap"},{"name":"Swing (Java)","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Asynchronous JavaScript and XML (AJAX)","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Hibernate","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Java DataBase Connection API (JDBC)","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Java Server Pages (JSP)","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Java Servlets","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"JSP Standard Tag Library (JSTL)","level":"KNOWLEDGEABLE","important":null,"englishLevelName":"Knowledgeable","swedishLevelName":"Baskunskap"},{"name":"Spring Framework","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"},{"name":"Cascading Style Sheet (CSS)","level":"EXPERIENCED","important":null,"englishLevelName":"Experienced","swedishLevelName":"Erfaren"}],"industryKnowledge":[{"name":"Financial Services","level":"KNOWLEDGEABLE","important":null,"englishLevelName":"Knowledgeable","swedishLevelName":"Baskunskap"}],"certifications":[],"foreignLanguages":[{"name":"English","level":"FLUENT","englishLevelName":"Fluent","swedishLevelName":"Flytande"},{"name":"German","level":"BASIC_KNOWLEDGE","englishLevelName":"Basic Knowledge","swedishLevelName":"Baskunskap"},{"name":"Swedish","level":"MOTHER_TONGUE","englishLevelName":"Mother Tongue","swedishLevelName":"Modersmål"}],"courses":[{"date":"1993-09 - 1993-12","name":"Projektledare","location":""}],"organisations":[],"employments":[{"date":"2006","name":"Sogeti Sverige AB","description":"","important":false,"duration":0},{"date":"2004 - 2006","name":"Enlight AB","description":"Utvecklade webbaserat system för kunskapsmätning som baseras på Java (Servlets, JSP, JavaScript) och IBM DB2. Ansvarig för användargränssnittet.","important":false,"duration":24},{"date":"2004","name":"Egen företagare","description":"","important":false,"duration":0},{"date":"1999 - 2004","name":"Spreadskill AB","description":"IT-chef och delägare med ansvar för utveckling och drift av internt och externt IT-stöd. Spreadskill tillhandahöll system och konsultresurser inom området kunskapsmätningar och undersökningar.","important":false,"duration":61},{"date":"1997 - 1999","name":"Svenska Test AB","description":"","important":false,"duration":28}],"educations":[{"date":"1985-08 - 1989-06","name":"4-årig Tekniskt Gymnasium el-tele","location":"Gymnasium"},{"date":"1991-09 - 1993-05","name":"Data och Elektronik (120p)","location":"Umeå universitet"},{"date":"1994-09","name":"Civilingenjör i Teknisk Datavetenskap (180 p)","location":"Umeå universitet"}],"coverImage":null,"tags":[],"personalQualities":[]}};
	//CvResource.get();
	
	$scope.selectedEngagements = 0;
	$scope.allEngagementsSelected = false;
	$scope.selectedTechs = 0;
	$scope.allTechsSelected = false;
	var MAX_DESC_LENGTH = 570;
	
	Navigation.onNext(function (success, state) {
		var valid = false;
		if($scope.model.cv.profile.title && $scope.model.cv.profile.title.length > 0) {
			valid = true;
		} else {
			valid = false;
			alert('Titel är obligatoriskt!');
		}
		if(valid && $scope.model.cv.description && $scope.model.cv.description.length <= MAX_DESC_LENGTH) {
			valid = true;
		} else {
			valid = false;
			alert('Maxlängd för Beskrivning är ' + MAX_DESC_LENGTH);
		}
		if(valid) {
			success();
		} else {
			state.disabled = false;
		}
	});
	
	$scope.selectEngagements = function () {
		$scope.selectedEngagements = 
			SelectionService.selectAll($scope.model.cv.engagements, $scope.allEngagementsSelected);
	};
	
	$scope.selectEngagement = function (engagement, i) {
		$scope.selectedEngagements =
			SelectionService.countSelected($scope.model.cv.engagements);
	};
	
	$scope.countSelectedEngagements = function() {
		$scope.selectedEngagements = 
			SelectionService.countSelected($scope.model.cv.engagements);
	}
	
	$scope.selectTechs = function () {
		$scope.selectedTechs = 
			SelectionService.selectAll($scope.model.cv.technologies, $scope.allTechsSelected);
	};
	
	$scope.selectTech = function (tech) {
		tech.important = !tech.important;
		$scope.selectedTechs = 
			SelectionService.countSelected($scope.model.cv.technologies);
	};
	
	$scope.countSelectedTechs = function () {
		$scope.selectedTechs = 
			SelectionService.countSelected($scope.model.cv.technologies);
	};
	
	$scope.getSelectedEngagements = function () {
		return $scope.selectedEngagements;
	};
	
	$scope.getSelectedTechs = function () {
		return $scope.selectedTechs;
	};
	
	$scope.countSelectedEngagements();
	$scope.countSelectedTechs();
	
} ])

.factory('SelectionService', function() {
	
	var serviceInstance = {
		countSelected : function(elems) {
			var selectedCount = 0;
			for (var i = 0; i < elems.length; i++) {
				if (elems[i].important) {
					selectedCount++;
				}
			}
			return selectedCount;
		},
		selectAll : function(elems, checked) {
			for(var i=0; i<elems.length; i++) {
				elems[i].important = checked;
			}
			if (checked ) {
				return elems.length;
			} else {
				return 0;
			}
		}		
	};
	return serviceInstance;
})

.directive('customz', function() {
	  return {
	    require: 'ngModel',
	    link: function(scope, elm, attrs, ctrl) {
	      ctrl.$parsers.unshift(function(viewValue) {
	    	  console.log("Value of important: " + scope.engagementForm.important);
	    	  console.log("Value of important equals true: " + (scope.engagementForm.important == true));
	    	  if (viewValue == "I AM VALID") {
	          // it is valid
	          ctrl.$setValidity('integer', true);
	          return viewValue;
	        } else {
	          // it is invalid, return undefined (no model update)
	          ctrl.$setValidity('integer', false);
	          return undefined;
	        }
	      });
	    }
	  };
});