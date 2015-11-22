'use strict';

angular.module('fanClubSocialApp')
	.controller('AccessDirectoryDeleteController', function($scope, $modalInstance, entity, AccessDirectory) {

        $scope.accessDirectory = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AccessDirectory.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });