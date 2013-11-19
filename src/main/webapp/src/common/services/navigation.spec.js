'use strict';

describe('Test suite for the navigation service', function(){
  beforeEach(module('services.navigation'));
  
  var service, location;
  beforeEach(inject(function(Navigation, $location) {
      service = Navigation;
      location = $location;
  }));
  
  it('should have the expected functions', inject(function() {
	  expect(angular.isFunction(service.next)).toBe(true);
	  expect(angular.isFunction(service.previous)).toBe(true);
	  expect(angular.isFunction(service.getState)).toBe(true);
	  expect(angular.isFunction(service.onNext)).toBe(true);
  }));

  it('should be initialized correctly', inject(function() {
	  var selectedIndex = service.getState().selectedIndex;
	  expect(selectedIndex).toBe(0);	  
	  expect(service.getState().steps[selectedIndex].label).toBe('Person >');
	  expect(service.getState().steps[selectedIndex].path).toBe('/laddaupp');
	  expect(service.getState().steps[selectedIndex].buttonLabel).toBe('Nästa');
	  expect(service.getState().steps[selectedIndex].disabled).toBe(true);	  
  }));
  
  it('should do next step in navigation', inject(function() {
	  expect(service.getState().selectedIndex).toBe(0);
	  spyOn(location, 'path');
	  
	  service.next();
	  
	  expect(location.path).toHaveBeenCalledWith('/anpassa');
	  var selectedIndex = service.getState().selectedIndex;
	  expect(selectedIndex).toBe(1);	   
	  expect(service.getState().steps[selectedIndex].label).toBe('Anpassning >');
	  expect(service.getState().steps[selectedIndex].path).toBe('/anpassa');
	  expect(service.getState().steps[selectedIndex].buttonLabel).toBe('Nästa');
	  expect(service.getState().steps[selectedIndex].disabled).toBe(false);
  }));
  
  it('should not proceed when callback does not run success', inject(function() {
	  expect(service.getState().selectedIndex).toBe(0);
	  var mockCallBack = {
			  callback : function(success) {
				  // do nothing
			  }
	  }
	  spyOn(mockCallBack, 'callback').andCallThrough();
	  
	  service.onNext(mockCallBack.callback);
	  service.next();
	  
	  expect(mockCallBack.callback).toHaveBeenCalled();
	  expect(service.getState().selectedIndex).toBe(0);
  }));
  
  it('should proceed when callback runs success', inject(function() {
	  expect(service.getState().selectedIndex).toBe(0);
	  var mockCallBack = {
			  callback : function(success) {
				  success();
			  }
	  }
	  spyOn(mockCallBack, 'callback').andCallThrough();
	  
	  service.onNext(mockCallBack.callback);
	  service.next();
	  
	  expect(mockCallBack.callback).toHaveBeenCalled();
	  expect(service.getState().selectedIndex).toBe(1);
  }));
  
  it('should be able to go back', inject(function() {
	  expect(service.getState().selectedIndex).toBe(0);
	  
	  service.next();
	  
	  expect(service.getState().selectedIndex).toBe(1);
	  
	  service.previous();
	  
	  expect(service.getState().selectedIndex).toBe(0);
  }));
  
  it('should not go back beyond first', inject(function() {
	  expect(service.getState().selectedIndex).toBe(0);
	  
	  service.previous();
	  
	  expect(service.getState().selectedIndex).toBe(0);
  }));
  
  it('should not go beyond last', inject(function() {
	  var steps = service.getState().steps.length;
	  for(var i=0; i< steps; i++) {
		  service.next();		  
	  }
	  expect(service.getState().selectedIndex).toBe(service.getState().steps.length-1);
  }));
});