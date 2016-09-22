'use strict';

function editTopicController($scope, $state, $stateParams, $rootScope, myHttpService, principalService){
    var that = this;
    var id = 0;
    var groupId = 0;

    that.hideErrorMessage = hideErrorMessage;
    that.hideInfo = hideInfo;
    that.save = save;
    that.cancel = cancel;
    that.delete = deleteTopic;

    initialize();

    function initialize(){
        id = $stateParams.id;

        $scope.showContent = true;

        if (id > 0){
            myHttpService.getTopicById(id)
                .then(function(data){
                    $scope.name = data.name;
                    groupId = data.group.id;

                    $scope.showDelete = $stateParams.userIsAdmin;
                }, function(error){
                    $scope.showSave = false;
                    showErrorMessage(error);
                });
        } else{
            $scope.name = '';

            groupId = $stateParams.groupId;

            if (groupId === 0){
                $state.go($rootScope.fromState, $rootScope.fromParams);
            }
        }

        $scope.showSave = true;
    }

    function save(){
        var data = {
            id: id,
            groupId: groupId,
            creatorId: principalService.getIdentity().id,
            name: $scope.name
        };

        startLoading();

        myHttpService.addOrUpdateTopic(data)
            .then(function(){
                showInfo('Topic successfully saved.');
                stopLoading();

                if (id === 0){
                    $scope.showSave = false;
                }
            }, function(error){
               showErrorMessage(error);
                stopLoading();
            });
    }

    function cancel(){
        $state.go($rootScope.fromState, $rootScope.fromParams);
    }

    function deleteTopic() {
        startLoading();

        myHttpService.deleteTopic(id)
            .then(function () {
                showInfo('Topic successfully deleted.');

                $scope.showContent = false;
                $scope.showSave = false;
                $scope.showDelete = false;
                stopLoading();
            }, function (error) {
                showErrorMessage(error);
                stopLoading();
            });
    }

    function startLoading(){
        $scope.loading = true;
    }

    function stopLoading(){
        $scope.loading = false;
    }

    function showErrorMessage(error){
        $scope.showErrorMessage = true;
        $scope.errorMessage = error;
    }

    function showInfo(info){
        $scope.showInfo = true;
        $scope.info = info;
    }

    function hideErrorMessage(){
        $scope.showErrorMessage = false;
        $scope.errorMessage = '';
    }

    function hideInfo(){
        $scope.showInfo = false;
        $scope.info = '';
    }
}

function editTopicDirective(){
    return{
        restrict: 'E',
        scope:{
            showNew: '=?',
            showSave: '=?',
            name: '=?',
            showErrorMessage: '=?',
            errorMessage: '=?',
            showInfo: '=?',
            info: '=?'
        },
        controller: editTopicController,
        controllerAs: 'editTopicCtrl',
        templateUrl: 'groups/template/edit-topic-template.html'
    }
}

angular.module('ComNet').directive('editTopic', editTopicDirective);