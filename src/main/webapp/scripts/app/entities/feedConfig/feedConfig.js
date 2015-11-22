'use strict';

angular.module('fanClubSocialApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('feedConfig', {
                parent: 'entity',
                url: '/feedConfigs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'FeedConfigs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feedConfig/feedConfigs.html',
                        controller: 'FeedConfigController'
                    }
                },
                resolve: {
                }
            })
            .state('feedConfig.detail', {
                parent: 'entity',
                url: '/feedConfig/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'FeedConfig'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feedConfig/feedConfig-detail.html',
                        controller: 'FeedConfigDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'FeedConfig', function($stateParams, FeedConfig) {
                        return FeedConfig.get({id : $stateParams.id});
                    }]
                }
            })
            .state('feedConfig.new', {
                parent: 'feedConfig',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feedConfig/feedConfig-dialog.html',
                        controller: 'FeedConfigDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    feedClass: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('feedConfig', null, { reload: true });
                    }, function() {
                        $state.go('feedConfig');
                    })
                }]
            })
            .state('feedConfig.edit', {
                parent: 'feedConfig',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feedConfig/feedConfig-dialog.html',
                        controller: 'FeedConfigDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['FeedConfig', function(FeedConfig) {
                                return FeedConfig.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('feedConfig', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('feedConfig.delete', {
                parent: 'feedConfig',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feedConfig/feedConfig-delete-dialog.html',
                        controller: 'FeedConfigDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['FeedConfig', function(FeedConfig) {
                                return FeedConfig.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('feedConfig', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
