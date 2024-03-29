'use strict';

describe('FixtureConfig Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockFixtureConfig;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockFixtureConfig = jasmine.createSpy('MockFixtureConfig');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'FixtureConfig': MockFixtureConfig
        };
        createController = function() {
            $injector.get('$controller')("FixtureConfigDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'fanClubSocialApp:fixtureConfigUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
