function newConversationController($scope, messageService){
    var that = this;

    that.showButton = showButton;
    that.showContent = showContent;
    that.showNewConversationContent = showNewConversationContent;
    that.startNewConversation = startNewConversation;
    that.addStudentFromContactList = addStudentFromContactList;
    that.removeStudentFromMembers = removeStudentFromMembers;

    $scope.messageService = messageService;

    initialize();

    function initialize() {
        $scope.messageService = messageService;
        $scope.members = [];

        messageService.newConversationController.set(that);

        $scope.showButton = true;
        $scope.showContent = false;
    }

    function startNewConversation() {
        showNewConversationContent(true);

        messageService.startNewConversation();

        var historyEntry = {
            type: 'new-conversation'
        };

        messageService.addHistoryEntry(historyEntry);
    }

    function loadNewConversation() {
        showButton(false);
        showContent(true);
    }

    function showButton(show) {
        $scope.showButton = show;
    }

    function showContent(show) {
        $scope.showContent = show;
    }

    function showNewConversationContent(show) {
        showButton(!show);
        showContent(show);
    }

    function addStudentFromContactList(student) {
        $scope.members.push(student);
    }

    function removeStudentFromMembers(student) {
        $scope.members.splice($scope.members.indexOf(student), 1);
    }
}

function newConversationDirective() {
    var that = this;

    return {
        restrict: 'E',
        scope: {
            showButton: '=?',
            showContent: '=?',
            members: '=?',
            messageService: '=?'
        },
        controller: ['$scope', 'messageService', newConversationController],
        controllerAs: 'newConversationCtrl',
        templateUrl: '../../messages/template/new-conversation-template.html'
    }
}

angular.module('ComNet').directive('newConversation', newConversationDirective);