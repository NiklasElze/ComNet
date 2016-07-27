function conversationOverviewEntryController($scope) {
    var that = this;

    that.loadConversation = loadConversation;

    initialize();

    function initialize() {
        var data = $scope.data;
        
        $scope.id = data.id;
        $scope.members = data.members;
    }

    function loadConversation(id) {
        $scope.conversationOverviewCtrl.loadConversation(id);
    }
}

function conversationOverviewEntryDirective() {
    var that = this;

    return {
        restrict: 'E',
        require: '^conversationOverview',
        scope: {
            data: '=',
            id: '=?',
            conversationPartner: '=?'
        },
        controller: ['$scope', conversationOverviewEntryController],
        controllerAs: 'conversationOverviewEntryCtrl',
        templateUrl: 'messages/template/conversation-overview-entry-template.html',
        link: {
            pre: function (scope, element, attrs, controller) {
                scope.conversationOverviewCtrl = controller;
            }
        }
    }
}

angular.module('ComNet').directive('conversationOverviewEntry', conversationOverviewEntryDirective);