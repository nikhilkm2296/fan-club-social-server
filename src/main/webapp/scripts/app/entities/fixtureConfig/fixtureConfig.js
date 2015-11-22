'use strict';

angular.module('fanClubSocialApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fixtureConfig', {
                parent: 'entity',
                url: '/fixtureConfigs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'FixtureConfigs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fixtureConfig/fixtureConfigs.html',
                        controller: 'FixtureConfigController'
                    }
                },
                resolve: {
                }
            })
            .state('fixtureConfig.detail', {
                parent: 'entity',
                url: '/fixtureConfig/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'FixtureConfig'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fixtureConfig/fixtureConfig-detail.html',
                        controller: 'FixtureConfigDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'FixtureConfig', function($stateParams, FixtureConfig) {
                        return FixtureConfig.get({id : $stateParams.id});
                    }]
                }
            })
            .state('fixtureConfig.new', {
                parent: 'fixtureConfig',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fixtureConfig/fixtureConfig-dialog.html',
                        controller: 'FixtureConfigDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    sourceClass: null,
                                    sourceProcessorClass: null,
                                    active: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fixtureConfig', null, { reload: true });
                    }, function() {
                        $state.go('fixtureConfig');
                    })
                }]
            })
            .state('fixtureConfig.edit', {
                parent: 'fixtureConfig',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fixtureConfig/fixtureConfig-dialog.html',
                        controller: 'FixtureConfigDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['FixtureConfig', function(FixtureConfig) {
                                return FixtureConfig.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fixtureConfig', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('fixtureConfig.delete', {
                parent: 'fixtureConfig',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fixtureConfig/fixtureConfig-delete-dialog.html',
                        controller: 'FixtureConfigDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['FixtureConfig', function(FixtureConfig) {
                                return FixtureConfig.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fixtureConfig', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
