'use strict';

angular.module('fanClubSocialApp')
    .controller('AccessDirectoryDetailController', function ($scope, $rootScope, $stateParams, entity, AccessDirectory) {
        $scope.accessDirectory = entity;
        $scope.load = function (id) {
            AccessDirectory.get({id: id}, function(result) {
                $scope.accessDirectory = result;
            });
        };
        var unsubscribe = $rootScope.$on('fanClubSocialApp:accessDirectoryUpdate', function(event, result) {
            $scope.accessDirectory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
