'use strict';

angular.module('services.navigation', []).

factory('Navigation', ['$rootScope','$location', function($rootScope, $location) {
	
	/**
	 * 	Navigation steps. Each step consists of: 
	 * 
	 *  label 		: The text shown on the bread crumb
	 *  path  		: The path to next step
	 *  buttonLabel	: Label on the next button
	 *  disabled	: True if the next button should be disabled
	 */
	var steps = [
 		{label:"Person >", path:"/laddaupp", buttonLabel: "Nästa", disabled: true},
 		{label:"Anpassning >", path:"/anpassa", buttonLabel: "Nästa", disabled: false},
 		{label:"Uppdrag >", path:"/uppdrag", buttonLabel: "Nästa", disabled: false},
 		{label:"Teknik >", path:"/teknik", buttonLabel: "Nästa", disabled: false},
 		{label:"Egenskaper >", path:"/egenskaper", buttonLabel: "Nästa", disabled: false},
 		{label:"Ordmoln >", path:"/ordmoln", buttonLabel: "Nästa", disabled: false},
 		{label:"Bilder ", path:"/bilder", buttonLabel: "Skapa pdf", disabled: true} //,
 		// {label:"Layout", path:"/layout", buttonLabel: "Skapa pdf", disabled: true}
 	];
	
	/**
	 * State of navigation.
	 * 
	 * steps			: navigation steps (see above).
	 * selectedIndex	: current selected step.
	 * disabled			: if the next button is disabled
	 * admin			: if current page is admin page
	 * callback			: callback function to be executed on next()
	 */
	var state = { 
			steps : steps,
			selectedIndex : 0,
			disabled : true,
			admin : false,
			callback : null
	};
	
	/**
	 * When route change, this will set current navigation to correct step as well as 
	 * check if this is an admin route.
	 */
	$rootScope.$on('$routeChangeSuccess', function(event, current){
		var path = $location.path();
		state.admin = (path.indexOf("admin") !== -1);		
		for(var i=0; i< steps.length; i++) {
			if(steps[i].path == path) {
				state.selectedIndex = i;
				state.callback = null;
				state.disabled = steps[state.selectedIndex].disabled;
				break;
			}
		}
	});
	
	var serviceInstance = {
		next : function() {
			// Immediately disable next-button.
			state.disabled = true;
			if(state.callback != null) {
				state.callback(this.success, state);
			} else {
				this.success();
			}
		},
		success : function() {
			if(state.selectedIndex < steps.length-1) {
				state.selectedIndex++;
				var navTo = steps[state.selectedIndex].path;
				state.callback = null;
				state.disabled = steps[state.selectedIndex].disabled;
				$location.path(navTo);
			}
		},
		previous : function() {
			if(state.selectedIndex > 0) {
				state.selectedIndex--;
				var navTo = steps[state.selectedIndex].path;
				state.callback = null;
				state.disabled = steps[state.selectedIndex].disabled;
				$location.path(navTo);
			}
		},
		getState : function() {
			return state;
		},
		
		/**
		 * Sets a callback to be executed when navigating to next step.
		 * 
		 * @param callback: 	The callback will be called with one argument (success) that is a function
		 * 						that will navigate to the next step in the navigation.
		 * 						function (success) {
		 * 							... perform business ...
		 * 							success();
		 * 						}			 
		 */
		onNext : function(callback) {
			state.callback = callback;
		},
		
		/**
		 * Returns true if current step is the last step.
		 */
		isLast : function() {
			alert("Hej");
			return (state.selectedIndex == steps.length-1);
		}
	};
	
	return serviceInstance;
} ]);