'use strict';

angular.module('fanClubSocialApp')
    .factory('Event', function ($resource, DateUtils) {
        return $resource('api/events/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.eventDate = DateUtils.convertDateTimeFromServer(data.eventDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
