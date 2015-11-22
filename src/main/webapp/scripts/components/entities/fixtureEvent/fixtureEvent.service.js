'use strict';

angular.module('fanClubSocialApp')
    .factory('FixtureEvent', function ($resource, DateUtils) {
        return $resource('api/fixtureEvents/:id', {}, {
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
