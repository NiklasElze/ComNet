function titleController($scope, titleService){

    initialize();

    function initialize() {
        $scope.page = titleService;
    }
}

angular.module('ComNet').controller('titleController', ['$scope', 'titleService', titleController]);
