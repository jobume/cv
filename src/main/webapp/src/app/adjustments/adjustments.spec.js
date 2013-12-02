'use strict';

describe('Test suite for the adjustments module', function(){
	  beforeEach(module('adjustments'));
	  
	  var cvMock;
	  beforeEach(inject(function() {
	      cvMock = { 
	    		  description : 'Hej',
	    		  profile : { title : {} },
	    		  technologies : [{ important : false },{ important : true },{ important : false }], 
	    		  engagements : [ { important : false}, { important : true}, { important : true} ] } ;	      
	  }));
	  
	  describe('Test suite for the adjustments controller', function(){
		  
		  var controller, scope, cvResourceMock, serviceMock, navMock, state;
		  beforeEach(inject(function($rootScope, $controller) {
		      scope = $rootScope.$new();
		      cvResourceMock = { get : function () { }, create : function () {} };
		      
		      state = { disabled : true, callback : null };
		      
		      navMock = {
		    		  success : function () { console.log('Success') },
		    		  next : function () { state.callback(this.success, state)} , 
		    		  onNext : function (success) {
		    			  state.callback = success;
		    		  }
		      };
		
		      serviceMock = { selectAll : function () {}, countSelected : function () { return 242; } };
		      
		      spyOn(cvResourceMock, 'get').andReturn( { cv : cvMock } );
		      spyOn(navMock, 'onNext').andCallThrough();
		      spyOn(navMock, 'next').andCallThrough();
		      spyOn(navMock, 'success').andCallThrough();
		      
		      controller = $controller('AdjustmentsController', { 
		    	  $scope : scope, CvResource : cvResourceMock, SelectionService : serviceMock, Navigation : navMock });
		      
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
		  
		  it('should not call success when profile is not set', inject(function() {
			  navMock.next();
			  expect(navMock.success).not.toHaveBeenCalled();
		  }));
		  
		  it('should call success when profile is set', inject(function() {
			  cvMock.profile.title = "Title";
			  navMock.next();
			  expect(navMock.success).toHaveBeenCalled();
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
