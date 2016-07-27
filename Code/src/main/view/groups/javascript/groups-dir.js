function groupsController($scope, navMenuService) {
    var that = this;

    that.showContent = showContent;

    initialize();

    function initialize() {
        $scope.showContent = false;

        navMenuService.groupsController.set(that);

        loadGroups();
    }

    function showContent(show) {
        $scope.showContent = show;
    }

    function loadGroups() {
        $scope.groups = getGroups;
    }

    function getGroups() {
        // -- API --
        return [{ id: 1, name: 'Essensgruppe' }, { id: 2, name: 'Theoretische Informatik' }, { id: 3, name: 'Organisation' }];
    }
}

function groupsDirective() {
    var that = this;

    return {
        restrict: 'E',
        scope: {
            groups: '=?',
            showContent: '=?'
        },
        controller: ['$scope', 'navMenuService', groupsController],
        controllerAs: 'groupsCtrl',
        templateUrl: '../../groups/template/groups-template.html'
    }
}

angular.module('ComNet').directive('groups', groupsDirective);