'use strict';
(function() {
	angular.module('patUI')
	.factory('User', ['$resource', 'dataRoot', function($resource, dataRoot) {
		return $resource(dataRoot + '/users/:userId/user.json', {}, {
			memberOf: {
				method: 'GET',
				isArray: true,
				url: dataRoot + '/users/:userId/memberof.json'
			}
		});
	}]);
})();
