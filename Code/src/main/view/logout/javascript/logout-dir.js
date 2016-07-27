function logoutController($scope, $state) {
    var that = this;

    that.goToLogin = goToLogin;

    initialize();

    function initialize() {
    }

    function goToLogin() {
        $state.go('login');
    }
}

function logoutDirective() {
    var that = this;

    return {
        restrict: 'E',
        scope: {},
        controller: ['$scope', '$state', logoutController],
        controllerAs: 'logoutCtrl',
        templateUrl: 'logout/template/logout-template.html'
    }
}

angular.module('ComNet').directive('logout', logoutDirective);