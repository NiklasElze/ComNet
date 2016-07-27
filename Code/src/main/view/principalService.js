function principalService($q, $http, $timeout) {
    var _identity = undefined,
      _authenticated = false,
        _credentials = null

    return {
        isIdentityResolved: function() {
            return angular.isDefined(_identity);
        },
        isAuthenticated: function() {
            return _authenticated;
        },
        hasRole: function(id) {
            //return userHasRole(id);
            return true;
        },
        isInAnyRole: function(roles) {
            if (!_authenticated || !_identity.roles) return false;

            /*for (var i = 0; i < roles.length; i++) {
                if (this.userHasRole(roles[i])) return true;
            }*/

            return false;
        },
        authenticate: function(credentials) {
            _credentials = credentials;
            _authenticated = false;
        },
        getIdentity: function(){
            return _identity;
        },
        setIdentity: function(data){
            _identity = data;
            _authenticated = data != null;
        },
        identity: function(force) {
            var deferred = $q.defer(_identity);

            if (force === true) _identity = undefined;

            if (_identity != null && angular.isDefined(_identity)) {
                deferred.resolve(_identity);

                return deferred.promise;
            }

            if (_credentials !== null) {
                $http({
                    url: 'http://localhost:8080/myTest/rest/login',
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    data: _credentials
                }).then(function successCallback(response) {
                    _identity = response.data;
                    _authenticated = true;
                    deferred.resolve(_identity);
                }, function errorCallback(response) {
                    _identity = null;
                    _authenticated = false;
                    deferred.resolve(_identity);
                });
            }
            else {
                deferred.resolve(_identity);
            }

            return deferred.promise;
        }
    };

    function userHasRole(id){
        var hasRole = false;

        angular.forEach(_identity.privileges, function(privilege){
            if (privilege.id === id){
                hasRole = true;
            }
        });

        return hasRole;
    }
}

angular.module('ComNet').factory('principalService', ['$q', '$http', '$timeout', principalService]);