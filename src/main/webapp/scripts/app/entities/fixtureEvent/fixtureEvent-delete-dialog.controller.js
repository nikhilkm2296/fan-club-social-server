'use strict';

angular.module('fanClubSocialApp')
	.controller('FixtureEventDeleteController', function($scope, $modalInstance, entity, FixtureEvent) {

        $scope.fixtureEvent = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            FixtureEvent.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });