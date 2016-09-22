'use strict';

function topicEntryController($scope){
    var that = this;

    initialize();

    function initialize(){
        var data = $scope.data;

        $scope.text = data.text;
        $scope.senderName = data.sender.firstname + ' ' + data.sender.lastname;
        $scope.createDate = new Date(data.createDate);
        $scope.seminarGroup = data.sender.seminargroup.name;
    }
}

function topicEntryDirective(){
    return{
        restrict: 'E',
        scope:{
            data: '=',
            senderName: '=?',
            createDate: '=?'
        },
        controller: ['$scope', topicEntryController],
        controllerAs: 'topicEntryCtrl',
        templateUrl: 'groups/template/topic-entry-template.html'
    }
}

angular.module('ComNet').directive('topicEntry', topicEntryDirective);