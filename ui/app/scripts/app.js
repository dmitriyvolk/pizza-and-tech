'use strict';

/**
 * @ngdoc overview
 * @name patUI
 * @description
 * # patUI
 *
 * Main module of the application.
 */
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
        templateUrl: 'views/groups.html'
      })
      .when('/groups/:id', {
        templateUrl: 'views/group.html',
        controller: 'GroupCtrl'
      })
      .when('/events', {
        templateUrl: 'views/events.html'
      })
      .when('/events/:id', {
        templateUrl: 'views/event.html',
        controller: 'EventCtrl'
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
  });
