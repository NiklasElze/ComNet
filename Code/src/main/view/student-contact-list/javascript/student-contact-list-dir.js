function studentContactListController($scope, $state, seminarGroupService, myHttpService) {
    var that = this;
    var parentController = {};

    that.addItemToMembers = addItemToMembers;
    that.removeItemFromMembers = removeItemFromMembers;

    initialize();

    function initialize(){
        $scope.newMembers = [];
        $scope.newMemberItems = [];
        parentController = $scope.parentController;
        if ($scope.type === 'conversation' && $scope.id && $scope.id > 0){
            myHttpService.getStudentContactListOfConversation($scope.id).then(function(data){
                $scope.seminarGroups = seminarGroupService.orderGroups(data);
            }, function(errorMessage){
                parentController.showErrorMessage(errorMessage);
            });
        }
    }

    function addItemToMembers(item){
        $scope.newMemberItems.push(item);

        addMembersOfItem(item);
    }

    function addMembersOfItem(item){
        if (item.type === 'student'){
            $scope.newMembers.push(item.data);

            var indexOfGroup = seminarGroupService.indexOf($scope.seminarGroups, item.data.seminargroup);
            var indexOfStudent = indexOfStudentInGroup($scope.seminarGroups[indexOfGroup], item.data);

            $scope.seminarGroups[indexOfGroup].students.splice(indexOfStudent, 1);
        } else if (item.type === 'seminargroup'){
            var indexOfGroup = seminarGroupService.indexOf($scope.seminarGroups, item.data);

            for (var i = 0; i < $scope.seminarGroups[indexOfGroup].students.length; i++){
                $scope.newMembers.push($scope.seminarGroups[indexOfGroup].students[i]);
            }

            addStudentsOfSubGroups(item.data);

            $scope.seminarGroups.splice(indexOfGroup, 1);
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
        if (item.type === 'student'){
            var indexOfGroup = seminarGroupService.indexOf($scope.seminarGroups, item.data.seminargroup);
            var studentIndex = indexOfStudent($scope.newMembers, item.data);

            $scope.seminarGroups[indexOfGroup].students.push(item.data);
            $scope.newMembers.splice(studentIndex, 1);
        } else if (item.type === 'seminargroup'){
            var newMembersList = [];

            for (i = 0; i < $scope.newMembers.length; i++){
                if (!containsMember(item.data.students, $scope.newMembers[i])){
                    newMembersList.push($scope.newMembers[i]);
                }
            }

            $scope.seminarGroups.push(item.data);
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

    function containsMember(members, member){
        for (var i = 0; i < members.length; i++){
            if (members[i].id === member.id){
                return true;
            }
        }

        return false;
    }
}

function studentContactListDirective() {
    return {
        restrict: 'E',
        scope: {
            type: '@',
            id: '=',
            parentController: '=',
            seminarGroups: '=?',
            newMembers: '=?',
            newMemberItems: '=?'
        },
        controller: ['$scope', '$state', 'seminarGroupService', 'myHttpService', studentContactListController],
        controllerAs: 'studentContactListCtrl',
        templateUrl: 'student-contact-list/template/student-contact-list-template.html'
    }
}

angular.module('ComNet').directive('studentContactList', studentContactListDirective);