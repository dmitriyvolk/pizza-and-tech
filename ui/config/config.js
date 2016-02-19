'use strict';
(function() {
angular.module('patUI.config', [])
  .constant('patConfig', {
    environment: '@@environment',
    dataRoot: '@@dataRoot',
    commandSideServiceUrl: '@@commandSideServiceUrl',
    authenticationServiceUrl: '@@authenticationServiceUrl'
  });
})();
