'use strict';

describe('Test suite for the adjustments module', function(){
	  beforeEach(module('adjustments'));
	  
	  var cvMock;
	  beforeEach(inject(function() {
	      cvMock = { 
	    		  technologies : [{ important : false },{ important : true },{ important : false }], 
	    		  engagements : [ { important : false}, { important : true}, { important : true} ] } ;	      
	  }));
	  
	  describe('Test suite for the adjustments controller', function(){
		  
		  var controller, scope, cvResourceMock, serviceMock;
		  beforeEach(inject(function($rootScope, $controller) {
		      scope = $rootScope.$new();
		      cvResourceMock = { get : function () { }, create : function () {} };
		      
		
		      serviceMock = { selectAll : function () {}, countSelected : function () { return 242; } };
		      
		      spyOn(cvResourceMock, 'get').andReturn( { cv : cvMock } );
		      
		      controller = $controller('AdjustmentsController', { 
		    	  $scope : scope, CvResource : cvResourceMock, SelectionService : serviceMock });
		      
		  }));
		  
		  it('should load the model', inject(function() {
			  expect(cvResourceMock.get).toHaveBeenCalled();
		  }));
		  
		  it('should have the expected methods', inject(function() {
			  expect(scope.selectTechs).toBeDefined();
			  expect(scope.selectTech).toBeDefined();
			  expect(scope.countSelectedTechs).toBeDefined();
			  
			  expect(scope.selectEngagements).toBeDefined();
			  expect(scope.selectEngagement).toBeDefined();
			  expect(scope.countSelectedEngagements).toBeDefined();
			  	  
		  }));
		  
		  it('should count selection on start', inject(function() {
			  expect(scope.selectedEngagements).toEqual(242);
			  expect(scope.selectedTechs).toEqual(242);
		  }));
		  
	  });
	  
	  describe('Test suite for the select service', function(){
		  
		  var service;
		  beforeEach(inject(function(SelectionService) {	  
			  service = SelectionService;			  
		  }));
		  
		  it('should be able to check all', inject(function() {
			  expect(service.selectAll).toBeDefined();
			  
			  expect(cvMock.technologies[0].important).toBe(false);
			  expect(cvMock.technologies[1].important).toBe(true);
			  expect(cvMock.technologies[2].important).toBe(false);
			  
			  service.selectAll(cvMock.technologies, true);
			  
			  expect(cvMock.technologies[0].important).toBe(true);
			  expect(cvMock.technologies[1].important).toBe(true);
			  expect(cvMock.technologies[2].important).toBe(true);
			  
			  expect(cvMock.engagements[0].important).toBe(false);
			  expect(cvMock.engagements[1].important).toBe(true);
			  expect(cvMock.engagements[2].important).toBe(true);
			  
			  service.selectAll(cvMock.engagements, true);
			  
			  expect(cvMock.engagements[0].important).toBe(true);
			  expect(cvMock.engagements[1].important).toBe(true);
			  expect(cvMock.engagements[2].important).toBe(true);
			  
			  service.selectAll(cvMock.engagements, false);
			  
			  expect(cvMock.engagements[0].important).toBe(false);
			  expect(cvMock.engagements[1].important).toBe(false);
			  expect(cvMock.engagements[2].important).toBe(false);
			  
		  }));
		  
		  it('should be able to count selected', inject(function() {
			 expect(service.countSelected).toBeDefined();
			  
			 expect(service.countSelected(cvMock.technologies)).toBe(1);
			 
			 expect(service.countSelected(cvMock.engagements)).toBe(2);
			 
		  }));
		  
	  });
});	  
