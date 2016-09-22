function conversationOverviewController($scope, $state, $interval, updateService, myHttpService) {
    var that = this;
    that.hideInfo = hideInfo;
    that.hideErrorMessage = hideErrorMessage;
    that.startUpdate = startUpdate;
    that.stopUpdate = stopUpdate;

    initialize();

    function initialize() {
        hideInfo();
        hideErrorMessage();
        startLoading();

        myHttpService.getConversationsOfStudent().then(function(data){
            sortConversations(data);
            stopLoading();
        }, function(errorMessage){
           showErrorMessage(errorMessage);
            stopLoading();
        });

        updateService.addUpdate('conversation-overview', that);
        updateService.setUpdate('conversation-overview');
    }

    function startUpdate(){
        return $interval(function(){
            myHttpService.getConversationsOfStudent().then(function(data){
                if (data.length > $scope.conversations.length){
                    sortConversations(data);
                }
            }, function(errorMessage){
                showErrorMessage(errorMessage);
            });
        }, 2000);
    }

    function stopUpdate(process){
        $interval.cancel(process);
    }

    function startLoading(){
        $scope.loading = true;
    }

    function stopLoading(){
        $scope.loading = false;
    }

    function hideInfo(){
        $scope.info = '';
        $scope.showInfo = false;
    }

    function showInfo(info){
        $scope.info = info;
        $scope.showInfo = true;
    }

    function showErrorMessage(errorMessage){
        $scope.errorMessage = errorMessage;
        $scope.showErrorMessage = true;
    }

    function hideErrorMessage(){
        $scope.errorMessage = '';
        $scope.showErrorMessage = false;
    }

    function sortConversations(conversations){

        var sortedConversations = [];

        angular.forEach(conversations, function(conversation){
            if (sortedConversations.length === 0){
                sortedConversations.push(conversation);
            }
            else{
                var index = getIndexInSortedList(sortedConversations, 0, conversation);
                sortedConversations.splice(index, 0, conversation);
            }
        });

        $scope.conversations = sortedConversations;
    }

    function getIndexInSortedList(conversations, index, conversation){
        if (conversation.messages.length === 0){
            return conversations.length;
        }

        if (conversations[index] === null || conversations[index] === undefined){
            return index;
        }

        if (conversations[index].messages.length > 0 && conversation.messages.length > 0 &&
            conversations[index].messages[conversations[index].messages.length - 1].createDate
            > conversation.messages[conversation.messages.length - 1].createDate){
            index = index + 1;
            index = getIndexInSortedList(conversations, index, conversation);
        }

        return index;
    }
}

function conversationOverviewDirective() {
    return {
        restrict: 'E',
        scope: {
            showInfo: '=?',
            showErrorMessage: '=?',
            loading: '=?',
            info: '=?',
            errorMessage: '=?',
            conversations: '=?'
        },
        controller: ['$scope', '$state', '$interval', 'updateService', 'myHttpService', conversationOverviewController],
        controllerAs: 'conversationOverviewCtrl',
        templateUrl: 'messages/template/conversation-overview-template.html'
    }
}

angular.module('ComNet').directive('conversationOverview', conversationOverviewDirective);