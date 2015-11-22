'use strict';

angular.module('fanClubSocialApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('accessDirectory', {
                parent: 'entity',
                url: '/accessDirectorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'AccessDirectorys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/accessDirectory/accessDirectorys.html',
                        controller: 'AccessDirectoryController'
                    }
                },
                resolve: {
                }
            })
            .state('accessDirectory.detail', {
                parent: 'entity',
                url: '/accessDirectory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'AccessDirectory'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/accessDirectory/accessDirectory-detail.html',
                        controller: 'AccessDirectoryDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'AccessDirectory', function($stateParams, AccessDirectory) {
                        return AccessDirectory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('accessDirectory.new', {
                parent: 'accessDirectory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/accessDirectory/accessDirectory-dialog.html',
                        controller: 'AccessDirectoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    email: null,
                                    accessCode: null,
                                    memberType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('accessDirectory', null, { reload: true });
                    }, function() {
                        $state.go('accessDirectory');
                    })
                }]
            })
            .state('accessDirectory.edit', {
                parent: 'accessDirectory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/accessDirectory/accessDirectory-dialog.html',
                        controller: 'AccessDirectoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AccessDirectory', function(AccessDirectory) {
                                return AccessDirectory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('accessDirectory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('accessDirectory.delete', {
                parent: 'accessDirectory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/accessDirectory/accessDirectory-delete-dialog.html',
                        controller: 'AccessDirectoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AccessDirectory', function(AccessDirectory) {
                                return AccessDirectory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('accessDirectory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
