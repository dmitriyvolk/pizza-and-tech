'use strict';
(function() {
	angular.module('patUI')
	.controller('UserCtrl', ['$scope', '$rootScope', '$routeParams', 'User', function($scope, $rootScope, $routeParams, User) {
		$rootScope.activeTab='users';
		var userId = $routeParams.id;
		$scope.user = User.get({userId: userId});
		$scope.groupsMemberOf = User.memberOf({userId: userId});
	}]);
})();