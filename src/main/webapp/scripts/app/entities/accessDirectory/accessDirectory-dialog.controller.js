'use strict';

angular.module('fanClubSocialApp').controller('AccessDirectoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AccessDirectory',
        function($scope, $stateParams, $modalInstance, entity, AccessDirectory) {

        $scope.accessDirectory = entity;
        $scope.load = function(id) {
            AccessDirectory.get({id : id}, function(result) {
                $scope.accessDirectory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('fanClubSocialApp:accessDirectoryUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.accessDirectory.id != null) {
                AccessDirectory.update($scope.accessDirectory, onSaveSuccess, onSaveError);
            } else {
                AccessDirectory.save($scope.accessDirectory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
