'use strict';

angular.module('fanClubSocialApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


