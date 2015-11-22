'use strict';

angular.module('fanClubSocialApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fixture', {
                parent: 'entity',
                url: '/fixtures',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Fixtures'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fixture/fixtures.html',
                        controller: 'FixtureController'
                    }
                },
                resolve: {
                }
            })
            .state('fixture.detail', {
                parent: 'entity',
                url: '/fixture/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Fixture'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fixture/fixture-detail.html',
                        controller: 'FixtureDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Fixture', function($stateParams, Fixture) {
                        return Fixture.get({id : $stateParams.id});
                    }]
                }
            })
            .state('fixture.new', {
                parent: 'fixture',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fixture/fixture-dialog.html',
                        controller: 'FixtureDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
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
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fixture', null, { reload: true });
                    }, function() {
                        $state.go('fixture');
                    })
                }]
            })
            .state('fixture.edit', {
                parent: 'fixture',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fixture/fixture-dialog.html',
                        controller: 'FixtureDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Fixture', function(Fixture) {
                                return Fixture.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fixture', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('fixture.delete', {
                parent: 'fixture',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fixture/fixture-delete-dialog.html',
                        controller: 'FixtureDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Fixture', function(Fixture) {
                                return Fixture.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fixture', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
