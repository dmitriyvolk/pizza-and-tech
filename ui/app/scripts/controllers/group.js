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
    $scope.isInTheFuture = function(meeting) {
      return new Date(meeting.startDate) > now;
    };
    $scope.isInThePast = function(meeting) {
      return !($scope.isInTheFuture(meeting));
    };

    $scope.loadComments = function() {
      $scope.comments = Group.comments({groupId: groupId});
    };

    function load() {
      $scope.group = Group.get({groupId: groupId});
      $scope.allMeetings = Group.meetings({groupId: groupId});
      $scope.members = Group.members({groupId: groupId});
      $scope.loadComments();
    }


    $scope.newComment = {};

    $scope.addGroupComment = function() {
      console.log('Adding comment to group');
      Group
        .newComment({groupId: groupId}, {text: $scope.newComment.text})
        .$promise.then(function() {
          $scope.newComment.text='';
          load();
        }, function(error) {
          console.log('Error: ' + error);
        });
    };

    load();
  }]);
})();
