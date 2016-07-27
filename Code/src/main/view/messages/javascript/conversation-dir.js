function conversationController($scope, messageService) {
    var that = this;

    that.showConversation = showConversation;
    that.loadConversation = loadConversation;
    that.sendMessage = sendMessage;
    that.autoExpand = autoExpand;

    initialize();

    function initialize() {
        $scope.showConversation = false;
        $scope.members = [];
        messageService.conversationController.set(that);
    }

    function loadConversation(data){
        var data = $scope.data;

        $scope.id = data.id;
        $scope.members = data.members;
    }

    function sendMessage(){
        var message = $scope.message;
        alert(message);
    }

    function showConversation(show){
        $scope.showConversation = show;
    }

    function autoExpand (e) {
        var element = typeof e === 'object' ? e.target : document.getElementById(e);

        element.style.height = '1px';
        element.style.height = (22 + element.scrollHeight) + 'px';

        if (parseInt(element.style.height) >= parseInt(element.style.maxHeight)) {
            element.style.height = element.style.maxHeight;
            element.style.overflowY = 'scroll';
        }
        else {
            element.style.overflowY = 'hidden';
        }
    };
}

function conversationDirective() {
    var that = this;

    return {
        restrict: 'E',
        scope: {
            showConversation: '=?',
            message: '=?',
            id: '=?',
            members: '=?'
        },
        controller: ['$scope', 'messageService', conversationController],
        controllerAs: 'conversationCtrl',
        templateUrl: '../../messages/template/conversation-template.html'
    }
}

angular.module('ComNet').directive('conversation', conversationDirective);