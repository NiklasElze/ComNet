function studentContactListController($scope) {
    var that = this;

    that.addStudentFromContactList = addStudentFromContactList;
    that.addStudentToContactList = addStudentToContactList;
    that.loadContactList = loadContactList;

    initialize();

    function initialize() {
        $scope.service.studentContactListController.set(that);

        loadContactList();
    }

    function addStudentFromContactList(student) {
        $scope.service.addStudentFromContactList(student);
    }

    function addStudentToContactList(student) {
        angular.forEach($scope.seminarGroups, function (seminarGroup) {
            if (seminarGroup.id === student.seminarGroup.id) {
                seminarGroup.students.push(student);
            }
        });
    }

    function loadContactList() {
        $scope.seminarGroups = getContactList();
    }

    function getContactList() {
        // -- API --

        return [{ id: 1, name: 'CS13', students: [{ id: 1, firstname: 'Max', lastname: 'Mustermann', seminarGroup: { id: 1, name: 'cs13-1' } }, { id: 2, firstname: 'Erika', lastname: 'Mustermann', seminarGroup: { id: 1, name: 'cs13-2' } }] }, { id: 2, name: 'CS12', students: [{ id: 3, firstname: 'Moritz', lastname: 'Mustermann', seminarGroup: { id: 2, name: 'cs12' } }] }];
    }
}

function studentContactListDirective() {
    var that = this;

    return {
        restrict: 'E',
        scope: {
            service: '=',
            excludeStudents: '=',
            seminarGroups: '=?'
        },
        controller: studentContactListController,
        controllerAs: 'studentContactListCtrl',
        templateUrl: '../../student-contact-list/template/student-contact-list-template.html'
    }
}

angular.module('ComNet').directive('studentContactList', studentContactListDirective);