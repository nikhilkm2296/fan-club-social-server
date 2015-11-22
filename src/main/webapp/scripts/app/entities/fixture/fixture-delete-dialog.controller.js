'use strict';

angular.module('fanClubSocialApp')
	.controller('FixtureDeleteController', function($scope, $modalInstance, entity, Fixture) {

        $scope.fixture = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Fixture.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });