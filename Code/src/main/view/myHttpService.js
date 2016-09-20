'use strict';

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
        getStudentsOfSeminarGroup: getStudentsOfSeminarGroup,
        getConversationsOfStudent: getConversationsOfStudent,
        getConversationByMembers: getConversationByMembers,
        getConversationById: getConversationById,
        addOrUpdateConversation: addOrUpdateConversation,
        addMessage: addMessage,
        getMessagesOfConversation: getMessagesOfConversation,
        getStudentContactListOfConversation: getStudentContactListOfConversation,
        addMembersToConversation: addMembersToConversation,
        getStudentContactListOfNewConversation: getStudentContactListOfNewConversation,
        removeStudentFromConversation: removeStudentFromConversation,
        getGroupsOfStudent: getGroupsOfStudent,
        getGroupById: getGroupById,
        addOrUpdateGroup: addOrUpdateGroup,
        deleteGroup: deleteGroup,
        getTopicEntriesOfTopic: getTopicEntriesOfTopic,
        addOrUpdateTopic: addOrUpdateTopic,
        deleteTopic: deleteTopic
    };

    function addOrUpdateTopic(data){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();
        var id = identity.id;

        $http({
            url: 'http://localhost:8080/myTest/rest/group/student/' + id,
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: data
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

    function getTopicEntriesOfTopic(id){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/topicentry/topic/' + id,
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

    function getGroupById(id){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/group/' + id,
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

    function deleteGroup(id){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/group/' + id,
            method: 'DELETE',
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

    function addOrUpdateGroup(data){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/group',
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: data
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

    function getGroupsOfStudent(){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();
        var id = identity.id;

        $http({
            url: 'http://localhost:8080/myTest/rest/group/student/' + id,
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

    function removeStudentFromConversation(conversationId){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/conversation/' + conversationId + '/members/remove',
            method: 'PUT',
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

    function getStudentContactListOfNewConversation(){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/seminargroup/contactlist/conversation/new',
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

    function addMembersToConversation(id, members){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/conversation/' + id + '/members/add',
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: members
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

    function getStudentContactListOfConversation(id){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/seminargroup/contactlist/conversation/' + id,
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

    function getMessagesOfConversation(id){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/message/conversation/' + id,
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

    function addMessage(data){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();
        data.senderId = identity.id;

        $http({
            url: 'http://localhost:8080/myTest/rest/message',
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: data
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

    function addOrUpdateConversation(data){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/conversation',
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: data
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

    function getConversationById(id){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/conversation/' + id,
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

    function getConversationByMembers(data){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();

        $http({
            url: 'http://localhost:8080/myTest/rest/conversation',
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + identity.sessionId,
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: data
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

    function getConversationsOfStudent(){
        var deferred = $q.defer();

        var identity = principalService.getIdentity();
        var id = identity.id;

        $http({
            url: 'http://localhost:8080/myTest/rest/conversation/student/' + id,
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