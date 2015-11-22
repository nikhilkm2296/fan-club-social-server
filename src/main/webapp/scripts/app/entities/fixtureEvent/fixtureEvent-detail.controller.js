'use strict';

angular.module('fanClubSocialApp')
    .controller('FixtureEventDetailController', function ($scope, $rootScope, $stateParams, entity, FixtureEvent) {
        $scope.fixtureEvent = entity;
        $scope.load = function (id) {
            FixtureEvent.get({id: id}, function(result) {
                $scope.fixtureEvent = result;
            });
        };
        var unsubscribe = $rootScope.$on('fanClubSocialApp:fixtureEventUpdate', function(event, result) {
            $scope.fixtureEvent = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
