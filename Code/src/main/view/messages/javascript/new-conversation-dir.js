function newConversationController($scope, $state, $rootScope, principalService, myHttpService){
    var that = this;

    that.saveMemberAdd = saveMemberAdd;
    that.cancelMemberAdd = cancelMemberAdd;
    that.hideErrorMessage = hideErrorMessage;

    initialize();

    function initialize() {
        $scope.controller = that;
    }

    function cancelMemberAdd(){
        $state.go($rootScope.fromState, $rootScope.fromParams);
    }

    function saveMemberAdd(members){
        $scope.loading = true;

        var memberIds = [];

        memberIds.push(principalService.getIdentity().id);

        angular.forEach(members, function(member){
            memberIds.push(member.id);
        });

        var data = {
            id: 0,
            memberIds: memberIds
        };

        myHttpService.getConversationByMembers(data)
            .then(function(conversation){
                if (conversation && conversation.id && conversation.id > 0){
                    var params = {
                        id: conversation.id
                    };

                    $state.go('main.messages.conversation', params);
                } else{
                    var data = {
                        id: 0,
                        memberIds: memberIds
                    };

                    myHttpService.addOrUpdateConversation(data)
                        .then(function(){
                            var data = {
                                id: 0,
                                memberIds: memberIds
                            };

                            myHttpService.getConversationByMembers(data)
                                .then(function(conversation){
                                    var params = {
                                        id: conversation.id
                                    };

                                    $scope.loading = false;
                                    $state.go('main.messages.conversation', params);
                                }, function(error){
                                    showError(error);
                                    $scope.loading = false;
                                });
                        }, function(error){
                            showError(error);
                            $scope.loading = false;
                        });
                }
            }, function(error){
                showError(error);
                $scope.loading = false;
            });
    }

    function showError(error){
        $scope.errorMessage = error;
        $scope.showErrorMessage = true;
    }

    function hideErrorMessage(){
        $scope.errorMessage = '';
        $scope.showErrorMessage = false;
    }
}

function newConversationDirective() {
    return {
        restrict: 'E',
        scope: {
            controller: '=?',
            loading: '=?',
            showInfo: '=?',
            showErrorMessage: '=?',
            errorMessage: '=?'
        },
        controller: ['$scope', '$state', '$rootScope', 'principalService', 'myHttpService', newConversationController],
        controllerAs: 'newConversationCtrl',
        templateUrl: 'messages/template/new-conversation-template.html'
    }
}

angular.module('ComNet').directive('newConversation', newConversationDirective);