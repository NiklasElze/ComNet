'use strict';

function groupsController($scope, navMenuService) {
    var that = this;

    that.hideErrorMessage = hideErrorMessage;

    initialize();

    function initialize(){
        navMenuService.setToActive('groups');


    }

    function showErrorMessage(error){
        $scope.errorMessage = error;
        $scope.showError = true;
    }

    function hideErrorMessage(){
        $scope.errorMessage = '';
        $scope.showErrorMessage = false;
    }
}

function groupsDirective() {
    return {
        restrict: 'E',
        scope: {
            groups: '=?',
            errorMessage: '=?',
            showErrorMessage: '=?',
            info: '=?',
            showInfo: '=?'
        },
        controller: ['$scope', 'navMenuService', groupsController],
        controllerAs: 'groupsCtrl',
        templateUrl: 'groups/template/groups-template.html'
    }
}

angular.module('ComNet').directive('groups', groupsDirective);