'use strict';

function editGroupController($scope, $stateParams, $state, $rootScope, myHttpService, principalService){
    var that = this;
    var id = 0;

    that.hideErrorMessage = hideErrorMessage;
    that.hideInfo = hideInfo;
    that.cancel = cancel;
    that.save = save;
    that.delete = deleteGroup;

    initialize();

    function initialize(){
        id = $stateParams.id;

        if (id > 0){
            startLoading();

            myHttpService.getGroupById(id)
                .then(function(data){
                    $scope.name = data.name;
                    $scope.members = data.members;
                    $scope.administrators = data.administrators;

                    $scope.showDelete = true;
                    stopLoading();
                }, function(error){
                    stopLoading();
                    showErrorMessage(error);
                });
        } else{
            $scope.name = '';
        }

        $scope.showContent = true;
        $scope.showSave = true;
    }

    function cancel(){
        $state.go($rootScope.fromState, $rootScope.fromParams);
    }

    function save(){
        var data = {
            id: id,
            name: $scope.name,
            creatorId: principalService.getIdentity().id,
            members: $scope.members,
            administrators: $scope.administrators
        };

        startLoading();

        myHttpService.addOrUpdateGroup(data)
            .then(function(){
                showInfo('Group successfully saved.');

                if (id === 0){
                    $scope.showSave = false;
                }
                stopLoading();
            }, function(error){
                showErrorMessage(error);
                stopLoading();
            });
    }

    function deleteGroup(){
        startLoading();

        myHttpService.deleteGroup(id)
            .then(function(){
                showInfo('Group successfully deleted.');

                $scope.showSave = false;
                $scope.showDelete = false;
                $scope.showContent = false;
                stopLoading();
            }, function(error){
                showErrorMessage(error);
                stopLoading();
            });
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

    function startLoading(){
        $scope.loading = true;
    }

    function stopLoading(){
        $scope.loading = false;
    }
}

function editGroupDirective(){
    return{
        restrict: 'E',
        scope:{
            showNew: '=?',
            showSave: '=?',
            name: '=?',
            members: '=?',
            administrators: '=?',
            showErrorMessage: '=?',
            errorMessage: '=?',
            showInfo: '=?',
            info: '=?'
        },
        controller: ['$scope', '$stateParams', '$state', '$rootScope', 'myHttpService', 'principalService', editGroupController],
        controllerAs: 'editGroupCtrl',
        templateUrl: 'groups/template/edit-group-template.html'
    }
}

angular.module('ComNet').directive('editGroup', editGroupDirective);
