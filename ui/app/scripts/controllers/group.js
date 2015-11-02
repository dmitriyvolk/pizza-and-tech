'use strict';

/**
 * @ngdoc function
 * @name patUI.controller:GroupCtrl
 * @description
 * # GroupCtrl
 * Controller hanlding showing a single group.
 */
angular.module('patUI')
.controller('GroupCtrl', ['$rootScope', '$scope', '$routeParams', 'Group', function ($rootScope, $scope, $routeParams, Group) {
	$rootScope.activeTab = 'group1';
	var groupId = $routeParams.id;
	$scope.group = Group.get({groupId: groupId});
	$scope.futureEvents = Group.futureEvents({groupId: groupId});
	$scope.pastEvents = Group.pastEvents({groupId: groupId});
	$scope.members = Group.members({groupId: groupId});
	$scope.comments = Group.comments({groupId: groupId});
}]);
