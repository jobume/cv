'use strict';

describe('Test suite for the qualities controller', function(){
  beforeEach(module('qualities'));
  
  var controller, scope, cvResourceMock, navMock;
  beforeEach(inject(function($rootScope, $controller) {
      scope = $rootScope.$new();
      cvResourceMock = { get : function () {}, create : function () {}, update : function () {} };
      navMock = {
    		  mockState : { disabled : true }, 
    		  getState : function () { 
    			  return this.mockState; 
    		  }, onNext : function (callback) {} };
      spyOn(navMock, 'onNext');
      spyOn(cvResourceMock, 'get');
      spyOn(navMock, 'getState').andCallThrough();
      controller = $controller('QualitiesController', { 
    	  $scope : scope, CvResource : cvResourceMock, Navigation : navMock});      
  }));

  
  it('should set a callback on navigation', inject(function() {
	  expect(navMock.onNext).toHaveBeenCalled();
  }));
  
  it('should load the model', inject(function() {
	  expect(cvResourceMock.get).toHaveBeenCalled();
  }));
  
  it('should define addQuality() and deleteQuality()', inject(function() {
	  expect(scope.addQuality).toBeDefined();
	  expect(scope.deleteQuality).toBeDefined();	  
  }));
  
  it('should add quality to model on addQuality()', inject(function() {
	  expect(scope.addQuality).toBeDefined();
	  scope.qualityName = "One";
	  scope.model = { cv : {} };
	  scope.model.cv = { personalQualities : [] };
	  scope.addQuality();
	  expect(scope.model.cv.personalQualities[0]).toEqual("One");
	  scope.qualityName = "Two";
	  scope.addQuality();
	  expect(scope.model.cv.personalQualities[1]).toEqual("Two");
  }));
  
  it('should not add more than 5 qualities to model on addQuality()', inject(function() {
	  expect(scope.addQuality).toBeDefined();
	  scope.qualityName = "One";
	  scope.model = { cv : {} };
	  scope.model.cv = { personalQualities : [] };
	  scope.addQuality();
	  scope.qualityName = "Two";
	  scope.addQuality();
	  scope.qualityName = "Three";
	  scope.addQuality();
	  scope.qualityName = "Four";
	  scope.addQuality();
	  scope.qualityName = "Five";
	  scope.addQuality();
	  scope.qualityName = "Six";
	  scope.addQuality();
	  
	  expect(scope.model.cv.personalQualities.length).toEqual(5);
  }));
  
  it('should delete quality from model on deleteQuality()', inject(function() {
	  expect(scope.addQuality).toBeDefined();
	  scope.qualityName = "One";
	  scope.model = { cv : {} };
	  scope.model.cv = { personalQualities : [] };
	  scope.addQuality();
	  expect(scope.model.cv.personalQualities[0]).toEqual("One");
	  
	  scope.deleteQuality();
	  expect(scope.model.cv.personalQualities.length).toEqual(0);
  }));
  
  it('should not add duplicate qualities', inject(function() {
	  expect(scope.addQuality).toBeDefined();
	  scope.model = { cv : {} };
	  scope.model.cv = { personalQualities : [] };
	  
	  scope.qualityName = "One";
	  scope.addQuality();
	  scope.qualityName = "One";
	  scope.addQuality();
	  
	  expect(scope.model.cv.personalQualities.length).toEqual(1);
  }));

});