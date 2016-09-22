'use strict';

function topicOverviewEntryController($scope, $state, myHttpService){
    var that = this;
    var id = 0;

    that.loadTopic = loadTopic;
    that.edit = edit;
    that.delete = deleteTopic;

    initialize();

    function initialize(){
        var data = $scope.data;
        id = data.id;

        $scope.name = data.name;
    }

    function loadTopic(){
        var params = {
            id: id
        };

        $state.go('main.groups.topic', params);
    }

    function edit(){
        var params = {
            id: id,
            userIsAdmin: $scope.userIsAdmin
        };

        $state.go('main.groups.edit-topic', params);
    }

    function deleteTopic() {
        $scope.groupController.startLoading();

        myHttpService.deleteTopic(id)
            .then(function () {
                $scope.groupController.showInfo('Topic successfully deleted.');
                $scope.groupController.loadTopics();
                $scope.groupController.stopLoading();
            }, function (error) {
                $scope.groupController.showErrorMessage(error);
                $scope.groupController.stopLoading();
            });
    }
}

function topicOverviewEntryDirective() {
    return {
        restrict: 'E',
        require: '^group',
        scope: {
            data: '=',
            userIsAdmin: '='
        },
        controller: ['$scope', '$state', 'myHttpService', topicOverviewEntryController],
        controllerAs: 'topicOverviewEntryCtrl',
        templateUrl: 'groups/template/topic-overview-entry-template.html',
        link: {
            pre: function (scope, element, attrs, controller) {
                scope.groupController = controller;
            }
        }
    }
}

angular.module('ComNet').directive('topicOverviewEntry', topicOverviewEntryDirective);
