'use strict';

function studentController($scope, $state, myHttpService) {
    var that = this;
    that.load = load;
    that.delete = deleteStudent;

    initialize();

    function initialize() {
        var data = $scope.data;

        $scope.id = data.id;
        $scope.firstname = data.firstname;
        $scope.lastname = data.lastname;
        $scope.seminarGroup = data.seminargroup.name;
    }

    function load() {
        $scope.seminarGroupCtrl.startLoading();

        var params = {
            id: $scope.id,
            isStudent: true
        };

        $state.go('main.administration.user', params);
    }

    function deleteStudent() {
        $scope.seminarGroupCtrl.startLoading();
        $scope.seminarGroupCtrl.hideErrorMessage;

        myHttpService.deleteStudent($scope.id)
            .then(function () {
                $scope.seminarGroupCtrl.loadStudents();
                $scope.seminarGroupCtrl.showInfo('Successfully deleted.');
                $scope.seminarGroupCtrl.stopLoading();
            }, function (errorMessage) {
                $scope.seminarGroupCtrl.hideInfo();
                $scope.seminarGroupCtrl.showErrorMessage(errorMessage);
                $scope.seminarGroupCtrl.stopLoading();
            });
    }
}

function studentDirective() {
    var that = this;

    return {
        restrict: 'E',
        require: '^seminarGroup',
        scope: {
            data: '=',
            firstname: '=?',
            lastname: '=?'
        },
        controller: ['$scope', '$state', 'myHttpService', studentController],
        controllerAs: 'studentCtrl',
        templateUrl: 'administration/template/student-template.html',
        link: {
            pre: function (scope, element, attrs, controller) {
                scope.seminarGroupCtrl = controller;
            }
        }
    }
}

angular.module('ComNet').directive('student', studentDirective);