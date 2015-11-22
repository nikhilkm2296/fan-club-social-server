'use strict';

angular.module('fanClubSocialApp')
    .factory('FixtureConfig', function ($resource, DateUtils) {
        return $resource('api/fixtureConfigs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
