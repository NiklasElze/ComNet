'use strict';

function groupController($scope, $state, $stateParams, $interval, myHttpService, updateService){
    var that = this;
    var id = 0;

    that.hideErrorMessage = hideErrorMessage;
    that.hideInfo = hideInfo;
    that.loadTopic = loadTopic;
    that.newTopic = newTopic;
    that.startUpdate = startUpdate;
    that.stopUpdate = stopUpdate;

    initialize();

    function initialize(){
        id = $stateParams.id;

        if (id === 0){
            $state.go('main.groups.overview');
        }

        startLoading();

        myHttpService.getGroupById(id)
            .then(function(data){
                $scope.members = data.members;
                $scope.administrators = data.administrators;
                $scope.name = data.name;
                $scope.creatorName = data.creator.firstname + ' ' + data.creator.lastname;
                $scope.topics = data.topics;
                $scope.createDate = new Date($scope.createDate);

                stopLoading();
            }, function(error){
                stopLoading();
                showErrorMessage(error);
            });

        updateService.addUpdate('group', that);
        updateService.setUpdate('group');
    }

    function newTopic() {
        var params = {
            id: 0,
            groupId: id
        };

        $state.go('main.groups.edit-topic', params);
    }

    function loadTopic(){
        var params = {
            id: id
        };

        $state.go('main.groups.topic', params);
    }

    function startUpdate() {
        return $interval(function() {
            myHttpService.getGroupById(id)
                .then(function(data){
                    if (data.name !== $scope.name){
                        $scope.name = data.name;
                    }

                    if (data.members.length > $scope.members.length){
                        $scope.members = data.members;
                    }

                    if (data.administrators.length > $scope.administrators.length){
                        $scope.administrators = data.administrators;
                    }

                    if (data.topics.length > $scope.topics.length){
                        $scope.topics = data.topics;
                    }
                }, function(){
                });
        }, 2000);
    }

    function stopUpdate(process){
        $interval.cancel(process);
    }

    function showErrorMessage(error){
        $scope.showErrorMessage = true;
        $scope.errorMessage = error;
    }

    function showInfo(){
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

function groupDirective(){
    return{
        restrict: 'E',
        scope:{
            showErrorMessage: '=?',
            showInfo: '=?',
            info: '=?',
            errorMessage: '=?',
            loading: '=?',
            data: '=',
            name: '=?',
            members: '=?',
            administrators: '=?'
        },
        controller: ['$scope', '$state', '$stateParams', '$interval', 'myHttpService', 'updateService', groupController],
        controllerAs: 'groupCtrl',
        templateUrl: 'groups/template/group-template.html'
    }
}

angular.module('ComNet').directive('group', groupDirective);
