'use strict';

function administrationOverviewController($scope, myHttpService, navMenuService) {
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
                    resolveSeminarGroups(seminarGroups);
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

    function resolveSeminarGroups(seminarGroups) {
        var orderedGroups = [];
        var groupsToRemove = [];

        angular.forEach(seminarGroups, function (seminarGroup) {
            seminarGroup.subGroups = [];

            if (seminarGroup.fatherGroup) {
                seminarGroup.fatherGroup.subGroups = [];
            }

            orderedGroups.push(seminarGroup);
        });

        angular.forEach(seminarGroups, function (seminarGroup) {
            
            if (seminarGroup.fatherGroup) {
                var indexOfFatherGroup = indexOfGroup(orderedGroups, seminarGroup.fatherGroup);

                orderedGroups[indexOfFatherGroup].subGroups.push(seminarGroup);

                groupsToRemove.push(seminarGroup);
            }
        });

        angular.forEach(groupsToRemove, function (seminarGroup) {
            var index = indexOfGroup(orderedGroups, seminarGroup);

            orderedGroups.splice(index, 1);
        });

        $scope.seminarGroups = orderedGroups;
    }

    function indexOfGroup(groups, group) {
        var index = 0;
        var returnIndex = false;

        angular.forEach(groups, function (seminarGroup) {
            if (seminarGroup.id === group.id) {
                returnIndex = true;
            }

            if (!returnIndex) {
                index++;
            }
        });

        if (returnIndex) {
            return index;
        }
        else {
            return -1;
        }
    }
}

function administrationOverviewDirective() {
    return {
        restrict: 'E',
        scope: {
            seminarGroups: '=?'
        },
        controller: ['$scope', 'myHttpService', 'navMenuService', administrationOverviewController],
        controllerAs: 'administrationOverviewCtrl',
        templateUrl: 'administration/template/administration-overview-template.html'
    }
}

angular.module('ComNet').directive('administrationOverview', administrationOverviewDirective);