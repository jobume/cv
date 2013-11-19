'use strict';

describe('Test suite for the upload rtf controller', function(){
  beforeEach(module('uploadrtf'));
  
  var controller, scope, cvMock, navMock;
  beforeEach(inject(function($rootScope, $controller) {
      scope = $rootScope.$new();
      cvMock = { get : function () {}, create : function () {} };
      navMock = {
    		  mockState : { disabled : true }, 
    		  getState : function () { 
    			  return this.mockState; 
    		  }, onNext : function (callback) {} };
      spyOn(navMock, 'onNext');
      spyOn(cvMock, 'get');
      spyOn(navMock, 'getState').andCallThrough();
      controller = $controller('UploadRtfController', { 
    	  $scope : scope, CvResource : cvMock, Navigation : navMock});      
  }));

  
  it('should set a callback on navigation', inject(function() {
	  expect(navMock.onNext).toHaveBeenCalled();
  }));
  
  it('should load the model', inject(function() {
	  expect(cvMock.get).toHaveBeenCalled();
  }));
  
  it('should set disabled to false on prepare', inject(function() {
	  expect(scope.prepare).toBeDefined();
	  scope.prepare();
	  expect(navMock.getState().disabled).toEqual(false);
  }));

});