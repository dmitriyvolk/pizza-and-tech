'use strict';

/**
 * @ngdoc overview
 * @name patUI
 * @description
 * # patUI
 *
 * Main module of the application.
 */
(function() {
  angular
  .module('patUI', [
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ui.bootstrap',
    'patUI.config'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .when('/groups', {
        templateUrl: 'views/groups.html',
        controller: 'GroupsCtrl'
      })
      .when('/groups/:id', {
        templateUrl: 'views/group.html',
        controller: 'GroupCtrl'
      })
      .when('/groups/:groupId/meetings/new', {
        templateUrl: 'views/meeting-new.html',
        controller: 'ScheduleMeetingCtrl'
      })
      .when('/meetings', {
        templateUrl: 'views/meetings.html',
        controller: 'MeetingsCtrl'
      })
      .when('/meetings/:id', {
        templateUrl: 'views/meeting.html',
        controller: 'MeetingCtrl'
      })
      .when('/users/:id', {
        templateUrl: 'views/user.html',
        controller: 'UserCtrl'
      })
      .when('/profile', {
        templateUrl: 'views/profile.html',
        controller: 'ProfileCtrl'
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl',
        controllerAs: 'controller'
      })
      .otherwise({
        redirectTo: '/'
      });
  })
  .config(function($httpProvider) {
    $httpProvider.interceptors.push(function($rootScope) {
      return {
        request: function(config) {
          config.headers['X-PIZZAANDTECH-USERID'] = $rootScope.currentUser.id;
          return config;
        }
      };
    });
  })
;
})();
