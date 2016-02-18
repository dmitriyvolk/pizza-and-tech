'use strict';
(function() {
  angular.module('patUI')
  .controller('ProfileCtrl', ['$scope', '$rootScope', function($scope, $rootScope) {

    $rootScope.activeTab = 'profile';

  }]);
})();
