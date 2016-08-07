function contactListStudentController($scope) {
    var that = this;
    var id = 0;
    var studentContactListController = {};

    that.add = add;

    initialize();

    function initialize() {
        var data = $scope.data;

        id = data.id;
        $scope.firstname = data.firstname;
        $scope.lastname = data.lastname;
        $scope.seminarGroup = data.seminargroup.name;
    }

    function add() {
        var item = {
            type: 'student',
            data: $scope.data
        };

        $scope.studentContactListController.addItemToMembers(item);
    }
}

function contactListStudentDirective() {
    return {
        restrict: 'E',
        require: '^studentContactList',
        scope: {
            data: '=',
            firstname: '=?',
            lastname: '=?',
            seminarGroup: '=?'
        },
        controller: ['$scope', contactListStudentController],
        controllerAs: 'contactListStudentCtrl',
        templateUrl: 'student-contact-list/template/contact-list-student-template.html',
        link: {
            pre: function (scope, element, attrs, controller) {
                scope.studentContactListController = controller;
            }
        }
    }
}

angular.module('ComNet').directive('contactListStudent', contactListStudentDirective);