'use strict';

angular.module('fanClubSocialApp')
    .factory('Fixture', function ($resource, DateUtils) {
        return $resource('api/fixtures/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.matchDate = DateUtils.convertDateTimeFromServer(data.matchDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
