'use strict';

describe('Test suite for the layout controller', function(){
  beforeEach(module('layout'));
  
  var controller, scope, window, cvResourceMock, navMock, layoutsResourceMock, pdfResourceMock;
  beforeEach(inject(function($rootScope, $controller, $window) {
      scope = $rootScope.$new();
      window = $window;
      
      cvResourceMock = { get : function () {  
    	  return { cv : { coverImage : 'http://istrue.com'} } }, create : function () {} };
      
      navMock = {
    		  mockState : { disabled : true }, 
    		  getState : function () { 
    			  return this.mockState; 
    		  }, onNext : function (callback) {} 
      };
      
      layoutsResourceMock = { get : function () { } };
      
      pdfResourceMock = { create : function () { } };
      
      spyOn(navMock, 'onNext');
      spyOn(cvResourceMock, 'get').andCallThrough();
      spyOn(navMock, 'getState').andCallThrough();
      spyOn(layoutsResourceMock, 'get');
      controller = $controller('LayoutController', { 
    	  $scope : scope, CvResource : cvResourceMock, Navigation : navMock, 
    	  LayoutsResource : layoutsResourceMock, PdfResource : pdfResourceMock });      
  }));
  
  it('should load the model and the layouts', inject(function() {
	  expect(cvResourceMock.get).toHaveBeenCalled();
	  expect(layoutsResourceMock.get).toHaveBeenCalled();
  }));
  
  it('should have the expected methods', inject(function() {
	  expect(scope.layoutSelected).toBeDefined();	  
  }));
  
  it('should enable next when layout is selected', inject(function() {
	  expect(navMock.getState().disabled).toEqual(true);
	  scope.layoutSelected();
	  expect(navMock.getState().disabled).toEqual(false);
  }));

});