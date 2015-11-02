'use strict';

(function() {
angular.module('patUI')
.factory('Group', ['$resource', function($resource) {
	return $resource('data/groups/:groupId/group.json', {}, {
		futureEvents: {
			method: 'GET',
			isArray: true,
			url: 'data/groups/:groupId/futureevents.json'
		},
		pastEvents: {
			method: 'GET',
			isArray: true,
			url: 'data/groups/:groupId/pastevents.json'
		},
		members: {
			method: 'GET',
			isArray: true,
			url: 'data/groups/:groupId/members.json'
		},
		comments: {
			method: 'GET',
			isArray: true,
			url: 'data/groups/:groupId/comments.json'
		}
	});
}]);
})();