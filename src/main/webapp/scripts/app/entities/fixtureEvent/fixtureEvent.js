'use strict';

angular.module('fanClubSocialApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fixtureEvent', {
                parent: 'entity',
                url: '/fixtureEvents',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'FixtureEvents'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fixtureEvent/fixtureEvents.html',
                        controller: 'FixtureEventController'
                    }
                },
                resolve: {
                }
            })
            .state('fixtureEvent.detail', {
                parent: 'entity',
                url: '/fixtureEvent/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'FixtureEvent'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fixtureEvent/fixtureEvent-detail.html',
                        controller: 'FixtureEventDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'FixtureEvent', function($stateParams, FixtureEvent) {
                        return FixtureEvent.get({id : $stateParams.id});
                    }]
                }
            })
            .state('fixtureEvent.new', {
                parent: 'fixtureEvent',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fixtureEvent/fixtureEvent-dialog.html',
                        controller: 'FixtureEventDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    eventId: null,
                                    matchId: null,
                                    eventMinute: null,
                                    eventTeam: null,
                                    eventPlayer: null,
                                    eventType: null,
                                    eventResult: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fixtureEvent', null, { reload: true });
                    }, function() {
                        $state.go('fixtureEvent');
                    })
                }]
            })
            .state('fixtureEvent.edit', {
                parent: 'fixtureEvent',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fixtureEvent/fixtureEvent-dialog.html',
                        controller: 'FixtureEventDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['FixtureEvent', function(FixtureEvent) {
                                return FixtureEvent.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fixtureEvent', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('fixtureEvent.delete', {
                parent: 'fixtureEvent',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fixtureEvent/fixtureEvent-delete-dialog.html',
                        controller: 'FixtureEventDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['FixtureEvent', function(FixtureEvent) {
                                return FixtureEvent.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fixtureEvent', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
