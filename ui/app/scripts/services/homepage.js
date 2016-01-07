'use strict';
(function() {
	angular.module('patUI')
	.factory('HomePage', ['$resource', 'dataRoot', function($resource, dataRoot) {
		return $resource('data', {}, {
			featuredGroups: {
				method: 'GET',
				isArray: true,
				url: dataRoot + '/featuredgroups.json'
			}
		});
	}]);
})();
