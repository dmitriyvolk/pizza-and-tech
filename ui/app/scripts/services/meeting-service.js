'use strict';

(function() {
	angular.module('patUI')
	.factory('Meeting', ['$resource', 'dataRoot', 'commandSideServiceUrl', function($resource, dataRoot, commandSideServiceUrl) {
		return $resource(dataRoot + '/meetings/:meetingId/meeting.json', {}, {
			rsvps: {
				method: 'GET',
				url: dataRoot + '/meetings/:meetingId/rsvp.json'
			},
			comments: {
				method: 'GET',
				isArray: true,
				url: dataRoot + '/meetings/:meetingId/comments.json'
			},
			newMeeting: {
			  method: 'POST',
			  url: commandSideServiceUrl + '/meetings/'
			}
		});
	}]);
})();
