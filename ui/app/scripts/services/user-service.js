'use strict';
(function() {
	angular.module('patUI')
	.factory('User', ['$resource', 'patConfig', function($resource, patConfig) {
	  var dataRoot = patConfig.dataRoot;
		return $resource(dataRoot + '/users/:userId/user.json', {}, {
			memberOf: {
				method: 'GET',
				isArray: true,
				url: dataRoot + '/users/:userId/memberof.json'
			}
		});
	}]);
})();
