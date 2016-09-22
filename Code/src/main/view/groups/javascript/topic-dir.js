'use strict';

function topicController($scope, $stateParams, $state, $interval, myHttpService, principalService, updateService){
    var that = this;
    var id = 0;

    that.hideErrorMessage = hideErrorMessage;
    that.hideInfo = hideInfo;
    that.addEntry = addEntry;
    that.startUpdate = startUpdate;
    that.stopUpdate = stopUpdate;
    that.goToGroup = goToGroup;

    initialize();

    function initialize(){
        id = $stateParams.id;

        if (id === 0){
            $state.go('main.groups.overview');
        }

        startLoading();

        myHttpService.getTopicById(id)
            .then(function(data){
                $scope.groupName = data.group.name;
                $scope.creatorName = data.group.creator.firstname + ' ' + data.group.creator.lastname;
                $scope.name = data.name;
                $scope.entries = data.entries;
                $scope.createDate = new Date(data.createDate);
                $scope.groupId = data.group.id;

                stopLoading();
            }, function(error){
                showErrorMessage(error);
                stopLoading();
            });

        updateService.addUpdate('topic', that);
        updateService.setUpdate('topic');
    }

    function goToGroup(){
        var params = {
            id: $scope.groupId
        };

        $state.go('main.groups.group', params);
    }

    function addEntry(){
        var data = {
            senderId: principalService.getIdentity().id,
            text: $scope.entry,
            topicId: id
        };

        myHttpService.addTopicEntry(data);

        $scope.entry = '';
    }

    function startUpdate() {
        return $interval(function() {
            myHttpService.getTopicById(id)
                .then(function(data){
                    if (data.name !== $scope.name){
                        $scope.name = data.name;
                    }

                    if (data.entries.length > $scope.entries.length){
                        $scope.entries = data.entries;
                    }
                }, function(){
                });
        }, 500);
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

function topicDirective(){
    return{
        restrict: 'E',
        scope:{
            loading: '=?',
            showErrorMessage: '=?',
            showInfo: '=?',
            hideErrorMessage: '=?',
            hideInfo: '=?',
            creatorName: '=?',
            groupName: '=?',
            createDate: '=?',
            entries: '=?',
            name: '=?',
            entry: '=?'
        },
        controller: ['$scope', '$stateParams', '$state', '$interval', 'myHttpService', 'principalService', 'updateService', topicController],
        controllerAs: 'topicCtrl',
        templateUrl: 'groups/template/topic-template.html'
    }
}

angular.module('ComNet').directive('topic', topicDirective);
