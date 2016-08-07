function conversationOverviewEntryController($scope, $state) {
    var that = this;
    var id = 0;

    that.loadConversation = loadConversation;

    initialize();

    function initialize() {
        var data = $scope.data;
        
        id = data.id;
        $scope.members = data.members;

        if (data.messages && data.messages.length > 0){
            setLastMessage(data.messages[data.messages.length - 1]);
        }
    }

    function loadConversation() {
        var params = {
            id: id
        };

        $state.go('main.messages.conversation', params);
    }

    function setLastMessage(lastMessage){
        $scope.lastMessageDate = new Date(lastMessage.createDate);

        var message = lastMessage.sender.firstname + ' ' + lastMessage.sender.lastname + ': ' + lastMessage.text.substring(0, 30);

        if (lastMessage.text.length > 40){
            message = message.substring(0, 37) + '...';
        }

        $scope.lastMessage = message;
        $scope.showLastMessage = true;
    }
}

function conversationOverviewEntryDirective() {
    return {
        restrict: 'E',
        require: '^conversationOverview',
        scope: {
            data: '=',
            id: '=?',
            lastMessageDate: '=?',
            lastMessage: '=?',
            showLastMessage: '=?'
        },
        controller: ['$scope', '$state', conversationOverviewEntryController],
        controllerAs: 'conversationOverviewEntryCtrl',
        templateUrl: 'messages/template/conversation-overview-entry-template.html',
        link:{
            pre: function (scope, elem, attr){
                elem.on('mouseenter', function(){
                    angular.element(elem.children()[0]).css('background-color', '#eee');
                }).on('mouseleave', function(){
                    angular.element(elem.children()[0]).css('background-color', '#fff');
                });
            }
        }
    }
}

angular.module('ComNet').directive('conversationOverviewEntry', conversationOverviewEntryDirective);