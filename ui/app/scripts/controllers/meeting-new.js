'use strict';
(function(){
  angular.module('patUI')
  .controller('ScheduleMeetingCtrl', ['$scope', '$routeParams', 'Meeting', function ($scope, $routeParams, Meeting) {

    var groupId = $routeParams.groupId;
    $scope.newMeeting = {};

    $scope.submitNewMeetingForm = function() {
      var data = {
        groupId: groupId,
        meetingDetails: {
          name: $scope.newMeeting.title,
          description: $scope.newMeeting.description,
          startDate: $scope.newMeeting.startDate
        }
      };
      console.dir(data);
      $scope.newMeetingResult = Meeting.newMeeting(data);
    };
  }]);
})();
