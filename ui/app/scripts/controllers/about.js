'use strict';

/**
 * @ngdoc function
 * @name patUI.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the patUI
 */
angular.module('patUI')
  .controller('AboutCtrl', ['$rootScope', '$scope', function ($rootScope, $scope) {
  	$rootScope.activeTab = 'about';
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  }]);
