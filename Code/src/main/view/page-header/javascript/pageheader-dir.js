function pageHeaderController($scope, navMenuService, principalService) {
    var that = this;

    initialize();

    function initialize() {
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