'use strict';

angular.module('fanClubSocialApp').controller('FeedDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Feed',
        function($scope, $stateParams, $modalInstance, entity, Feed) {

        $scope.feed = entity;
        $scope.load = function(id) {
            Feed.get({id : id}, function(result) {
                $scope.feed = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('fanClubSocialApp:feedUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.feed.id != null) {
                Feed.update($scope.feed, onSaveSuccess, onSaveError);
            } else {
                Feed.save($scope.feed, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
