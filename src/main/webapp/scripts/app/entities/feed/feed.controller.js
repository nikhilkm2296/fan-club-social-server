'use strict';

angular.module('fanClubSocialApp')
    .controller('FeedController', function ($scope, $state, $modal, Feed, ParseLinks) {
      
        $scope.feeds = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Feed.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.feeds = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.feed = {
                title: null,
                description: null,
                link: null,
                image: null,
                source: null,
                publicationDate: null,
                id: null
            };
        };
    });
