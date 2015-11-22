'use strict';

angular.module('fanClubSocialApp').controller('FixtureEventDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'FixtureEvent',
        function($scope, $stateParams, $modalInstance, entity, FixtureEvent) {

        $scope.fixtureEvent = entity;
        $scope.load = function(id) {
            FixtureEvent.get({id : id}, function(result) {
                $scope.fixtureEvent = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('fanClubSocialApp:fixtureEventUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.fixtureEvent.id != null) {
                FixtureEvent.update($scope.fixtureEvent, onSaveSuccess, onSaveError);
            } else {
                FixtureEvent.save($scope.fixtureEvent, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
