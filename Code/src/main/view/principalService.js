function principalService($q, $http, $localStorage) {
    var _authenticated = false,
        _credentials = null;

    return {
        isAuthenticated: function() {
            if (!_authenticated && $localStorage.identity){
                _authenticated = true;
            }

            return _authenticated;
        },
        hasRole: function(id) {
            return userHasRole(id);
        },
        isInAnyRole: function(roles) {
            if (!_authenticated || !$localStorage.identity.privileges){
                return false;
            }

            for (var i = 0; i < roles.length; i++) {
                if (userHasRole(roles[i])) return true;
            }

            return false;
        },
        authenticate: function(credentials) {
            _credentials = credentials;
            _authenticated = false;
        },
        getIdentity: function(){
            return $localStorage.identity;
        },
        setIdentity: function(data){
            $localStorage.identity = data;
            _authenticated = data != null;
        },
        identity: function(force) {
            var deferred = $q.defer($localStorage.identity);

            if (force === true){
                $localStorage.identity = undefined;
            }

            if ($localStorage.identity != null && angular.isDefined($localStorage.identity)){
                _authenticated = true;
                deferred.resolve($localStorage.identity);

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
                    $localStorage.identity = response.data;
                    _authenticated = true;
                    deferred.resolve($localStorage.identity);
                }, function errorCallback(response) {
                    $localStorage.identity = null;
                    _authenticated = false;
                    deferred.resolve($localStorage.identity);
                });
            }
            else {
                deferred.resolve($localStorage.identity);
            }

            return deferred.promise;
        }
    };

    function userHasRole(id){
        var hasRole = false;

        angular.forEach($localStorage.identity.privileges, function(privilege){
            if (privilege.id === id){
                hasRole = true;
            }
        });

        return hasRole;
    }
}

angular.module('ComNet').factory('principalService', ['$q', '$http', '$localStorage', principalService]);