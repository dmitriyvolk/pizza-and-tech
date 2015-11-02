'use strict';

(function() {
	angular.module('patUI')
	.factory('Event', ['$resource', function($resource) {
		return $resource('data/events/:eventId/event.json', {}, {
			rsvps: {
				method: 'GET',
				url: 'data/events/:eventId/rsvp.json'
			},
			comments: {
				method: 'GET',
				isArray: true,
				url: 'data/events/:eventId/comments.json'
			}
		});
	}]);
})();
