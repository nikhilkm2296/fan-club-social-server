'use strict';

angular.module('fanClubSocialApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('activate', {
                parent: 'account',
                url: '/activate?key',
                data: {
                    authorities: [],
                    pageTitle: 'Activation'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/activate/activate.html',
                        controller: 'ActivationController'
                    }
                },
                resolve: {
                    
                }
            });
    });
