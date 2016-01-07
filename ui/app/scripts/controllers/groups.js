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
  .controller('GroupsCtrl', ['$scope', 'Group', function ($scope, Group) {
    $scope.newGroup = {
      submitNewGroupForm: function() {
        console.log('Submitting form....');
        console.dir($scope.newGroupForm);
        $scope.newGroup.result = Group.newGroup({name: $scope.newGroup.name, description: $scope.newGroup.description});
      }
    };

  }]);
})();
