function errorService() {
    var that = this;

    return {
        getMessageByType: getMessageByType
    }

    function getMessageByType(errorType) {

        switch (errorType) {
            case "LOGIN_ALREADY_EXISTS":
                return "Username already exists.";

            case "INVALID_LOGIN_DATA":
                return "Invalid login data.";

            case "GROUP_CONTAINS_STUDENTS":
                return "Seminar group contains students.";

            case "UNAUTHORIZED_ACTION":
                return "You are not permitted to perform this action.";

            case "INTERNAL_ERROR":
                return "Error occured.";

            default:
                return "Some undefined error.";
        }
    }
}

angular.module('ComNet').factory('errorService', errorService);