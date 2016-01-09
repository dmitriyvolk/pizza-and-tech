'use strict';
(function(){
  /**
   * @ngdoc function
   * @name patUI.controller:GroupCtrl
   * @description
   * # GroupCtrl
   * Controller hanlding showing a single group.
   */
  angular.module('patUI')
  .controller('GroupCtrl', ['$rootScope', '$scope', '$routeParams', 'Group', function ($rootScope, $scope, $routeParams, Group) {

    $rootScope.activeTab = 'groups';
    var groupId = $routeParams.id;
    $scope.groupId = groupId;
    var now = new Date();
    $scope.group = Group.get({groupId: groupId});
    $scope.isInTheFuture = function(meeting) {
      return new Date(meeting.startDate) > now;
    };
    $scope.isInThePast = function(meeting) {
      return !($scope.isInTheFuture(meeting));
    };
    $scope.allMeetings = Group.meetings({groupId: groupId});
    console.log('allMeetings');
    console.dir($scope.allMeetings);
    $scope.members = Group.members({groupId: groupId});
    $scope.comments = Group.comments({groupId: groupId});

  }]);
})();
