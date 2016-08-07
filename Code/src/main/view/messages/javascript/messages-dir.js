function messagesController($state, titleService, navMenuService) {
    var that = this;
    that.newConversation = newConversation;

    initialize();

    function initialize() {
        navMenuService.setToActive('messages');
        titleService.setTitle('ComNet | Messages');
    }

    function newConversation(){
        $state.go('main.messages.new');
    }
}

function messagesDirective() {
    return {
        restrict: 'E',
        scope: {
            showContent: '=?',
            showGoBack: '=?',
            showGoForward: '=?'
        },
        controller: ['$state', 'titleService', 'navMenuService', messagesController],
        controllerAs: 'messagesCtrl',
        templateUrl: 'messages/template/messages-template.html'
    }
}

angular.module('ComNet').directive('messages', messagesDirective);