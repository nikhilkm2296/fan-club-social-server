'use strict';

angular.module('fanClubSocialApp')
    .controller('FixtureConfigController', function ($scope, $state, $modal, FixtureConfig) {
      
        $scope.fixtureConfigs = [];
        $scope.loadAll = function() {
            FixtureConfig.query(function(result) {
               $scope.fixtureConfigs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.fixtureConfig = {
                sourceClass: null,
                sourceProcessorClass: null,
                active: false,
                id: null
            };
        };
    });
