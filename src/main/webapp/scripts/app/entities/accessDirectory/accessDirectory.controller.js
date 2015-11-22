'use strict';

angular.module('fanClubSocialApp')
    .controller('AccessDirectoryController', function ($scope, $state, $modal, AccessDirectory) {
      
        $scope.accessDirectorys = [];
        $scope.loadAll = function() {
            AccessDirectory.query(function(result) {
               $scope.accessDirectorys = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.accessDirectory = {
                email: null,
                accessCode: null,
                memberType: null,
                id: null
            };
        };
    });
