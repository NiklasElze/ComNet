function homeController($scope, navMenuService) {
    var that = this;

    that.showContent = showContent;

    initialize();

    function initialize() {
        $scope.showContent = true;
        $scope.firstname = 'Niklas';
        $scope.lastname = 'Elze';

        navMenuService.setToActive('home');
    }

    function showContent(show) {
        $scope.showContent = show;
    }
}

function homeDirective() {
    var that = this;

    return {
        restrict: 'E',
        scope: {
            firstname: '=?',
            lastname: '=?',
            showContent: '=?'
        },
        controller: ['$scope', 'navMenuService', homeController],
        controllerAs: 'mainPageCtrl',
        templateUrl: 'home/template/home-template.html'
    }
}

angular.module('ComNet').directive('home', homeDirective);