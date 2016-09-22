'use strict';

function groupController($scope, $state, $stateParams, $interval, myHttpService, updateService, principalService){
    var that = this;
    var id = 0;

    that.showErrorMessage = showErrorMessage;
    that.showInfo = showInfo;
    that.startLoading = startLoading;
    that.stopLoading = stopLoading;
    that.loadTopics = loadTopics;
    that.hideErrorMessage = hideErrorMessage;
    that.hideInfo = hideInfo;
    that.loadTopic = loadTopic;
    that.newTopic = newTopic;
    that.startUpdate = startUpdate;
    that.stopUpdate = stopUpdate;
    that.cancelMemberAdd = cancelMemberAdd;
    that.saveMemberAdd = saveMemberAdd;
    that.showContactList = showContactList;
    that.edit = edit;

    initialize();

    function initialize(){
        id = $stateParams.id;

        if (id === 0){
            $state.go('main.groups.overview');
        }

        $scope.id = id;
        $scope.controller = that;

        startLoading();

        myHttpService.getGroupById(id)
            .then(function(data){
                $scope.members = data.members;
                $scope.administrators = data.administrators;
                $scope.name = data.name;
                $scope.creatorName = data.creator.firstname + ' ' + data.creator.lastname;
                $scope.topics = data.topics;
                $scope.createDate = new Date($scope.createDate);

                $scope.userIsAdmin = userIsAdmin();
                stopLoading();
            }, function(error){
                stopLoading();
                showErrorMessage('Could not load group: ' + error);
            });

        updateService.addUpdate('group', that);
        updateService.setUpdate('group');
    }

    function edit(){
        var params = {
            id: id,
            userIsAdmin: $scope.userIsAdmin
        };

        $state.go('main.groups.edit', params);
    }

    function loadTopics(){
        myHttpService.getGroupById(id)
            .then(function(data){
                $scope.topics = data.topics;

                $scope.userIsAdmin = userIsAdmin();
            }, function(error){
                showErrorMessage('Could not reload topics: ' + error);
            });
    }

    function showContactList(){
        $scope.showContactList = true;
        $scope.showAdminList = false;
    }

    function cancelMemberAdd(){
        $scope.showContactList = false;
    }

    function saveMemberAdd(members){
        startLoading();
        var memberIds = [];
        var administratorIds = [];

        angular.forEach($scope.administrators, function(admin){
            administratorIds.push(admin.id);
        });

        angular.forEach($scope.members, function(member){
            memberIds.push(member.id);
        });

        angular.forEach(members, function(member){
            memberIds.push(member.id);
        });

        var data = {
            id: id,
            name: $scope.name,
            memberIds: memberIds,
            administratorIds: administratorIds
        };

        myHttpService.addOrUpdateGroup(data)
            .then(function(){
                $scope.showContactList = false;
                stopLoading();
            }, function(error){
                showErrorMessage('Could not add members: ' + error);
                stopLoading();
            });
    }

    function userIsAdmin(){
        for (var i = 0; i < $scope.administrators.length; i++){
            var admin = $scope.administrators[i];

            if (admin.id === principalService.getIdentity().id){
                return true;
            }
        }

        return false;
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
                        $scope.userIsAdmin = userIsAdmin();
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
            administrators: '=?',
            userIsAdmin: '=?',
            id: '=?',
            controller: '=?'
        },
        controller: ['$scope', '$state', '$stateParams', '$interval', 'myHttpService', 'updateService', 'principalService', groupController],
        controllerAs: 'groupCtrl',
        templateUrl: 'groups/template/group-template.html'
    }
}

angular.module('ComNet').directive('group', groupDirective);
