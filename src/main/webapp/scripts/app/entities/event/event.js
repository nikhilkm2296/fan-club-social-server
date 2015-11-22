'use strict';

angular.module('fanClubSocialApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('event', {
                parent: 'entity',
                url: '/events',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Events'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/event/events.html',
                        controller: 'EventController'
                    }
                },
                resolve: {
                }
            })
            .state('event.detail', {
                parent: 'entity',
                url: '/event/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Event'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/event/event-detail.html',
                        controller: 'EventDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Event', function($stateParams, Event) {
                        return Event.get({id : $stateParams.id});
                    }]
                }
            })
            .state('event.new', {
                parent: 'event',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/event/event-dialog.html',
                        controller: 'EventDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    eventDate: null,
                                    venue: null,
                                    image: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('event', null, { reload: true });
                    }, function() {
                        $state.go('event');
                    })
                }]
            })
            .state('event.edit', {
                parent: 'event',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/event/event-dialog.html',
                        controller: 'EventDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Event', function(Event) {
                                return Event.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('event', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('event.delete', {
                parent: 'event',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/event/event-delete-dialog.html',
                        controller: 'EventDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Event', function(Event) {
                                return Event.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('event', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
