'use strict';
(function(){
  angular.module('patUI')
  .controller('ScheduleMeetingCtrl', ['$scope', '$routeParams', '$location', 'Meeting', function ($scope, $routeParams, $location, Meeting) {

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
      Meeting
        .newMeeting(data)
        .$promise
        .then(function(result) {
          $location.path('/meetings/' + result.meetingId);
        }, function(error) {
          if (error.status === -1) {
            $scope.newMeeting.error = 'can\'t reach server';
          } else {
            $scope.newMeeting.error = error.statusText;
          }
        });
    };
  }]);
})();
