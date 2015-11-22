'use strict';

angular.module('fanClubSocialApp').controller('EventDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Event',
        function($scope, $stateParams, $modalInstance, entity, Event) {

        $scope.event = entity;
        $scope.load = function(id) {
            Event.get({id : id}, function(result) {
                $scope.event = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('fanClubSocialApp:eventUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.event.id != null) {
                Event.update($scope.event, onSaveSuccess, onSaveError);
            } else {
                Event.save($scope.event, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
