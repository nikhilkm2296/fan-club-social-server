'use strict';

describe('FixtureEvent Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockFixtureEvent;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockFixtureEvent = jasmine.createSpy('MockFixtureEvent');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'FixtureEvent': MockFixtureEvent
        };
        createController = function() {
            $injector.get('$controller')("FixtureEventDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'fanClubSocialApp:fixtureEventUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
