function contactListStudentController($scope) {
    var that = this;

    that.addStudentFromContactList = addStudentFromContactList;
    that.addStudentFromContactList = addStudentFromContactList;

    initialize();

    function initialize() {
        $scope.showAdd = true;

        var data = $scope.data;

        $scope.id = data.id;
        $scope.firstname = data.firstname;
        $scope.lastname = data.lastname;
        $scope.seminarGroup = data.seminarGroup;
    }

    function addStudentFromContactList() {
        $scope.contactListGroupController.addStudentFromContactList($scope.data);
    }
}

function contactListStudentDirective() {
    var that = this;

    return {
        restrict: 'E',
        require: '^contactListGroup',
        scope: {
            data: '=',
            id: '=?',
            firstname: '=?',
            lastname: '=?',
            seminarGroup: '=?'
        },
        controller: ['$scope', contactListStudentController],
        controllerAs: 'contactListStudentCtrl',
        templateUrl: '../../student-contact-list/template/contact-list-student-template.html',
        link: {
            pre: function (scope, element, attrs, controller) {
                scope.contactListGroupController = controller;
            }
        }
    }
}

angular.module('ComNet').directive('contactListStudent', contactListStudentDirective);