'use strict';

angular.module('fanClubSocialApp')
    .controller('FixtureConfigDetailController', function ($scope, $rootScope, $stateParams, entity, FixtureConfig) {
        $scope.fixtureConfig = entity;
        $scope.load = function (id) {
            FixtureConfig.get({id: id}, function(result) {
                $scope.fixtureConfig = result;
            });
        };
        var unsubscribe = $rootScope.$on('fanClubSocialApp:fixtureConfigUpdate', function(event, result) {
            $scope.fixtureConfig = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
