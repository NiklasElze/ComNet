function conversationMessageDirective() {
    var that = this;

    return {
        restrict: 'E',
        scope: {
            data: '=',
            sender: '=?',
            createDate: '=?'
        },
        controller: conversationMessageController,
        controllerAs: 'conversationMessageCtrl',
        templateUrl: 
    }
}

angular.module('ComNet').directive('conversationMessage', conversationMessageDirective);