function updateService($interval){

    var updates = [];
    var currentUpdateProcess = null;

    return{
        addUpdate: addUpdate,
        setUpdate: setUpdate,
        stopUpdate: stopUpdate
    };

    function addUpdate(name, controller){
        var index = -1;

        for (i = 0; i < updates.length; i++){
            if (updates[i].name === name){
                index = i;
                break;
            }
        }

        if (index > -1){
            updates.splice(index, 1);
        }

        updates.push({
            name: name,
            controller: controller
        });
    }

    function setUpdate(name){
        if (currentUpdateProcess){
            currentUpdateProcess.controller.stopUpdate(currentUpdateProcess.process);
        }

        angular.forEach(updates, function(update){
            if (update.name === name){
                currentUpdateProcess = {};
                currentUpdateProcess.controller = update.controller;
                currentUpdateProcess.process = update.controller.startUpdate();
            }
        });
    }

    function stopUpdate(name){
        currentUpdateProcess.controller.stopUpdate(currentUpdateProcess.process);
        currentUpdateProcess = null;
    }
}

angular.module('ComNet').factory('updateService', updateService);