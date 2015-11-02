'use strict';

/**
 * @ngdoc function
 * @name patUI.controller:EventCtrl
 * @description
 * # EventCtrl
 * Controller hanlding a single event.
 */
(function(){
	angular.module('patUI')
	.controller('EventCtrl', ['$rootScope', '$scope', '$routeParams', 'Event', function ($rootScope, $scope, $routeParams, Event) {
		$rootScope.activeTab = 'events';
		var eventId = $routeParams.id;
		$scope.event = Event.get({eventId: eventId});
		$scope.rsvps = Event.rsvps({eventId: eventId});
		$scope.comments = Event.comments({eventId: eventId});

		$scope.isDescriptionCollapsed = true;
		$scope.toggleIsDescriptionCollapsed = function() {
			$scope.isDescriptionCollapsed = !$scope.isDescriptionCollapsed;
		};
	}]);
})();
