'use strict';

describe('FeedConfig Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockFeedConfig;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockFeedConfig = jasmine.createSpy('MockFeedConfig');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'FeedConfig': MockFeedConfig
        };
        createController = function() {
            $injector.get('$controller')("FeedConfigDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'fanClubSocialApp:feedConfigUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
