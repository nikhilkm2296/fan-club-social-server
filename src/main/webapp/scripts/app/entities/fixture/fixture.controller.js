'use strict';

angular.module('fanClubSocialApp')
    .controller('FixtureController', function ($scope, $state, $modal, Fixture, ParseLinks) {
      
        $scope.fixtures = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Fixture.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.fixtures = result;
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
            $scope.fixture = {
                matchId: null,
                teamOneId: null,
                teamTwoId: null,
                teamOne: null,
                teamTwo: null,
                teamOneScore: null,
                teamTwoScore: null,
                matchTime: null,
                htScore: null,
                ftScore: null,
                matchDate: null,
                id: null
            };
        };
    });
