function messagesController($state, titleService, navMenuService) {
    var that = this;
    that.newConversation = newConversation;
    that.goToOverview = goToOverview;

    initialize();

    function initialize() {
        navMenuService.setToActive('messages');
        titleService.setTitle('ComNet | Messages');
    }

    function newConversation(){
        $state.go('main.messages.new');
    }

    function goToOverview(){
        if ($state.current.name === 'main.messages.overview'){
            $state.reload();
        } else{
            $state.go('main.messages.overview');
        }
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