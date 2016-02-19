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
			template: '<a href="#/users/{{user.id}}">{{user.name}}</a>'
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
				'<p>{{comment.text}}</p>',
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
	})
	.directive('userSelect', function() {
	  return {
	    restrict: 'E',
	    scope: {},
	    template: [
	      '<select ng-model="currentUser.id">',
	      '  <option ng-repeat="user in users" value="{{user.id}}">{{user.name}}</option>',
	      '</select>'
      ].join(''),
	    controller: function($scope, $rootScope, User) {
	      $scope.users = User.list();
	      $scope.selectUser = function(user) {
	        $rootScope.currentUser = user;
	      };
	    }
	  };
	});

})();
