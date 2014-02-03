'use strict';

describe('Test suite for the portrait resource', function(){
  beforeEach(module('resources.portraitresource'));
  
  var resource, httpBackend;
  beforeEach(inject(function(PortraitResource, $httpBackend) {
      resource = PortraitResource;
      httpBackend = $httpBackend;
      httpBackend.when("POST").respond('Model POST:ed');            
  }));

  it('should have the expected functions', inject(function() {
	  expect(angular.isFunction(resource.create)).toBe(true);	  
  }));
  
  it('should do POST on create', inject(function() {
	  var caller = { success : function (data) {} };
	  
	  spyOn(caller, 'success').andCallThrough();
	  
	  resource.create([{}], 'name', caller.success);
	  httpBackend.flush();
	  
	  expect(caller.success).toHaveBeenCalledWith('Model POST:ed');
  }));

});