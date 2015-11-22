'use strict';

angular.module('fanClubSocialApp')
	.controller('FeedConfigDeleteController', function($scope, $modalInstance, entity, FeedConfig) {

        $scope.feedConfig = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            FeedConfig.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });