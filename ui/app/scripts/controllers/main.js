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
  .controller('MainCtrl', ['$rootScope', '$scope', 'HomePage', function ($rootScope, $scope, HomePage) {
  	$rootScope.activeTab = 'home';
  	$scope.featuredGroups = HomePage.featuredGroups();
  }]);
})();
