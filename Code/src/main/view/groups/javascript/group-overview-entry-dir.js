'use strict';

function groupOverviewEntryController($scope, $state){
    var that = this;
    var id = 0;

    that.loadGroup = loadGroup;

    initialize();

    function initialize(){
        var data = $scope.data;

        id = data.id;
        $scope.name = data.name;
        $scope.members = data.members;
    }

    function loadGroup(){
        var params = {
            id: id
        };

        $state.go('main.groups.group', params);
    }
}

function groupOverviewEntryDirective(){
    return{
        restrict: 'E',
        scope:{
            data: '=',
            name: '=?',
            members: '=?'
        },
        controller: ['$scope', '$state', groupOverviewEntryController],
        controllerAs: 'groupOverviewEntryCtrl',
        templateUrl: 'groups/template/group-overview-entry-template.html'
    }
}

angular.module('ComNet').directive('groupOverviewEntry', groupOverviewEntryDirective);
