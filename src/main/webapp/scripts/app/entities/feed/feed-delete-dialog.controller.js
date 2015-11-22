'use strict';

angular.module('fanClubSocialApp')
	.controller('FeedDeleteController', function($scope, $modalInstance, entity, Feed) {

        $scope.feed = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Feed.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });