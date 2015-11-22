'use strict';

angular.module('fanClubSocialApp')
    .controller('FeedDetailController', function ($scope, $rootScope, $stateParams, entity, Feed) {
        $scope.feed = entity;
        $scope.load = function (id) {
            Feed.get({id: id}, function(result) {
                $scope.feed = result;
            });
        };
        var unsubscribe = $rootScope.$on('fanClubSocialApp:feedUpdate', function(event, result) {
            $scope.feed = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
