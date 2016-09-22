function memberItemController($scope) {
    var that = this;

    that.removeStudent = removeStudent;

    initialize();

    function initialize() {
        var data = $scope.data;

        $scope.id = data.id;
        $scope.firstname = data.firstname;
        $scope.lastname = data.lastname;
        $scope.seminarGroup = data.seminargroup;
    }

    function removeStudent() {
        $scope.service.addStudentToContactList($scope.data);
    }
}

function memberItemDirective() {
    var that = this;

    return {
        restrict: 'E',
        scope: {
            service: '=',
            data: '=',
            showremove: '=',
            id: '=?',
            firstname: '=?',
            lastname: '=?',
            seminarGroup: '=?'
        },
        controller: ['$scope', memberItemController],
        controllerAs: 'memberItemCtrl',
        templateUrl: 'member-item/template/member-item-template.html',
    }
}

angular.module('ComNet').directive('memberItem', memberItemDirective);