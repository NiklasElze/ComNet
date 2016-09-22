'use strict';

function seminarGroupService(){
    return{
        orderGroups: orderGroups,
        indexOf: indexOf,
        findById: findById
    };

    function orderGroups(seminarGroups) {
        var orderedGroups = [];
        var groupsToRemove = [];

        angular.forEach(seminarGroups, function (seminarGroup) {
            seminarGroup.subGroups = [];

            if (seminarGroup.fatherGroup) {
                seminarGroup.fatherGroup.subGroups = [];
            }

            orderedGroups.push(seminarGroup);
        });

        angular.forEach(seminarGroups, function (seminarGroup) {

            if (seminarGroup.fatherGroup) {
                var indexOfFatherGroup = indexOf(orderedGroups, seminarGroup.fatherGroup);

                orderedGroups[indexOfFatherGroup].subGroups.push(seminarGroup);

                groupsToRemove.push(seminarGroup);
            }
        });

        angular.forEach(groupsToRemove, function (seminarGroup) {
            var index = indexOf(orderedGroups, seminarGroup);

            orderedGroups.splice(index, 1);
        });

        angular.forEach(orderedGroups, function(group){
            angular.forEach(group.subGroups, function (subGroup){
                subGroup.fatherGroup = group;
            });
        });

        return orderedGroups;
    }

    function indexOf(groups, group) {
        var index = -1;

        for (var i = 0; i < groups.length; i++){
            if (groups[i].id === group.id){
                index = i;
                return index;
            }
        }

        return index;
    }

    function findById(groups, id){
        for (var i = 0; i < groups.length; i++){
            if (groups[i].id === id){
                return groups[i];
            }
        }

        return null;
    }
}

angular.module('ComNet').factory('seminarGroupService', seminarGroupService);