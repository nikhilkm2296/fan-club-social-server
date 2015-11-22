'use strict';

angular.module('fanClubSocialApp')
    .controller('FixtureEventController', function ($scope, $state, $modal, FixtureEvent) {
      
        $scope.fixtureEvents = [];
        $scope.loadAll = function() {
            FixtureEvent.query(function(result) {
               $scope.fixtureEvents = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.fixtureEvent = {
                eventId: null,
                matchId: null,
                eventMinute: null,
                eventTeam: null,
                eventPlayer: null,
                eventType: null,
                eventResult: null,
                id: null
            };
        };
    });
