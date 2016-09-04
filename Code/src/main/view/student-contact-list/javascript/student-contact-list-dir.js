'use strict';

function studentContactListController($scope, $state, seminarGroupService, myHttpService) {
    var that = this;
    var parentController = {};

    that.addItemToMembers = addItemToMembers;
    that.removeItemFromMembers = removeItemFromMembers;
    $scope.orderFunction = orderFunction;
    $scope.labels = {};
    that.cancel = cancel;
    that.save = save;

    initialize();

    function initialize(){
        $scope.newMembers = [];
        $scope.newMemberItems = [];
        parentController = $scope.parentController;
        setLabels();
        if ($scope.type === 'conversation' && $scope.id && $scope.id > 0) {
            myHttpService.getStudentContactListOfConversation($scope.id)
                .then(function (data) {
                    $scope.seminarGroups = seminarGroupService.orderGroups(data);
                }, function (errorMessage) {
                    parentController.showErrorMessage(errorMessage);
                });
        } else if ($scope.type === 'new') {
            myHttpService.getStudentContactListOfNewConversation()
                .then(function (data) {
                    $scope.seminarGroups = seminarGroupService.orderGroups(data);
                }, function (errorMessage) {
                    parentController.showErrorMessage(errorMessage);
                });
        }
    }

    function setLabels(){
        var labels = {};

        if (!$scope.subtype || $scope.subtype === ''){
            labels.buttonSave = 'Save';
        } else if ($scope.subtype === 'conversation'){
            labels.buttonSave = 'Start conversation';
            $scope.showNewMembersLabel = false;
        }

        $scope.labels = labels;
    }

    function cancel(){
        parentController.cancelMemberAdd();
    }

    function save(){
        parentController.saveMemberAdd($scope.newMembers);
    }

    function addItemToMembers(item){
        $scope.newMemberItems.push(item);

        addMembersOfItem(item);
    }

    function addMembersOfItem(item){
        var indexOfGroup;

        if (item.type === 'student'){
            $scope.newMembers.push(item.data);

            var indexOfStudent;

            if (item.data.seminargroup.fatherGroup){
                var fatherGroup = seminarGroupService.findById($scope.seminarGroups, item.data.seminargroup.fatherGroup.id);

                indexOfGroup = seminarGroupService.indexOf(fatherGroup.subGroups, item.data.seminargroup);
                indexOfStudent = indexOfStudentInGroup(fatherGroup.subGroups[indexOfGroup], item.data);

                fatherGroup.subGroups[indexOfGroup].students.splice(indexOfStudent, 1);
            } else{
                indexOfGroup = seminarGroupService.indexOf($scope.seminarGroups, item.data.seminargroup);
                indexOfStudent = indexOfStudentInGroup($scope.seminarGroups[indexOfGroup], item.data);

                $scope.seminarGroups[indexOfGroup].students.splice(indexOfStudent, 1);
            }
        } else if (item.type === 'seminargroup'){

            addStudentsOfSubGroups(item.data);

            removeExistingItemsOfGroup(item.data);

            if (item.data.fatherGroup){
                indexOfGroup = seminarGroupService.indexOf(item.data.fatherGroup.subGroups, item.data);
                item.data.fatherGroup.subGroups.splice(indexOfGroup, 1);
            }
            else{
                indexOfGroup = seminarGroupService.indexOf($scope.seminarGroups, item.data);
                $scope.seminarGroups.splice(indexOfGroup, 1);
            }
        }
    }

    function addStudentsOfSubGroups(seminarGroup){
        angular.forEach(seminarGroup.subGroups, function(subGroup){
            addStudentsOfSubGroups(subGroup);
        });

        for (var i = 0; i < seminarGroup.students.length; i++){
            $scope.newMembers.push(seminarGroup.students[i]);
        }
    }

    function removeItemFromMembers(item){
        var itemIndex = indexOfItem($scope.newMemberItems, item);

        removeMembersOfItem(item);
        $scope.newMemberItems.splice(itemIndex, 1);
    }

    function removeMembersOfItem(item){
        var indexOfGroup;

        if (item.type === 'student'){
            if (item.data.seminargroup.fatherGroup){
                var fatherGroup = seminarGroupService.findById($scope.seminarGroups, item.data.seminargroup.fatherGroup.id);

                indexOfGroup = seminarGroupService.indexOf(fatherGroup.subGroups, item.data.seminargroup);
                fatherGroup.subGroups[indexOfGroup].students.push(item.data);
            } else{
                indexOfGroup = seminarGroupService.indexOf($scope.seminarGroups, item.data.seminargroup);

                $scope.seminarGroups[indexOfGroup].students.push(item.data);
            }

            var studentIndex = indexOfStudent($scope.newMembers, item.data);

            $scope.newMembers.splice(studentIndex, 1);
        } else if (item.type === 'seminargroup'){
            var newMembersList = [];

            for (var i = 0; i < $scope.newMembers.length; i++){
                var member = $scope.newMembers[i];

                if (member.seminargroup.id !== item.data.id &&
                    (!member.seminargroup.fatherGroup || member.seminargroup.fatherGroup.id !== item.data.id)){
                    newMembersList.push(member);
                }
            }

            if (item.data.fatherGroup){
                item.data.fatherGroup.subGroups.push(item.data);
            } else{
                $scope.seminarGroups.push(item.data);
            }

            $scope.newMembers = newMembersList;
        }
    }

    function indexOfItem(items, item){
        for (var i = 0; i < items.length; i++){
            if (items[i].data.id === item.data.id){
                return i;
            }
        }

        return -1;
    }

    function indexOfStudentInGroup(group, student){
        for (var i = 0; i < group.students.length; i++){
            if (group.students[i].id === student.id){
                return i;
            }
        }

        return -1;
    }

    function indexOfStudent(students, student){
        for (var i = 0; i < students.length; i++){
            if (students[i].id === student.id){
                return i;
            }
        }

        return -1;
    }

    function removeExistingItemsOfGroup(group){
        var newItemList = [];

        for (var i = 0; i < $scope.newMemberItems.length; i++){
            var item = $scope.newMemberItems[i];

            if (item.type === 'student'){

                if (item.data.seminargroup.id === group.id){
                    group.students.push(item.data);
                } else if (item.data.seminargroup.fatherGroup && item.data.seminargroup.fatherGroup.id === group.id) {
                    var fatherGroup = seminarGroupService.findById($scope.seminarGroups, item.data.seminargroup.fatherGroup.id);
                    var indexOfGroup = seminarGroupService.indexOf(fatherGroup.subGroups, item.data.seminargroup);

                    fatherGroup.subGroups[indexOfGroup].students.push(item.data);
                } else{
                    newItemList.push(item);
                }
            } else if (item.type === 'seminargroup'){
                if (item.data.fatherGroup && item.data.fatherGroup.id == group.id){
                    group.subGroups.push(item.data);
                } else{
                    newItemList.push(item);
                }
            }
        }

        $scope.newMemberItems = newItemList;
    }

    function orderFunction(item){
        if (item.type === 'student'){
            return item.data.firstname;
        } else{
          return ' ' + item.data.name;
        }
    }
}

function studentContactListDirective() {
    return {
        restrict: 'E',
        scope: {
            type: '@',
            subtype: '@',
            id: '=',
            parentController: '=',
            seminarGroups: '=?',
            newMembers: '=?',
            newMemberItems: '=?',
            labels: '=?',
            showNewMembersLabel: '=?'
        },
        controller: ['$scope', '$state', 'seminarGroupService', 'myHttpService', studentContactListController],
        controllerAs: 'studentContactListCtrl',
        templateUrl: 'student-contact-list/template/student-contact-list-template.html'
    }
}

angular.module('ComNet').directive('studentContactList', studentContactListDirective);