'use strict';

describe('Test suite for the cv resource', function(){
  beforeEach(module('resources.cvresource'));
  
  var resource, httpBackend;
  beforeEach(inject(function(CvResource, $httpBackend) {
      resource = CvResource;
      httpBackend = $httpBackend;
      httpBackend.when("POST").respond('Model POST:ed');
      httpBackend.when("PUT").respond('Model PUT:ed');      
  }));

  it('should have the expected functions', inject(function() {
	  expect(angular.isFunction(resource.create)).toBe(true);
	  expect(angular.isFunction(resource.get)).toBe(true);
	  expect(angular.isFunction(resource.update)).toBe(true);
  }));
  
  it('should do POST on create', inject(function() {
	  resource.create([{}], function(){});
	  httpBackend.flush();
	  expect(resource.get().cv).toBe('Model POST:ed');
  }));
  
  it('should do PUT on update', inject(function() {
	  resource.update(function(){});
	  httpBackend.flush();
	  expect(resource.get().cv).toBe('Model PUT:ed');
  }));

});