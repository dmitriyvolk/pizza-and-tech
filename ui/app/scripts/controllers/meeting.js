'use strict';

/**
 * @ngdoc function
 * @name patUI.controller:MeetingCtrl
 * @description
 * # MeetingCtrl
 * Controller hanlding a single meeting.
 */
(function(){
	angular.module('patUI')
	.controller('MeetingCtrl', ['$rootScope', '$scope', '$routeParams', 'Meeting', 'Group', function ($rootScope, $scope, $routeParams, Meeting, Group) {
		$rootScope.activeTab = 'meetings';
		var meetingId = $routeParams.id;
//		$scope.meeting = Meeting.get({meetingId: meetingId});
//		$scope.group = Group.get({groupId: $scope.meeting.groupId});
    Meeting.get({meetingId: meetingId}, function(data) {
      $scope.meeting = data;
    }).$promise.then(function(meeting){
      console.dir(meeting);
      Group.get({groupId: meeting.groupId}, function(data){
        $scope.group = data;
      });
    });
		$scope.rsvps = Meeting.rsvps({meetingId: meetingId});
		$scope.comments = Meeting.comments({meetingId: meetingId});

		$scope.isDescriptionCollapsed = true;
		$scope.toggleIsDescriptionCollapsed = function() {
			$scope.isDescriptionCollapsed = !$scope.isDescriptionCollapsed;
		};
	}]);
})();
