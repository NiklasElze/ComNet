﻿'use strict';

function myHttpService($state, $rootScope, $http, $q, principalService, errorService) {

    return {
        login: login,
        getSeminarGroups: getSeminarGroups,
        addOrUpdateSeminarGroup: addOrUpdateSeminarGroup,
        deleteSeminarGroup: deleteSeminarGroup,
        getStudentData: getStudentData,
        getUserData: getUserData,
        addOrUpdateStudent: addOrUpdateStudent,
        deleteStudent: deleteStudent,
        deleteUser: deleteUser,
        addOrUpdateUser: addOrUpdateUser,
        getCustodians: getCustodians,
        getStudentsOfSeminarGroup: getStudentsOfSeminarGroup
    };

    function getStudentsOfSeminarGroup(id) {
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/student/seminargroup/' + id,
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            }
        }).then(function successCallback(response) {
            deferred.resolve(response.data);
        }, function errorCallback(response) {
            if (response.data) {
                var errorType = response.data.value;
                var errorMessage = errorService.getMessageByType(errorType);
                deferred.reject(errorMessage);
            }
            else {
                deferred.reject('Could not connect to server.');
            }
        });

        return deferred.promise;
    }

    function getCustodians() {
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/user/custodians',
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            }
        }).then(function successCallback(response) {
            deferred.resolve(response.data);
        }, function errorCallback(response) {
            if (response.data) {
                var errorType = response.data.value;
                var errorMessage = errorService.getMessageByType(errorType);
                deferred.reject(errorMessage);
            }
            else {
                deferred.reject('Could not connect to server.');
            }
        });

        return deferred.promise;
    }

    function addOrUpdateUser(data) {
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/user',
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: data
        }).then(function successCallback(response) {
            deferred.resolve();
        }, function errorCallback(response) {
            if (response.data) {
                var errorType = response.data.value;
                var errorMessage = errorService.getMessageByType(errorType);
                deferred.reject(errorMessage);
            }
            else {
                deferred.reject('Could not connect to server.');
            }
        });

        return deferred.promise;
    }

    function deleteUser(id) {
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/user/' + id,
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            }
        }).then(function successCallback(response) {
            deferred.resolve();
        }, function errorCallback(response) {
            if (response.data) {
                var errorType = response.data.value;
                var errorMessage = errorService.getMessageByType(errorType);
                deferred.reject(errorMessage);
            }
            else {
                deferred.reject('Could not connect to server.');
            }
        });

        return deferred.promise;
    }

    function deleteStudent(id) {
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/student/' + id,
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            }
        }).then(function successCallback(response) {
            deferred.resolve();
        }, function errorCallback(response) {
            if (response.data) {
                var errorType = response.data.value;
                var errorMessage = errorService.getMessageByType(errorType);
                deferred.reject(errorMessage);
            }
            else {
                deferred.reject('Could not connect to server.');
            }
        });

        return deferred.promise;
    }

    function getUserData(id) {
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/user/' + id,
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            }
        }).then(function successCallback(response) {
            deferred.resolve(response.data);
        }, function errorCallback(response) {
            if (response.data) {
                var errorType = response.data.value;
                var errorMessage = errorService.getMessageByType(errorType);
                deferred.reject(errorMessage);
            }
            else {
                deferred.reject('Could not connect to server.');
            }
        });

        return deferred.promise;
    }

    function addOrUpdateStudent(data) {
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/student',
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: data
        }).then(function successCallback(response) {
            deferred.resolve();
        }, function errorCallback(response) {
            if (response.data) {
                var errorType = response.data.value;
                var errorMessage = errorService.getMessageByType(errorType);
                deferred.reject(errorMessage);
            }
            else {
                deferred.reject('Could not connect to server.');
            }
        });

        return deferred.promise;
    }

    function getStudentData(id) {
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/student/' + id,
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            }
        }).then(function successCallback(response) {
            deferred.resolve(response.data);
        }, function errorCallback(response) {
            if (response.data) {
                var errorType = response.data.value;
                var errorMessage = errorService.getMessageByType(errorType);
                deferred.reject(errorMessage);
            }
            else {
                deferred.reject('Could not connect to server.');
            }
        });

        return deferred.promise;
    }

    function deleteSeminarGroup(id) {
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/seminargroup/' + id,
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            }
        }).then(function successCallback(response) {
            deferred.resolve();
        }, function errorCallback(response) {
            if (response.data) {
                var errorType = response.data.value;
                var errorMessage = errorService.getMessageByType(errorType);
                deferred.reject(errorMessage);
            }
            else {
                deferred.reject('Could not connect to server.');
            }
        });

        return deferred.promise;
    }

    function addOrUpdateSeminarGroup(data) {
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/seminargroup',
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: data
        }).then(function successCallback(response) {
            deferred.resolve();
        }, function errorCallback(response) {
            if (response.data) {
                var errorType = response.data.value;
                var errorMessage = errorService.getMessageByType(errorType);
                deferred.reject(errorMessage);
            }
            else {
                deferred.reject('Could not connect to server.');
            }
        });

        return deferred.promise;
    }

    function getSeminarGroups() {
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/seminargroup/list',
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            }
        }).then(function successCallback(response) {
            deferred.resolve(response.data);
        }, function errorCallback(response) {
            
            if (response.data) {
                var errorType = response.data.value;
                var errorMessage = errorService.getMessageByType(errorType);
                deferred.reject(errorMessage);
            }
            else {
                deferred.reject('Could not connect to server.');
            }
        });

        return deferred.promise;
    }

    function login(credentials) {

        var deferred = $q.defer();

        $http({
            url: 'http://localhost:8080/myTest/rest/login',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: credentials
        }).then(function (response) {
            principalService.setIdentity(response.data);
            var goTo = null;
            var goToParams = null;

            if ($rootScope.returnToState) {
                goTo = $rootScope.returnToState.name;
                goToParams = $rootScope.returnToStateParams;
            }
            else {
                goTo = 'main.home';
            }

            var result = {
                goTo: goTo,
                goToParams: goToParams
            };

            deferred.resolve(result);

        }, function (response) {

            if (response.data) {
                var errorType = response.data.value;
                var errorMessage = errorService.getMessageByType(errorType);
                deferred.reject(errorMessage);
            }
            else {
                deferred.reject('Could not connect to server.');
            }
        });

        return deferred.promise;
    }
}

angular.module('ComNet').factory('myHttpService', ['$state', '$rootScope', '$http', '$q', 'principalService', 'errorService', myHttpService]);