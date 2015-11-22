'use strict';

angular.module('fanClubSocialApp').controller('FeedConfigDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'FeedConfig',
        function($scope, $stateParams, $modalInstance, entity, FeedConfig) {

        $scope.feedConfig = entity;
        $scope.load = function(id) {
            FeedConfig.get({id : id}, function(result) {
                $scope.feedConfig = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('fanClubSocialApp:feedConfigUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.feedConfig.id != null) {
                FeedConfig.update($scope.feedConfig, onSaveSuccess, onSaveError);
            } else {
                FeedConfig.save($scope.feedConfig, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
