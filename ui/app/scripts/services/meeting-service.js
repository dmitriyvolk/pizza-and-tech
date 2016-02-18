'use strict';

(function() {
	angular.module('patUI')
	.factory('Meeting', ['$resource', 'patConfig', 'Authentication', function($resource, patConfig, Authentication) {
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
			  url: commandSideServiceUrl + '/meetings/',
        headers: {
          'X-PIZZAANDTECH-USERID': Authentication.getCurrentUserId()
        }
			},
			deleteMeeting: {
			  method: 'DELETE',
			  url: commandSideServiceUrl + '/meetings/:meetingId'
			},
			newComment: {
			  method: 'POST',
			  url: commandSideServiceUrl + '/meetings/:meetingId/comments',
        headers: {
          'X-PIZZAANDTECH-USERID': Authentication.getCurrentUserId()
        }
			}
		});
	}]);
})();
