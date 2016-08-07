'use strict';

function conversationMessageController($scope){
    var that = this;

    initialize();

    function initialize(){
        var data = $scope.data;

        $scope.firstname = data.sender.firstname;
        $scope.lastname = data.sender.lastname;
        $scope.seminarGroup = data.sender.seminargroup.name;
        $scope.text = data.text.trim();
        $scope.createDate = new Date(data.createDate);
    }
}

function conversationMessageDirective() {
    return {
        restrict: 'E',
        scope: {
            data: '=',
            createDate: '=?',
            firstname: '=?',
            lastname: '=?',
            seminarGroup: '=?'
        },
        controller: ['$scope', conversationMessageController],
        controllerAs: 'conversationMessageCtrl',
        templateUrl: 'messages/template/conversation-message-template.html'
    }
}

angular.module('ComNet').directive('conversationMessage', conversationMessageDirective);