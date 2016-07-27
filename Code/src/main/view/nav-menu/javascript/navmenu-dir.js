function navMenuController($scope,$state, navMenuService) {
    var that = this;

    that.activeElement = 'li_home';
    that.goTo = goTo;
    that.setToActive = setToActive;

    initialize();

    function initialize() {
        navMenuService.navMenuController.set(that);
    }

    function goTo(target) {
        if (target === 'logout') {
            $scope.$parent.logout();
            return;
        }

        var stateTarget = 'main.' + target;

        $state.go(stateTarget);
    }

    function setToActive(target) {
        var targetElementId = 'li_' + target;

        document.getElementById(that.activeElement).classList.remove('active');
        document.getElementById(targetElementId).classList.add('active');

        that.activeElement = targetElementId;
    }
}

function navMenuDirective() {
    return {
        restrict: 'E',
        scope: {
            show: '=?'
        },
        templateUrl: 'nav-menu/template/navmenu-template.html',
        controller: ['$scope', '$state', 'navMenuService', navMenuController],
        controllerAs: 'menuCtrl'
    }
}

angular.module('ComNet').directive('navMenu', navMenuDirective);