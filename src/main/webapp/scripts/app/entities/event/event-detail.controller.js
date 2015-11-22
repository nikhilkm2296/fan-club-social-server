'use strict';

angular.module('fanClubSocialApp')
    .controller('EventDetailController', function ($scope, $rootScope, $stateParams, entity, Event) {
        $scope.event = entity;
        $scope.load = function (id) {
            Event.get({id: id}, function(result) {
                $scope.event = result;
            });
        };
        var unsubscribe = $rootScope.$on('fanClubSocialApp:eventUpdate', function(event, result) {
            $scope.event = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
