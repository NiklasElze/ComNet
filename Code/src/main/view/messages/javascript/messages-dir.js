function messagesController($scope, navMenuService, messageService) {
    var that = this;

    initialize();

    return {
    }

    function initialize() {
        navMenuService.setToActive('messages');
    }
}

function messagesDirective() {
    var that = this;

    return {
        restrict: 'E',
        scope: {
            showContent: '=?',
            showGoBack: '=?',
            showGoForward: '=?'
        },
        controller: ['$scope', 'navMenuService', 'messageService', messagesController],
        controllerAs: 'messagesCtrl',
        templateUrl: 'messages/template/messages-template.html'
    }
}

angular.module('ComNet').directive('messages', messagesDirective);