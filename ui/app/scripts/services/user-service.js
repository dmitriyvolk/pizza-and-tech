'use strict';
(function() {
	angular.module('patUI')
	.factory('User', ['$resource', function($resource) {
		return $resource('/data/users/:userId/user.json', {}, {
			memberOf: {
				method: 'GET',
				isArray: true,
				url: '/data/users/:userId/memberof.json'
			}
		});
	}]);
})();