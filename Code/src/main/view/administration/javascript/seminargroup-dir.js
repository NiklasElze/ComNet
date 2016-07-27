'use strict';

function seminarGroupController($scope, $state, myHttpService) {
    var that = this;
    that.load = load;
    that.delete = deleteGroup;
    that.collapse = collapse;
    that.expand = expand;
    that.showInfo = showInfo;
    that.hideInfo = hideInfo;
    that.showErrorMessage = showErrorMessage;
    that.hideErrorMessage = hideErrorMessage;
    that.loadStudents = loadStudents;
    that.startLoading = startLoading;
    that.stopLoading = stopLoading;

    initialize();

    function initialize() {
        var data = $scope.data;

        $scope.students = data.students;
        $scope.id = data.id;
        $scope.name = data.name;
        $scope.fatherGroup = data.fatherGroup;
        $scope.subGroups = data.subGroups;

        $scope.showStudents = false;
    }

    function load() {
        var params = {
            id: $scope.id,
            name: $scope.name,
            fatherGroup: $scope.fatherGroup
        };

        $state.go('main.administration.seminargroup', params);
    }

    function deleteGroup() {
        $scope.administrationOverviewCtrl.hideInfo();
        $scope.administrationOverviewCtrl.hideErrorMessage();
        $scope.administrationOverviewCtrl.startLoading();

        myHttpService.deleteSeminarGroup($scope.id)
            .then(function () {
                $scope.administrationOverviewCtrl.loadSeminarGroups();
                $scope.administrationOverviewCtrl.showInfo("Successfully deleted.");
                $scope.administrationOverviewCtrl.stopLoading();
            }, function (errorMessage) {
                $scope.administrationOverviewCtrl.hideInfo();
                $scope.administrationOverviewCtrl.showErrorMessage(errorMessage);
                $scope.administrationOverviewCtrl.stopLoading();
            });
    }

    function expand() {
        $scope.showStudents = true;
    }

    function collapse() {
        $scope.showStudents = false;
    }

    function loadStudents() {
        myHttpService.getStudentsOfSeminarGroup($scope.id)
            .then(function (data) {
                $scope.students = data;
            }, function (errorMessage) {
                $scope.administrationOverviewCtrl.hideInfo();
                $scope.administrationOverviewCtrl.showErrorMessage(errorMessage);
            });
    }

    function showInfo(info) {
        $scope.administrationOverviewCtrl.showInfo(info);
    }

    function hideInfo() {
        $scope.administrationOverviewCtrl.hideInfo();
    }

    function showErrorMessage(errorMessage) {
        $scope.administrationOverviewCtrl.showErrorMessage(errorMessage);
    }

    function hideErrorMessage() {
        $scope.administrationOverviewCtrl.hideErrorMessage();
    }

    function startLoading() {
        $scope.administrationOverviewCtrl.startLoading();
    }

    function stopLoading() {
        $scope.administrationOverviewCtrl.stopLoading();
    }
}

function seminarGroupDirective() {
    var that = this;

    return {
        restrict: 'E',
        require: '^administrationOverview',
        scope: {
            data: '=',
            students: '=?',
            id: '=?',
            name: '=?',
            fatherGroup: '=?',
            subGroups: '=?',
            showStudents: '=?'
        },
        controller: ['$scope', '$state', 'myHttpService', seminarGroupController],
        controllerAs: 'seminarGroupCtrl',
        templateUrl: 'administration/template/seminargroup-template.html',
        link: {
            pre: function (scope, element, attrs, controller) {
                scope.administrationOverviewCtrl = controller;
            }
        }
    }
}

angular.module('ComNet').directive('seminarGroup', seminarGroupDirective);