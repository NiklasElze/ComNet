'use strict';

function groupsController($scope, $state, navMenuService) {
    var that = this;

    that.newGroup = newGroup;
    that.goToOverview = goToOverview;

    initialize();

    function initialize(){
        navMenuService.setToActive('groups');
    }

    function newGroup(){
        var params = {
            id: 0
        };

        $state.go('main.groups.edit', params);
    }

    function goToOverview(){
        if ($state.current.name === 'main.groups.overview'){
            $state.reload();
        } else{
            $state.go('main.groups.overview');
        }
    }
}

function groupsDirective() {
    return {
        restrict: 'E',
        scope: {
        },
        controller: ['$scope', '$state', 'navMenuService', groupsController],
        controllerAs: 'groupsCtrl',
        templateUrl: 'groups/template/groups-template.html'
    }
}

angular.module('ComNet').directive('groups', groupsDirective);