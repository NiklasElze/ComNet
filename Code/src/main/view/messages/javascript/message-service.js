function messageService() {
    var that = this;

    return {
        loadConversation: loadConversation,
        loadNewConversation: loadNewConversation,
        startNewConversation: startNewConversation,
        addStudentFromContactList: addStudentFromContactList,
        addStudentToContactList: addStudentToContactList,
        conversationController: {
            set: setConversationController
        },
        conversationOverviewController: {
            set: setConversationOverviewController
        },
        messageController: {
            set: setMessageController
        },
        studentContactListController: {
            set: setStudentContactListController
        },
        newConversationController: {
            set: setNewConversationController
        }
    }

    function setConversationController(controller){
        that.conversationController = controller;
    }

    function setConversationOverviewController(controller){
        that.conversationOverviewController = controller;
    }

    function setMessageController(controller) {
        that.messageController = controller;
    }

    function setStudentContactListController(controller) {
        that.studentContactListController = controller;
    }

    function setNewConversationController(controller) {
        that.newConversationController = controller;
    }

    function loadConversation(id) {
        var conversationData = getConversationData(id);

        that.conversationController.loadConversation(conversationData);
        that.conversationController.showConversation(true);
        that.conversationOverviewController.showOverview(false);
        that.newConversationController.showNewConversationContent(false);
    }

    function loadNewConversation() {
        that.newConversationController.showNewConversationContent(true);
        that.conversationController.showConversation(false);
        that.conversationOverviewController.showOverview(false);
    }

    function loadOverview() {
        that.conversationOverviewController.loadConversationOverview();
        that.conversationController.showConversation(false);
        that.conversationOverviewController.showOverview(true);
        that.newConversationController.showNewConversationContent(false);
    }

    function startNewConversation() {
        that.conversationController.showConversation(false);
        that.conversationOverviewController.showOverview(false);
        that.studentContactListController.loadContactList();
    }

    function addStudentFromContactList(student) {
        that.newConversationController.addStudentFromContactList(student);
    }

    function addStudentToContactList(student) {
        that.studentContactListController.addStudentToContactList(student);
        that.newConversationController.removeStudentFromMembers(student);
    }

    function getConversationData(id) {
        // -- API -- 
        if (id === 1) {
            return {
                id: 1,
                members: [{ id: 1, firstname: 'Max', lastname: 'Mustermann', seminarGroup: { id: 1, name: 'CS13' } }, { id: 2, firstname: 'Erika', lastname: 'Mustermann', seminarGroup: { id: 2, name: 'CS12' } }]
            };
        }

        if (id === 2) {
            return {
                id: 2,
                members: [{ id: 3, firstname: 'Hans', lastname: 'Meyer', seminarGroup: { id: 3, name: 'IT11' } }]
            };
        }
    }
}

angular.module('ComNet').factory('messageService', messageService);