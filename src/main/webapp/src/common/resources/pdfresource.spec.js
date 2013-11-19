'use strict';

describe('Test suite for the pdf resource', function(){
  beforeEach(module('resources.pdfresource'));
  
  var resource, httpBackend;
  beforeEach(inject(function(PdfResource, $httpBackend) {
      resource = PdfResource;
      httpBackend = $httpBackend;
      httpBackend.when("POST").respond('Model POST:ed');            
  }));

  it('should have the expected functions', inject(function() {
	  expect(angular.isFunction(resource.create)).toBe(true);	  
  }));
  
  it('should do POST on create', inject(function() {
	  var caller = { success : function (data) {} };
	  
	  spyOn(caller, 'success').andCallThrough();
	  
	  resource.create([{}], 1, caller.success);
	  httpBackend.flush();
	  
	  expect(caller.success).toHaveBeenCalled();
  }));

});