'use strict';

function administrationOverviewController($scope, seminarGroupService, myHttpService, navMenuService) {
    var that = this;
    that.showInfo = showInfo;
    that.showErrorMessage = showErrorMessage;
    that.hideErrorMessage = hideErrorMessage;
    that.hideInfo = hideInfo;
    that.loadSeminarGroups = loadSeminarGroups;
    that.loadCustodians = loadCustodians;
    that.startLoading = startLoading;
    that.stopLoading = stopLoading;

    initialize();

    function initialize() {
        navMenuService.setToActive('administration');
        $scope.userIsAdmin = $scope.$parent.userIsAdmin;
        $scope.userIsPersonInAuthority = $scope.$parent.userIsPersonInAuthority;
        startLoading();

        if ($scope.userIsPersonInAuthority) {
            myHttpService
                .getSeminarGroups()
                .then(function (seminarGroups) {
                    $scope.seminarGroups = seminarGroupService.orderGroups(seminarGroups);
                    stopLoading();
                }, function (errorMessage) {
                    showErrorMessage(errorMessage);
                    stopLoading();
                });
        }

        if ($scope.userIsAdmin) {
            myHttpService
                .getCustodians()
                .then(function (custodians) {
                    setCustodians(custodians);
                    stopLoading();
                }, function (errorMessage) {
                    showErrorMessage(errorMessage);
                    stopLoading();
                });
        }
    }

    function loadSeminarGroups() {
        myHttpService
            .getSeminarGroups()
            .then(function (seminarGroups) {
                resolveSeminarGroups(seminarGroups);
            }, function (errorMessage) {
                showErrorMessage(errorMessage);
            });
    }

    function loadCustodians() {
        myHttpService
            .getCustodians()
            .then(function (custodians) {
                setCustodians(custodians);
            }, function (errorMessage) {
                showErrorMessage(errorMessage);
            });
    }

    function setCustodians(custodians) {
        $scope.custodians = custodians;
    }

    function showErrorMessage(message) {
        $scope.errorMessage = message;
        $scope.showErrorMessage = true;
    }

    function hideErrorMessage() {
        $scope.errorMessage = '';
        $scope.showErrorMessage = false;
    }

    function showInfo(info) {
        $scope.info = info;
        $scope.showInfo = true;
        $scope.loading = true;
    }

    function hideInfo() {
        $scope.info = '';
        $scope.showInfo = false;
        $scope.loading = false;
    }

    function startLoading() {
        $scope.loading = true;
    }

    function stopLoading() {
        $scope.loading = false;
    }
}

function administrationOverviewDirective() {
    return {
        restrict: 'E',
        scope: {
            seminarGroups: '=?'
        },
        controller: ['$scope', 'seminarGroupService', 'myHttpService', 'navMenuService', administrationOverviewController],
        controllerAs: 'administrationOverviewCtrl',
        templateUrl: 'administration/template/administration-overview-template.html'
    }
}

angular.module('ComNet').directive('administrationOverview', administrationOverviewDirective);