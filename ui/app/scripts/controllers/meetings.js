'use strict';
(function() {
angular.module('patUI')
  .controller('MeetingsCtrl', ['$scope', '$rootScope', 'User', function($scope, $rootScope, User) {
    $rootScope.activeTab = 'meetings';
  }]);
})();
