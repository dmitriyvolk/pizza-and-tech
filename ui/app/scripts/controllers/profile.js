'use strict';
(function() {
  angular.module('patUI')
  .controller('ProfileCtrl', ['$scope', '$rootScope', 'CurrentUser', function($scope, $rootScope, CurrentUser) {

    $rootScope.activeTab = 'profile';

    $scope.user = CurrentUser.get();
    CurrentUser.meetings().then(function(data) {$scope.upcomingMeetings = data;});
    CurrentUser.memberOf().then(function(data) {$scope.groups = data;});

  }]);
})();
