'use strict';

angular.module('fanClubSocialApp')
    .controller('FeedConfigController', function ($scope, $state, $modal, FeedConfig) {
      
        $scope.feedConfigs = [];
        $scope.loadAll = function() {
            FeedConfig.query(function(result) {
               $scope.feedConfigs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.feedConfig = {
                feedClass: null,
                id: null
            };
        };
    });
