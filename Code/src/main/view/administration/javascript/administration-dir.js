function administrationController($scope, $state) {
    var that = this;
    that.addSeminarGroup = addSeminarGroup;
    that.addStudent = addStudent;
    that.addUser = addUser;
    that.goToOverview = goToOverview;

    initialize();

    function initialize() {
    }

    function addSeminarGroup() {
        var params = {
            id: 0,
            name: '',
            fatherGroup: null
        };

        $state.go('main.administration.seminargroup', params);
    }

    function addUser() {
        params = {
            id: 0,
            isStudent: false
        };

        $state.go('main.administration.user', params);
    }

    function addStudent() {
        var params = {
            id: 0,
            isStudent: true
        };

        $state.go('main.administration.user', params);
    }

    function goToOverview() {
        $state.go('main.administration.overview');
    }
}

function administrationDirective() {
    return {
        restrict: 'E',
        scope: {
            userIsAdmin: '=?',
            userIsPersonInAuthority: '=?'
        },
        controller: ['$scope', '$state', administrationController],
        controllerAs: 'administrationCtrl',
        templateUrl: 'administration/template/administration-template.html'
    }
}

angular.module('ComNet').directive('administration', administrationDirective);