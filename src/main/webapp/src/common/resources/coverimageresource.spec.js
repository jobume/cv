'use strict';

describe('Test suite for the cover image resource', function(){
  beforeEach(module('resources.coverimageresource'));
  
  var resource, httpBackend;
  beforeEach(inject(function(CoverImageResource, $httpBackend) {
      resource = CoverImageResource;
      httpBackend = $httpBackend;
      httpBackend.when("POST").respond('Model POST:ed');
      httpBackend.when("GET").respond('Model GET:ed');
  }));

  it('should have the expected functions', inject(function() {
	  expect(angular.isFunction(resource.create)).toBe(true);	  
	  expect(angular.isFunction(resource.get)).toBe(true);
  }));
  
  it('should do POST on create()', inject(function() {
	  var caller = { success : function (data) {} };
	  
	  spyOn(caller, 'success').andCallThrough();
	  
	  resource.create([{}], caller.success);
	  httpBackend.flush();
	  
	  expect(caller.success).toHaveBeenCalledWith('Model POST:ed');
  }));
  
  it('should do GET on get()', inject(function() {
	  var caller = { success : function (data) {} };
	  
	  spyOn(caller, 'success').andCallThrough();
	  
	  resource.get(caller.success);
	  httpBackend.flush();
	  
	  expect(caller.success).toHaveBeenCalledWith('Model GET:ed');
  }));

});