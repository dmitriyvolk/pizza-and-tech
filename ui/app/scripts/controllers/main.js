'use strict';
/**
 * @ngdoc function
 * @name patUI.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the patUI
 */
(function() {
angular.module('patUI')
  .controller('MainCtrl', ['$rootScope', '$scope', 'patConfig', 'HomePage', function ($rootScope, $scope, patConfig, HomePage) {
  	$rootScope.activeTab = 'home';
  	$scope.featuredGroups = HomePage.featuredGroups();
  	$rootScope.patEnv = patConfig.environment;
  }]);
})();
