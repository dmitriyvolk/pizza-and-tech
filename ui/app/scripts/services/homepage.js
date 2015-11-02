'use strict';
(function() {
	angular.module('patUI')
	.factory('HomePage', ['$resource', function($resource) {
		return $resource('/data', {}, {
			featuredGroups: {
				method: 'GET',
				isArray: true,
				url: '/data/featuredgroups.json'
			}
		});
	}]);
})();