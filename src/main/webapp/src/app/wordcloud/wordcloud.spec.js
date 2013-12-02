'use strict';

describe('Test suite for the wordcloud controller', function(){
  beforeEach(module('wordcloud'));
  
  var controller, scope, cvResourceMock, navMock;
  beforeEach(inject(function($rootScope, $controller) {
      scope = $rootScope.$new();
      cvResourceMock = { get : function () {}, create : function () {}, update : function () {} };
      navMock = {
    		  mockState : { disabled : true }, 
    		  getState : function () { 
    			  return this.mockState; 
    		  }, onNext : function (callback) {} };
      spyOn(navMock, 'onNext');
      spyOn(cvResourceMock, 'get');
      spyOn(navMock, 'getState').andCallThrough();
      controller = $controller('WordCloudController', { 
    	  $scope : scope, CvResource : cvResourceMock });      
  }));

  it('should load the model', inject(function() {
	  expect(cvResourceMock.get).toHaveBeenCalled();
  }));
  
  it('should define addTag() and deleteTag()', inject(function() {
	  expect(scope.addTag).toBeDefined();
	  expect(scope.deleteTag).toBeDefined();	  
  }));
  
  it('should add tag to model on addTag()', inject(function() {
	  scope.tagName = "tagOne";
	  scope.model = { cv : {} };
	  scope.model.cv = { tags : [] };
	  scope.addTag();
	  expect(scope.model.cv.tags[0].tagName).toEqual("tagOne");
	  scope.tagName = "tagTwo";
	  scope.addTag();
	  expect(scope.model.cv.tags[1].tagName).toEqual("tagTwo");
  }));
  
  it('should delete tag from model on deleteTag()', inject(function() {
	  scope.tagName = "tagOne";
	  scope.model = { cv : {} };
	  scope.model.cv = { tags : [] };
	  scope.addTag();
	  expect(scope.model.cv.tags[0].tagName).toEqual("tagOne");
	  
	  scope.deleteTag();
	  expect(scope.model.cv.tags.length).toEqual(0);
  }));
  
  it('should not add duplicates', inject(function() {
	  scope.tagName = "tagOne";
	  scope.model = { cv : {} };
	  scope.model.cv = { tags : [] };
	  scope.addTag();
	  
	  scope.tagName = "tagOne";
	  scope.addTag();
	  
	  expect(scope.model.cv.tags.length).toEqual(1);
  }));

});