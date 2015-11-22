'use strict';

angular.module('fanClubSocialApp')
    .controller('EventController', function ($scope, $state, $modal, Event, ParseLinks) {
      
        $scope.events = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Event.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.events = result;
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
            $scope.event = {
                name: null,
                description: null,
                eventDate: null,
                venue: null,
                image: null,
                id: null
            };
        };
    });
