'use strict';
(function() {
	angular.module('patUI')
	.directive('patUser', function() {
		return {
			restrict: 'E',
			require: 'user',
			replace: true,
			scope: {
				user: '='
			},
			template: '<a href="#/users/{{user.id}}">{{user.displayName}}</a>'
		};
	})
	.directive('patComment', function() {
		return {
			restrict: 'E',
			require: 'comment',
			scope: {
				comment: '='
			},
			template: [
				'<p>{{comment.comment}}</p>',
				'<p><i><pat-user user="comment.author"></pat-user> on {{comment.timestamp | date}}</i></p>'
			].join('')
		};
	})
	.directive('rsvp', function() {
		return {
			restrict: 'E',
			require: 'data',
			scope: {
				data: '='
			},
			template: [
				'<p><pat-user user="data.member"></pat-user>',
				'<p ng-if="data.comment">{{data.comment}}</p>'
			].join('')
		};
	})
	.directive('patComments', function() {
		return {
			restrict: 'E',
			require: 'comments',
			scope: {
				comments: '='
			},
			templateUrl: 'views/directives/comments.html'
		};
	});

})();