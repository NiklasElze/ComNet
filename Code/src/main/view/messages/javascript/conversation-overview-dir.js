function conversationOverviewController($scope, messageService) {
    var that = this;

    that.showOverview = showOverview;
    that.loadConversation = loadConversation;
    that.loadConversationOverview = loadConversationOverview;

    initialize();

    function initialize() {
        $scope.showOverview = true;
        $scope.conversations = [];

        messageService.conversationOverviewController.set(that);

        loadConversationOverview();
    }

    function loadConversationOverview() {
        var conversations = getConversations();

        $scope.conversations = getConversations();
    }

    function loadConversation(id) {
        var historyEntry = {
            type: 'conversation',
            id: id
        };

        messageService.addHistoryEntry(historyEntry);

        messageService.loadConversation(id);
    }

    function showOverview(show) {
        $scope.showOverview = show;
    }

    function getConversations() {
        // -- API --
        return [{ id: 1, members: [{ id: 1, firstname: 'Max', lastname: 'Mustermann', seminarGroup: { id: 1, name: 'CS13' } }, { id: 2, firstname: 'Erika', lastname: 'Mustermann', seminarGroup: { id: 2, name: 'CS12' } }] }, { id: 2, members: [{ id: 3, firstname: 'Hans', lastname: 'Meyer', seminarGroup: { id: 3, name: 'IT11' } }] }];
    }
}

function conversationOverviewDirective() {
    var that = this;

    return {
        restrict: 'E',
        scope: {
            showOverview: '=?',
            showGoBack: '=?',
            showGoForward: '=?',
            conversations: '=?'
        },
        controller: ['$scope', 'messageService', conversationOverviewController],
        controllerAs: 'conversationOverviewCtrl',
        templateUrl: 'messages/template/conversation-overview-template.html'
    }
}

angular.module('ComNet').directive('conversationOverview', conversationOverviewDirective);