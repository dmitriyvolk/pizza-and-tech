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
	.controller('MeetingCtrl', ['$rootScope', '$scope', '$routeParams', '$location', 'Meeting', 'Group', function ($rootScope, $scope, $routeParams, $location, Meeting, Group) {
		$rootScope.activeTab = 'meetings';
		var meetingId = $routeParams.id;

    function load() {

      Meeting
        .get({meetingId: meetingId}, function(data) {
          $scope.meeting = data;
        })
        .$promise
        .then(function(meeting) {
          Group.get({groupId: meeting.groupId}, function(data){
            $scope.group = data;
          });
        });

      $scope.rsvps = Meeting.rsvps({meetingId: meetingId});
      loadComments();
		}

		function loadComments() {
		  $scope.comments = Meeting.comments({meetingId: meetingId});
		}

		$scope.isDescriptionCollapsed = true;
		$scope.toggleIsDescriptionCollapsed = function() {
			$scope.isDescriptionCollapsed = !$scope.isDescriptionCollapsed;
		};

		$scope.deleteMeeting = function() {
      Meeting
        .deleteMeeting({meetingId: meetingId})
        .$promise
        .then(function() {
            $location.path('/groups/' + $scope.group.id);
          }, function(error) {
            if (error.status === -1) {
              $scope.error = 'can\'t reach server';
            } else {
              $scope.error = error.statusText;
            }
          }
        );
		};

		$scope.dismissDeleteMeetingAlert = function() {
		  $scope.error = '';
		};

		$scope.newComment = {};

		$scope.addMeetingComment = function() {
		  Meeting
		  .newComment({meetingId: meetingId}, { text: $scope.newComment.text })
		  .$promise
		  .then(function() {
		    $scope.newComment.text = '';
		    loadComments();
		  }, function(error) {
		    console.log('Error ' + error);
		  });
		};

		load();
	}]);
})();
