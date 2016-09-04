'use strict';

function conversationController($scope, $state, $stateParams, $timeout, $interval, seminarGroupService, updateService, myHttpService) {
    var that = this;
    var id = 0;

    that.sendMessage = sendMessage;
    that.hideInfo = hideInfo;
    that.hideErrorMessage = hideErrorMessage;
    that.startUpdate = startUpdate;
    that.stopUpdate = stopUpdate;
    that.showErrorMessage = showErrorMessage;
    that.cancelMemberAdd = cancelMemberAdd;
    that.showContactList = showContactList;
    that.saveMemberAdd = saveMemberAdd;
    that.leaveConversation = leaveConversation;

    initialize();

    function initialize() {
        $scope.controller = that;
        if ($stateParams.id === 0){
            $state.go('main.messages.overview');
        }

        hideInfo();
        hideErrorMessage();
        startLoading();

        id = $stateParams.id;
        $scope.id = id;

        loadConversation();
        loadStudentContactList();
        updateService.addUpdate('conversation', that);
        updateService.setUpdate('conversation');
    }

    function loadConversation(){
        myHttpService.getConversationById(id).then(function(data){
            setConversation(data);
            stopLoading();
        }, function(errorMessage){
            showErrorMessage(errorMessage);
            stopLoading();
        });
    }

    function loadStudentContactList() {
        myHttpService.getStudentContactListOfConversation(id)
            .then(function (data) {
                $scope.seminarGroups = seminarGroupService.orderGroups(data);
            }, function (errorMessage) {
                showErrorMessage(errorMessage);
            });
    }

    function setConversation(data){
        $scope.members = data.members;
        setMessages(data.messages);
    }

    function setMessages(messages){
        $scope.messages = messages;
        $timeout(function(){
            $scope.$broadcast('scrollDown');
        }, 80);
    }

    function showContactList(){
        $scope.showContactList = true;
    }

    function cancelMemberAdd(){
        $scope.showContactList = false;
    }

    function saveMemberAdd(members){
        myHttpService.addMembersToConversation(id, members).then(function(){
            $state.reload();
        }, function (errorMessage){
            showErrorMessage(errorMessage);
        })
    }

    function leaveConversation(){
        myHttpService.removeStudentFromConversation(id)
            .then(function(){
                $state.go('main.messages.overview');
            }, function(error){
                showErrorMessage(error);
            });
    }

    function sendMessage(){
        if ($scope.message.trim() !== '') {
            hideInfo();
            hideErrorMessage();

            var message = {
                text: $scope.message,
                conversationId: id
            };

            $scope.message = '';

            myHttpService.addMessage(message).then(function () {
                loadConversation();
            }, function (errorMessage) {
            });
        }
    }

    function startUpdate() {
        return $interval(function() {
            myHttpService.getConversationById(id)
                .then(function (data) {
                    var messages = data.messages;
                    if (messages.length > $scope.messages.length){
                        setMessages(data.messages);
                    }

                    var members = data.members;
                    if (members.length !== $scope.members.length){
                        $scope.members = members;
                    }
                }, function (errorMessage) {
                    showErrorMessage(errorMessage);
                });
        }, 500);
    }

    function stopUpdate(process){
        $interval.cancel(process);
    }

    function startLoading(){
        $scope.loading = true;
    }

    function stopLoading(){
        $scope.loading = false;
    }

    function showInfo(info){
        $scope.info = info;
        $scope.showInfo = true;
    }

    function hideInfo(){
        $scope.info = '';
        $scope.showInfo = false;
    }

    function showErrorMessage(errorMessage){
        $scope.errorMessage = errorMessage;
        $scope.showErrorMessage = true;
    }

    function hideErrorMessage(){
        $scope.errorMessage = '';
        $scope.showErrorMessage = false;
    }
}

function conversationDirective() {
    return {
        restrict: 'E',
        scope: {
            id: '=?',
            message: '=?',
            messages: '=?',
            members: '=?',
            showInfo: '=?',
            showErrorMessage: '=?',
            info: '=?',
            errorMessage: '=?',
            seminarGroups:'=?',
            controller: '=?'
        },
        controller: ['$scope', '$state', '$stateParams', '$timeout', '$interval', 'seminarGroupService', 'updateService', 'myHttpService', conversationController],
        controllerAs: 'conversationCtrl',
        templateUrl: 'messages/template/conversation-template.html'
    }
}

angular.module('ComNet').directive('conversation', conversationDirective);