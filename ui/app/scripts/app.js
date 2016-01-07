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
    'ui.bootstrap'
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
        templateUrl: 'views/meetings.html'
      })
      .when('/meetings/:id', {
        templateUrl: 'views/meeting.html',
        controller: 'MeetingCtrl'
      })
      .when('/users', {
        templateUrl: 'views/users.html'
      })
      .when('/users/:id', {
        templateUrl: 'views/user.html',
        controller: 'UserCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  })
  .value('dataRoot', 'localdata')
  .value('commandSideServiceUrl', 'http://localhost:8080');
})();
