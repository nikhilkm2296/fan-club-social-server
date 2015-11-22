'use strict';

angular.module('fanClubSocialApp').controller('FixtureConfigDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'FixtureConfig',
        function($scope, $stateParams, $modalInstance, entity, FixtureConfig) {

        $scope.fixtureConfig = entity;
        $scope.load = function(id) {
            FixtureConfig.get({id : id}, function(result) {
                $scope.fixtureConfig = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('fanClubSocialApp:fixtureConfigUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.fixtureConfig.id != null) {
                FixtureConfig.update($scope.fixtureConfig, onSaveSuccess, onSaveError);
            } else {
                FixtureConfig.save($scope.fixtureConfig, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
