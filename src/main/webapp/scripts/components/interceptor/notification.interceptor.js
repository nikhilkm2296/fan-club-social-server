 'use strict';

angular.module('fanClubSocialApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-fanClubSocialApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-fanClubSocialApp-params')});
                }
                return response;
            }
        };
    });
