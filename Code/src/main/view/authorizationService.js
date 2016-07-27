function authorizationService($rootScope, $state, principalService) {
    return {
        authorize: function () {
            var force = false;

            if ($state.current.name === 'signin') {
                force = true;
            }

            return principalService.identity(force)
              .then(function () {
                  var isAuthenticated = principalService.isAuthenticated();

                  if ($rootScope.toState.data.roles
                      && $rootScope.toState
                                   .data.roles.length > 0
                      && !principalService.isInAnyRole(
                         $rootScope.toState.data.roles)) {
                      if (isAuthenticated) {
                          //$state.go('login');
                      } else {
                          $rootScope.returnToState
                              = $rootScope.toState;
                          $rootScope.returnToStateParams
                              = $rootScope.toStateParams;

                          $state.go('login');
                      }
                  }
              });
        }
    };
}

angular.module('ComNet').factory('authorizationService', ['$rootScope', '$state', 'principalService', authorizationService]);