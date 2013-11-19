'use strict';

describe('Test suite for the select images controller', function(){
  beforeEach(module('selectimages'));
  
  var controller, scope, cvResourceMock, navMock, portraitResourceMock, coverImageResourceMock;
  beforeEach(inject(function($rootScope, $controller) {
      scope = $rootScope.$new();
      
      cvResourceMock = { get : function () {  
    	  return { cv : { coverImage : 'http://istrue.com'} } }, create : function () {} };
      navMock = {
    		  mockState : { disabled : true }, 
    		  getState : function () { 
    			  return this.mockState; 
    		  }, onNext : function (callback) {} };
      portraitResourceMock = { create : function () {} };
      coverImageResourceMock = { create : function () {}, 
    		  get : function () { 
    			 
    	      }
      };
      spyOn(navMock, 'onNext');
      spyOn(cvResourceMock, 'get').andCallThrough();
      spyOn(navMock, 'getState').andCallThrough();
      spyOn(coverImageResourceMock, 'get');
      controller = $controller('SelectImagesController', { 
    	  $scope : scope, CvResource : cvResourceMock, Navigation : navMock, 
    	  PortraitResource : portraitResourceMock, CoverImageResource : coverImageResourceMock });      
  }));
  
  it('should load the model and the cover images', inject(function() {
	  expect(cvResourceMock.get).toHaveBeenCalled();
	  expect(coverImageResourceMock.get).toHaveBeenCalled();
  }));
  
  it('should have the expected methods', inject(function() {
	  expect(scope.uploadFile).toBeDefined();	  
  }));
  
  it('should enable next when url is set', inject(function() {
	  expect(navMock.getState().disabled).toEqual(false);
  }));

});