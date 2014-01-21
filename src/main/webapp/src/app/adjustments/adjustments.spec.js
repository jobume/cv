'use strict';

describe('Test suite for the adjustments module', function(){
	  beforeEach(module('adjustments'));
	  
	  var cvMock;
	  beforeEach(inject(function() {
	      cvMock = { 
	    		  description : 'Hej',
	    		  profile : { title : {} },
	    		  technologies : [{ important : false },{ important : true },{ important : false }], 
	    		  engagements : [ { important : false}, { important : true}, { important : true} ] } ;	      
	  }));
	  
	  describe('Test suite for the adjustments controller', function(){
		  
		  var controller, scope, cvResourceMock, navMock, state;
		  beforeEach(inject(function($rootScope, $controller) {
		      scope = $rootScope.$new();
		      cvResourceMock = { get : function () { }, create : function () {} };
		      
		      state = { disabled : true, callback : null };
		      
		      navMock = {
		    		  success : function () { console.log('Success') },
		    		  next : function () { state.callback(this.success, state)} , 
		    		  onNext : function (success) {
		    			  state.callback = success;
		    		  }
		      };
		      
		      spyOn(cvResourceMock, 'get').andReturn( { cv : cvMock } );
		      spyOn(navMock, 'onNext').andCallThrough();
		      spyOn(navMock, 'next').andCallThrough();
		      spyOn(navMock, 'success').andCallThrough();
		      
		      
		      controller = $controller('AdjustmentsController', { 
		    	  $scope : scope, CvResource : cvResourceMock, Navigation : navMock });
		      
		  }));
		  
		  it('should load the model', inject(function() {
			  expect(cvResourceMock.get).toHaveBeenCalled();
		  }));
		  
		  
		  it('should not call success when profile is not set', inject(function() {
			  scope.adjustmentForm = { $valid : false };
			  navMock.next();
			  expect(navMock.success).not.toHaveBeenCalled();
		  }));
		  
		  it('should call success when profile is set', inject(function() {
			  scope.adjustmentForm = { $valid : true };
			  cvMock.profile.title = "Title";
			  navMock.next();
			  expect(navMock.success).toHaveBeenCalled();
		  }));
		  
	  });
	  
	  
});	  
