function pageHeaderController($scope, navMenuService, principalService) {
    var that = this;

    that.showHeader = showHeader;

    initialize();

    function initialize() {
        var data = principalService.getIdentity();

        setStudentData(data);
    }

    function showHeader(show) {
        $scope.showHeader = show;
    }

    function setStudentData(data) {
        $scope.firstname = data.firstname;
        $scope.lastname = data.lastname;
        
        if (data.seminarGroup) {
            $scope.seminarGroup = data.seminarGroup.name;
        }
    }
}

function pageHeaderDirective() {
    var that = this;

    return {
        restrict: 'E',
        scope: {
            firstname: '=?',
            lastname: '=?',
            seminarGroup: '=?'
        },
        templateUrl: 'page-header/template/pageheader-template.html',
        controller: ['$scope', 'navMenuService', 'principalService', pageHeaderController],
        controllerAs: 'pageHeaderCtrl'
    }
}

angular.module('ComNet').directive('pageHeader', pageHeaderDirective);