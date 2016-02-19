'use strict';
(function() {
	angular.module('patUI')
	.factory('User', ['$resource', 'patConfig', function($resource, patConfig) {
	  var dataRoot = patConfig.dataRoot;
	  var authenticationServiceUrl = patConfig.authenticationServiceUrl;
		return $resource(dataRoot + '/users/:userId/user.json', {}, {
			memberOf: {
				method: 'GET',
				isArray: true,
				url: dataRoot + '/users/:userId/memberof.json'
			},
			meetings: {
			  method: 'GET',
			  isArray: true,
			  url: dataRoot + '/users/:userId/meetings.json'
			},
			list: {
			  method: 'GET',
			  isArray: true,
			  url: authenticationServiceUrl + '/users'
			}
		});
	}]);
})();
