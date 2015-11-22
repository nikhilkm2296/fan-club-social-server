'use strict';

describe('AccessDirectory Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAccessDirectory;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAccessDirectory = jasmine.createSpy('MockAccessDirectory');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AccessDirectory': MockAccessDirectory
        };
        createController = function() {
            $injector.get('$controller')("AccessDirectoryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'fanClubSocialApp:accessDirectoryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
