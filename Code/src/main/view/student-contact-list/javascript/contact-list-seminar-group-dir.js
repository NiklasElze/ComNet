function contactListSeminarGroupController($scope) {
    var that = this;
    var studentContactListController = {};
    that.expand = expand;
    that.collapse = collapse;
    that.add = add;

    initialize();

    function initialize(){
        var data = $scope.data;

        $scope.name = data.name;
        $scope.students = data.students;
        $scope.subGroups = data.subGroups;
    }

    function expand(){
        $scope.showContent = true;
    }

    function collapse(){
        $scope.showContent = false;
    }

    function add(){
        var item = {
            type: 'seminargroup',
            data: $scope.data
        };

        $scope.studentContactListController.addItemToMembers(item);
    }
}

function contactListSeminarGroupDirective() {
    return {
        restrict: 'E',
        require: '^studentContactList',
        scope: {
            data: '=',
            name: '=?',
            showContent: '=?',
            students: '=?',
            subGroups: '=?'
        },
        controller: ['$scope', contactListSeminarGroupController],
        controllerAs: 'contactListSeminarGroupCtrl',
        templateUrl: 'student-contact-list/template/contact-list-seminar-group-template.html',
        link: {
            pre: function (scope, element, attrs, controller) {
                scope.studentContactListController = controller;
            }
        }
    }
}

angular.module('ComNet').directive('contactListSeminarGroup', contactListSeminarGroupDirective);