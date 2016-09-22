function logoutController($scope, $state, titleService, updateService) {
    var that = this;

    that.goToLogin = goToLogin;

    initialize();

    function initialize() {
        titleService.setTitle('ComNet | Logout');
        updateService.stopUpdate();
    }

    function goToLogin() {
        $state.go('login');
    }
}

function logoutDirective() {
    return {
        restrict: 'E',
        scope: {},
        controller: ['$scope', '$state', 'titleService', 'updateService', logoutController],
        controllerAs: 'logoutCtrl',
        templateUrl: 'logout/template/logout-template.html'
    }
}

angular.module('ComNet').directive('logout', logoutDirective);