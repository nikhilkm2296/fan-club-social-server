'use strict';

angular.module('fanClubSocialApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('feed', {
                parent: 'entity',
                url: '/feeds',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Feeds'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feed/feeds.html',
                        controller: 'FeedController'
                    }
                },
                resolve: {
                }
            })
            .state('feed.detail', {
                parent: 'entity',
                url: '/feed/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Feed'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feed/feed-detail.html',
                        controller: 'FeedDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Feed', function($stateParams, Feed) {
                        return Feed.get({id : $stateParams.id});
                    }]
                }
            })
            .state('feed.new', {
                parent: 'feed',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feed/feed-dialog.html',
                        controller: 'FeedDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    description: null,
                                    link: null,
                                    image: null,
                                    source: null,
                                    publicationDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('feed', null, { reload: true });
                    }, function() {
                        $state.go('feed');
                    })
                }]
            })
            .state('feed.edit', {
                parent: 'feed',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feed/feed-dialog.html',
                        controller: 'FeedDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Feed', function(Feed) {
                                return Feed.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('feed', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('feed.delete', {
                parent: 'feed',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feed/feed-delete-dialog.html',
                        controller: 'FeedDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Feed', function(Feed) {
                                return Feed.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('feed', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
