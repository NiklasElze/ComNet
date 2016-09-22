'use strict';

function groupOverviewController($scope, myHttpService){
    var that = this;

    that.hideInfo = hideInfo;
    that.hideErrorMessage = hideErrorMessage;

    initialize();

    function initialize(){
        startLoading();

        myHttpService.getGroupsOfStudent()
            .then(function(data){
                stopLoading();
                $scope.groups = data;
            }, function(error){
                stopLoading();
                showErrorMessage(error);
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

    function hideInfo(){
        $scope.showInfo = false;
        $scope.info = '';
    }

    function hideErrorMessage(){
        $scope.showErrorMessage = false;
        $scope.error = '';
    }

    function startLoading(){
        $scope.loading = true;
    }

    function stopLoading(){
        $scope.loading = false;
    }
}

function groupOverviewDirective(){
    return{
        restrict: 'E',
        scope:{
            groups: '=?',
            showErrorMessage: '=?',
            showInfo: '=?',
            errorMessage: '=?',
            info: '=?',
            loading: '=?'
        },
        controller: groupOverviewController,
        controllerAs: 'groupOverviewCtrl',
        templateUrl: 'groups/template/group-overview-template.html'
    }
}

angular.module('ComNet').directive('groupOverview', groupOverviewDirective);
