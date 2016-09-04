'use strict';

function custodianController($scope, $state, myHttpService) {
    var that = this;
    that.load = loadCustodian;
    that.delete = deleteCustodian;

    initialize();

    function initialize() {
        var data = $scope.data;
        
        $scope.id = data.id;
        $scope.firstname = data.firstname;
        $scope.lastname = data.lastname;
        $scope.privileges = data.privileges;
    }

    function loadCustodian() {
        var params = {
            id: $scope.id,
            isStudent: false
        };

        $state.go('main.administration.user', params);
    }

    function deleteCustodian() {
        var id = $scope.id;

        $scope.administrationOverviewCtrl.hideInfo();
        $scope.administrationOverviewCtrl.hideErrorMessage();
        $scope.administrationOverviewCtrl.startLoading();

        myHttpService.deleteUser(id)
            .then(function () {
                $scope.administrationOverviewCtrl.loadCustodians();
                $scope.administrationOverviewCtrl.showInfo('Successfully deleted.');
                $scope.administrationOverviewCtrl.stopLoading();
            }, function (errorMessage) {
                $scope.administrationOverviewCtrl.hideInfo();
                $scope.administrationOverviewCtrl.showErrorMessage(errorMessage);
                $scope.administrationOverviewCtrl.stopLoading();
            });
    }
}

function custodianDirective() {
    var that = this;

    return {
        restrict: 'E',
        require: '^administrationOverview',
        scope: {
            data: '='
        },
        controller: ['$scope', '$state', 'myHttpService', custodianController],
        controllerAs: 'custodianCtrl',
        templateUrl: 'administration/template/custodian-template.html',
        link: {
            pre: function (scope, element, attrs, controller) {
                scope.administrationOverviewCtrl = controller;
            }
        }
    }
}

angular.module('ComNet').directive('custodian', custodianDirective);