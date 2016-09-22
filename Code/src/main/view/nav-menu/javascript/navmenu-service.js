function navMenuService() {
    var that = this;
    
    return {
        navMenuController: {
            set: setNavMenuController
        },
        setToActive: setToActive
    }

    function setToActive(target) {
        that.navMenuController.setToActive(target)
    }

    function setNavMenuController(controller) {
        that.navMenuController = controller;
    }
}

angular.module('ComNet').factory('navMenuService', navMenuService);