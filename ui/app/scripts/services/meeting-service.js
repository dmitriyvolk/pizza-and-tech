'use strict';

(function() {
	angular.module('patUI')
	.factory('Meeting', ['$resource', 'patConfig', function($resource, patConfig) {
	  var dataRoot = patConfig.dataRoot;
	  var commandSideServiceUrl = patConfig.commandSideServiceUrl;
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
			},
			deleteMeeting: {
			  method: 'DELETE',
			  url: commandSideServiceUrl + '/meetings/:meetingId'
			},
			newComment: {
			  method: 'POST',
			  url: commandSideServiceUrl + '/meetings/:meetingId/comments'
			}
		});
	}]);
})();
