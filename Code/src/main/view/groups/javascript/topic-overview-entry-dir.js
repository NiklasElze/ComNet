'use strict';

function topicOverviewEntryController($scope, $state){
    var that = this;
    var id = 0;

    that.loadTopic = loadTopic;

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
}

function topicOverviewEntryDirective(){
    return{
        restrict: 'E',
        scope: {
            data: '='
        },
        controller: ['$scope', '$state', topicOverviewEntryController],
        controllerAs: 'topicOverviewEntryCtrl',
        templateUrl: 'groups/template/topic-overview-entry-template.html'
    }
}

angular.module('ComNet').directive('topicOverviewEntry', topicOverviewEntryDirective);
