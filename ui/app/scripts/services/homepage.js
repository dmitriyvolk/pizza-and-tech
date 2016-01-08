'use strict';
(function() {
	angular.module('patUI')
	.factory('HomePage', ['$resource', 'patConfig', function($resource, patConfig) {
	  var dataRoot = patConfig.dataRoot;
		return $resource('data', {}, {
			featuredGroups: {
				method: 'GET',
				isArray: true,
				url: dataRoot + '/featuredgroups.json'
			}
		});
	}]);
})();
