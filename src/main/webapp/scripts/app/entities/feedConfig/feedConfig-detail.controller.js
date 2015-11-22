'use strict';

angular.module('fanClubSocialApp')
    .controller('FeedConfigDetailController', function ($scope, $rootScope, $stateParams, entity, FeedConfig) {
        $scope.feedConfig = entity;
        $scope.load = function (id) {
            FeedConfig.get({id: id}, function(result) {
                $scope.feedConfig = result;
            });
        };
        var unsubscribe = $rootScope.$on('fanClubSocialApp:feedConfigUpdate', function(event, result) {
            $scope.feedConfig = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
