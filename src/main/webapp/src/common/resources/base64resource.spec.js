'use strict';

describe('Test suite for the cover image resource', function(){
  beforeEach(module('resources.base64resource'));
  
  var resource, httpBackend;
  beforeEach(inject(function(Base64Resource, $httpBackend) {
      resource = Base64Resource;
      httpBackend = $httpBackend;
      httpBackend.when("POST").respond('Model POST:ed');      
  }));

  it('should have the expected functions', inject(function() {
	  expect(angular.isFunction(resource.create)).toBe(true);	  	  
  }));
  
  it('should do POST on create()', inject(function() {
	  var caller = { success : function (data) {} };
	  
	  spyOn(caller, 'success').andCallThrough();
	  
	  resource.create([{}], caller.success);
	  httpBackend.flush();
	  
	  expect(caller.success).toHaveBeenCalledWith('Model POST:ed');
  }));

});