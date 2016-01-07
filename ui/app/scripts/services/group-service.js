'use strict';

(function() {
angular.module('patUI')
.factory('Group', ['$resource', 'dataRoot', 'commandSideServiceUrl', function($resource, dataRoot, commandSideServiceUrl) {
	return $resource(dataRoot + '/groups/:groupId/group.json', {}, {
	  newGroup: {
	    url: commandSideServiceUrl + '/groups',
	    method: 'POST'
	  },
		futureMeetings: {
			method: 'GET',
			isArray: true,
			url: dataRoot + '/groups/:groupId/futuremeetings.json'
		},
		pastMeetings: {
			method: 'GET',
			isArray: true,
			url: dataRoot + '/groups/:groupId/pastmeetings.json'
		},
		meetings: {
		  method: 'GET',
		  isArray: true,
		  url: dataRoot + '/groups/:groupId/meetings.json'
		},
		members: {
			method: 'GET',
			isArray: true,
			url: dataRoot + '/groups/:groupId/members.json'
		},
		comments: {
			method: 'GET',
			isArray: true,
			url: dataRoot + '/groups/:groupId/comments.json'
		}
	});
}]);
})();
