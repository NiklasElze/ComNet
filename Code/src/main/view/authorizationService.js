function authorizationService($rootScope, $state, principalService) {
    return {
        isAuthorized: function() {
            if ($rootScope.toState.data.roles
                && $rootScope.toState
                    .data.roles.length > 0
                && !principalService.isInAnyRole(
                    $rootScope.toState.data.roles)) {
                return false;
            }
            else {
                return true;
            }
        },
        authorize: function () {
            var force = false;

            if ($state.current.name === 'signin') {
                force = true;
            }

            return principalService.identity(force)
              .then(function () {
                    var toState = $rootScope.toState.name;
                  var isAuthenticated = principalService.isAuthenticated();

                  if ($rootScope.toState.data.roles
                      && $rootScope.toState
                                   .data.roles.length > 0
                      && !principalService.isInAnyRole(
                         $rootScope.toState.data.roles)) {
                      if (isAuthenticated) {
                          $state.go('main.administration.overview');
                      } else {
                          $rootScope.returnToState = $rootScope.toState;
                          $rootScope.returnToStateParams = $rootScope.toStateParams;

                          $state.go('login');
                      }
                  }
              });
        }
    };
}

angular.module('ComNet').factory('authorizationService', ['$rootScope', '$state', 'principalService', authorizationService]);