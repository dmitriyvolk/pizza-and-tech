'use strict';
(function() {
angular.module('patUI')
  .controller('MeetingsCtrl', ['$scope', '$rootScope', 'CurrentUser', function($scope, $rootScope, CurrentUser) {
    $rootScope.activeTab = 'meetings';
    $scope.upcomingMeetings = CurrentUser.meetings();
  }]);
})();
