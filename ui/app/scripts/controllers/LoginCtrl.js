'use strict';
(function() {
  /**
   * @ngdoc controller
   * @name patUI:LoginCtrl
   *
   * @description
   *
   *
   * @requires $scope
   * */
  angular.module('patUI')
    .controller('LoginCtrl', ['$rootScope', '$http', '$location', 'patConfig', function($rootScope, $http, $location, patConfig) {
      var self = this;
      var authenticate = function(credentials, callback) {
        var currentUserUrl = patConfig.commandSideServiceUrl + '/currentuser';

        var headers = credentials ? {
          authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
        } : {};
        console.log(headers);
        $http.get(currentUserUrl, {headers : headers}).success(function(data) {
          if (data.username) {
            $rootScope.authenticated = true;
          } else {
            $rootScope.authenticated = false;
          }
          if (callback) {
            callback();
          }
        }).error(function() {
          $rootScope.authenticated = false;
          if (callback) {
            callback();
          }
        });

      };

      authenticate();
      self.credentials = {};
      self.login = function() {
          authenticate(self.credentials, function() {
            if ($rootScope.authenticated) {
              $location.path('/');
              self.error = false;
            } else {
              $location.path('/login');
              self.error = true;
            }
          });
      };
    }]);
})();
