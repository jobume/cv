'use strict';

describe('Test suite for the layouts resource', function(){
  beforeEach(module('resources.layoutsresource'));
  
  var resource, httpBackend, layoutsMock;
  beforeEach(inject(function(LayoutsResource, $httpBackend) {
      resource = LayoutsResource;
      httpBackend = $httpBackend;
      layoutsMock = ['layout1','layout2'];
      httpBackend.when("GET").respond(layoutsMock);
      httpBackend.when("POST").respond("POST!");
      httpBackend.when("PUT").respond("PUT!");
      httpBackend.when("DELETE").respond("DELETE!");
  }));

  it('should have the expected functions', inject(function() {
	  expect(angular.isFunction(resource.get)).toBe(true);
	  expect(angular.isFunction(resource.getById)).toBe(true);
	  expect(angular.isFunction(resource.update)).toBe(true);
	  expect(angular.isFunction(resource.deleteLayout)).toBe(true);
	  expect(angular.isFunction(resource.create)).toBe(true);
  }));
  
  it('should do GET on get()', inject(function() {
	  var caller = { success : function (data) {} };
	  
	  spyOn(caller, 'success').andCallThrough();
	  
	  httpBackend.expectGET();
	  resource.get(caller.success);
	  httpBackend.flush();
	  
	  expect(caller.success).toHaveBeenCalledWith(layoutsMock);
  }));
  
  it('should do POST on create()', inject(function() {
	  var caller = { success : function (data) {} };
	  
	  spyOn(caller, 'success').andCallThrough();
	  
	  httpBackend.expectPOST();
	  resource.create([], "testLayout", caller.success);
	  httpBackend.flush();
	  
	  expect(caller.success).toHaveBeenCalledWith("POST!");
  }));
  
  it('should do PUT on update()', inject(function() {
	  var caller = { success : function (data) {} };
	  
	  spyOn(caller, 'success').andCallThrough();
	  
	  httpBackend.expectPUT();
	  resource.update(1, {}, caller.success);
	  httpBackend.flush();
	  
	  expect(caller.success).toHaveBeenCalledWith("PUT!");
  }));
  
  it('should do DELETE on deleteLayout()', inject(function() {
	  var caller = { success : function (data) {} };
	  
	  spyOn(caller, 'success').andCallThrough();
	  
	  httpBackend.expectDELETE();
	  resource.deleteLayout(1, caller.success);
	  httpBackend.flush();
	  
	  expect(caller.success).toHaveBeenCalled();
  }));

});