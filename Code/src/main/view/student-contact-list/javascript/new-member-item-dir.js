'use strict';

function newMemberItemController($scope){
    var that = this;

    that.remove = remove;

    initialize();

    function initialize(){
        loadItem();
    }

    function loadItem(){
        var item = $scope.item;

        if (item.type === 'seminargroup'){
            $scope.isStudentItem = false;
            $scope.label = item.data.name;
        }
        else if (item.type === 'student'){
            $scope.isStudentItem = true;
            $scope.label = item.data.firstname + ' ' + item.data.lastname + ' ' + item.data.seminargroup.name;
        }
    }

    function remove(){
        $scope.studentContactListController.removeItemFromMembers($scope.item);
    }
}

function newMemberItemDirective(){
    return{
        restrict: 'E',
        require: '^studentContactList',
        scope: {
            item: '=',
            isStudentItem: '=?',
            label: '=?'
        },
        controller: ['$scope', newMemberItemController],
        controllerAs: 'newMemberItemCtrl',
        templateUrl: 'student-contact-list/template/new-member-item-template.html',
        link:{
            pre: function (scope, element, attrs, controller) {
                scope.studentContactListController = controller;
            }
        }
    }
}

angular.module('ComNet').directive('newMemberItem', newMemberItemDirective);