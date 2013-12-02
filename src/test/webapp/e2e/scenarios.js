'use strict';

/* http://docs.angularjs.org/guide/dev_guide.e2e-testing */

describe('Cv Converter App', function() {

  it('should redirect index.html to index.html#/laddaupp', function() {
    browser().navigateTo('main/webapp/index.html');
    expect(browser().location().url()).toBe('/laddaupp');
  });
    
  describe('Anpassa view', function() {
	
	beforeEach(function() {
      browser().navigateTo('main/webapp/index.html#/anpassa');
    });
	
	
	it('should have Tillbaka and Nästa buttons', function() {
      expect(element('.right input').attr('value')).toBe('Nästa');	  
    });
  
  });
  
});
