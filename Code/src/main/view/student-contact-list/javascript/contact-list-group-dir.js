function contactListGroupController($scope) {
    var that = this;

    that.expand = expand;
    that.collapse = collapse;
    that.addStudentFromContactList = addStudentFromContactList;
    that.addGroupFromContactList = addGroupFromContactList;

    initialize();

    function initialize() {
        $scope.showGroup = true;
        toggleVisibility(false);

        var data = $scope.data;

        $scope.students = data.students;
        $scope.name = data.name;
        $scope.id = data.id;
    }

    function expand() {
        toggleVisibility(true);
    }

    function collapse() {
        toggleVisibility(false);
    }

    function toggleVisibility(showContent) {
        $scope.showExpand = !showContent;
        $scope.showCollapse = showContent;
        $scope.showGroupContent = showContent;
    }

    function addGroupFromContactList() {
        while ($scope.students.length > 0) {
            addStudentFromContactList($scope.students[0]);
        }
    }

    function addStudentFromContactList(student) {
        $scope.students.splice($scope.students.indexOf(student), 1);

        $scope.studentContactListController.addStudentFromContactList(student);
    }

    function showGroupContent(show) {
        $scope.showGroup = show;
    }
}

function contactListGroupDirective() {
    var that = this;

    return {
        restrict: 'E',
        require: '^studentContactList',
        scope: {
            data: '=',
            students: '=?',
            id: '=?',
            name: '=?',
            showGroup: '=?',
            showExpand: '=?',
            showCollapse: '=?',
            showGroupContent: '=?'
        },
        controller: ['$scope', contactListGroupController],
        controllerAs: 'contactListGroupCtrl',
        templateUrl: '../../student-contact-list/template/contact-list-group-template.html',
        link: {
            pre: function (scope, element, attrs, controller) {
                scope.studentContactListController = controller;
            }
        }
    }
}

angular.module('ComNet').directive('contactListGroup', contactListGroupDirective);