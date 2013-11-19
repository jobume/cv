'use strict';

describe('Test suite for the admin feature', function(){
  beforeEach(module('admin'));
  
  var location, navMock, layoutsResourceMock;
  beforeEach(inject(function($location) {
	  location = $location;
	  navMock = {
    		  mockState : { 
    			  steps : [ { path : 'path0' }, { path : 'path1'} ], 
    			  selectedIndex : 1, 
    			  disabled : true 
    		  }, 
    		  getState : function () { return this.mockState; }, 
    		  onNext : function (callback) {} 
      };
      
      layoutsResourceMock = { get : function () { }, deleteLayout : function (id) {}, 
    		  getById : function () {}, update : function(id, layout, success) {}, create : function () {} };
      
      spyOn(layoutsResourceMock, 'get');
  }));
  
  describe('Tests for the list controller', function() {
	  var controller, scope;
	  beforeEach(inject(function($rootScope, $controller) {
	      scope = $rootScope.$new();
	      controller = $controller('LayoutsController', { 
	    	  $scope : scope, $location : location,  
	    	  LayoutsResource : layoutsResourceMock, Navigation : navMock });      
	  }));
	  
	  it('should have the expected methods', inject(function() {
		  expect(scope.editLayout).toBeDefined();
		  expect(scope.createLayout).toBeDefined();
		  expect(scope.deleteLayout).toBeDefined();
	  }));
	  
	  it('should load layouts', inject(function() {
		  expect(layoutsResourceMock.get).toHaveBeenCalled();	  
	  }));
	  
	  it('should navigate to admin/ny on create', inject(function() {
		  spyOn(location, 'path')
		  scope.createLayout();
		  expect(location.path).toHaveBeenCalledWith("/admin/ny");
	  }));
	  
	  it('should navigate to admin/layouter/:id on editLayout', inject(function() {
		  spyOn(location, 'path')
		  scope.editLayout(1);
		  expect(location.path).toHaveBeenCalledWith("/admin/layouter/1");
	  }));
	  
	  it('should not delete when prompt is incorrect', inject(function() {
		  spyOn(window, 'prompt').andReturn("sdf");
		  spyOn(layoutsResourceMock, 'deleteLayout');
		  scope.deleteLayout(1);
		  expect(layoutsResourceMock.deleteLayout).not.toHaveBeenCalled();
	  }));
	  
	  it('should delete layout when prompt is correct', inject(function() {
		  spyOn(window, 'prompt').andReturn("RADERA");
		  spyOn(layoutsResourceMock, 'deleteLayout');
		  scope.deleteLayout(1);
		  expect(layoutsResourceMock.deleteLayout).toHaveBeenCalled();
	  }));		  
  });
  
  describe('Tests for the edit controller', function() {
	  var controller, scope, routeParams;
	  beforeEach(inject(function($rootScope, $controller, $routeParams) {
	      scope = $rootScope.$new();
	      routeParams = $routeParams;
	      routeParams.id = 1;
	      spyOn(layoutsResourceMock, 'getById');
	      controller = $controller('EditLayoutController', { 
	    	  $scope : scope, $location : location, $routeParams : routeParams, 
	    	  LayoutsResource : layoutsResourceMock, Navigation : navMock });      
	  }));
	  
	  it('should have the expected methods', inject(function() {
		  expect(scope.update).toBeDefined();		  
	  }));
	  

	  it('should load specified layout', inject(function() {
		  expect(layoutsResourceMock.getById).toHaveBeenCalledWith(1, jasmine.any(Function));		  
	  }));
	  
	  it('should update on update', inject(function() {
		  scope.layout = "Layout";
		  spyOn(layoutsResourceMock, 'update');
		  scope.update();		  
		  expect(layoutsResourceMock.update).toHaveBeenCalledWith(1, JSON.stringify("Layout"), jasmine.any(Function));		  
	  }));	  
  });
  
  describe('Tests for the new controller', function() {
	  var controller, scope, routeParams;
	  beforeEach(inject(function($rootScope, $controller) {
	      scope = $rootScope.$new();
	      spyOn(layoutsResourceMock, 'getById');
	      controller = $controller('NewLayoutController', { 
	    	  $scope : scope, $location : location, 
	    	  LayoutsResource : layoutsResourceMock, Navigation : navMock });      
	  }));
	  
	  it('should have the expected methods', inject(function() {
		  expect(scope.prepare).toBeDefined();
		  expect(scope.createLayout).toBeDefined();
	  }));
	  
	  it('should create resource on create', inject(function() {
		  spyOn(layoutsResourceMock, 'create');
		  scope.createLayout();
		  expect(layoutsResourceMock.create).toHaveBeenCalled();
	  }));
	  
  });
  
  describe('Tests for the base64 controller', function() {
	  var controller, scope, base64ResourceMock;
	  beforeEach(inject(function($rootScope, $controller) {
	      scope = $rootScope.$new();
	      base64ResourceMock = { create : function() {} };
	      controller = $controller('Base64Controller', { 
	    	  $scope : scope, $location : location, 
	    	  Base64Resource : base64ResourceMock });      
	  }));
	  
	  it('should have the expected methods', inject(function() {
		  expect(scope.prepare).toBeDefined();
		  expect(scope.base64Encode).toBeDefined();
	  }));
	  
	  it('should create resource on create', inject(function() {
		  spyOn(base64ResourceMock, 'create');
		  scope.base64Encode();
		  expect(base64ResourceMock.create).toHaveBeenCalled();
	  }));
	  
  });
  
  describe('Tests for the coverimage controller', function() {
	  var controller, scope, coverImageResourceMock;
	  beforeEach(inject(function($rootScope, $controller) {
	      scope = $rootScope.$new();
	      coverImageResourceMock = { 
	    		  create : function(files, success) {
	    			  success();
	    		  }, 
	    		  get : function() {}, 
	    		  deleteCoverImage : function(id) {} };
	      spyOn(coverImageResourceMock, 'get');
	      controller = $controller('CoverImageController', { 
	    	  $scope : scope, $location : location, 
	    	  CoverImageResource : coverImageResourceMock });      
	  }));
	  
	  it('should have the expected methods', inject(function() {
		  expect(scope.prepare).toBeDefined();
		  expect(scope.upload).toBeDefined();
		  expect(scope.deleteCoverImage).toBeDefined();
	  }));
	  
	  it('should have the expected methods', inject(function() {
		  expect(coverImageResourceMock.get).toHaveBeenCalled();		
	  }));
	  
	  it('should create resource on upload', inject(function() {
		  spyOn(coverImageResourceMock, 'create').andCallThrough();
		  spyOn(location, 'path');
		  scope.files = {};
		  scope.upload();		  
		  expect(coverImageResourceMock.create).toHaveBeenCalled();
		  expect(coverImageResourceMock.get).toHaveBeenCalled();		  
	  }));
	  
	  it('should create resource on upload', inject(function() {
		  spyOn(coverImageResourceMock, 'deleteCoverImage').andCallThrough();
		  
		  scope.deleteCoverImage('id');
		  
		  expect(coverImageResourceMock.deleteCoverImage).toHaveBeenCalled();
		  expect(coverImageResourceMock.get).toHaveBeenCalled();		  
	  }));
	  
  });

});