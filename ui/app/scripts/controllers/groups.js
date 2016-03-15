'use strict';

(function(){
  /**
   * @ngdoc function
   * @name patUI.controller:GroupsCtrl
   * @description
   * # GroupsCtrl
   * Controller hanlding groups page.
   */
  angular.module('patUI')
  .controller('GroupsCtrl', ['$scope', '$rootScope', '$location', 'Group', function ($scope, $rootScope, $location, Group) {
    $rootScope.activeTab = 'groups';

    $scope.groups = Group.allGroups();

    $scope.newGroup = {
      submitNewGroupForm: function() {
        Group.newGroup({name: $scope.newGroup.name, description: $scope.newGroup.description})
        .$promise.then(function(newGroupResult) {
          $location.path('/groups/' + newGroupResult.groupId);
        }, function(error) {
          if (error.status === -1) {
            $scope.newGroup.error = 'can\'t reach server';
          } else {
            $scope.newGroup.error = error.statusText;
          }
        });
      }
    };

    $scope.dismissNewGroupAlert = function() {
      $scope.newGroup.error = '';
    };
  }]);
})();
