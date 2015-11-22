'use strict';

angular.module('fanClubSocialApp')
	.controller('FixtureConfigDeleteController', function($scope, $modalInstance, entity, FixtureConfig) {

        $scope.fixtureConfig = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            FixtureConfig.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });