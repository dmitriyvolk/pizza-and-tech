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
			},
			meetings: {
			  method: 'GET',
			  isArray: true,
			  url: dataRoot + '/users/:userId/meetings.json'
			}
		});
	}])
	.factory('CurrentUser', ['$resource', 'patConfig', 'User', function($resource, patConfig, User) {
	  var commandSideServiceUrl = patConfig.commandSideServiceUrl;
	  var currentUser = $resource(commandSideServiceUrl + '/currentuser', {}).get();
	  return {
	    get: function() { return currentUser; },
	    memberOf: function() { return currentUser.$promise.then(function(cU) { return User.memberOf({userId: cU.userId.entityId}); }); },
	    meetings: function() { return currentUser.$promise.then(function(cU) { return User.meetings({userId: cU.userId.entityId}); }); }
	  };
	}]);
})();
