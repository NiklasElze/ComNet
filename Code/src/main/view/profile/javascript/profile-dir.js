function profileController($scope, navMenuService) {
    var that = this;

    that.showContent = showContent;

    initialize();

    function initialize() {
        $scope.showContent = false;

        navMenuService.profileController.set(that);
    }

    function showContent(show) {
        $scope.showContent = show;
    }
}

function profileDirective() {
    var that = this;

    return {
        restrict: 'E',
        scope: {
            showContent: '=?'
        },
        controller: ['$scope', 'navMenuService', profileController],
        controllerAs: 'profileCtrl',
        templateUrl: '../../profile/template/profile-template.html'
    }
}

angular.module('ComNet').directive('profile', profileDirective);