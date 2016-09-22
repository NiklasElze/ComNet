function editUserController($scope, $state, $stateParams, myHttpService) {
    var that = this;
    that.save = save;
    that.cancel = cancel;
    that.delete = deleteUser;
    that.setNewPassword = setNewPassword;
    that.cancelNewPassword = cancelNewPassword;
    that.acceptNewPassword = acceptNewPassword;
    that.initializeNew = initializeNew;
    that.hideInfo = hideInfo;
    that.hideErrorMessage = hideErrorMessage;

    initialize();

    function initialize() {
        var id = $stateParams.id;
        var isStudent = $stateParams.isStudent;
        $scope.isStudent = isStudent;

        if (id > 0) {
            if (isStudent) {
                myHttpService
                    .getStudentData(id)
                    .then(function (data) {
                        setStudentData(data);
                    }, function (errorMessage) {
                        showErrorMessage('Could not load data: ' + errorMessage);
                    });
            }
            else {
                myHttpService
                    .getUserData(id)
                    .then(function (data) {
                        setUserData(data);
                    }, function (errorMessage) {
                        showErrorMessage('Could not load data: ' + errorMessage);
                    });
            }
        }
        else {
            if (isStudent) {
                initializeNewStudent();
            }
            else {
                initializeNewUser();
            }
        }
    }

    function initializeNew() {
        if ($scope.isStudent === true) {
            initializeNewStudent();
        }
        else {
            initializeNewUser();
        }
    }

    function setUserData(data) {
        $scope.id = data.id;
        $scope.username = data.username;
        $scope.firstname = data.firstname;
        $scope.lastname = data.lastname;
        $scope.accessDenied = data.accessDenied;

        $scope.showSave = true;
        $scope.showPasswordArea = true;
        $scope.showSetNewPassword = true;
        $scope.showPasswordField = false;
        $scope.showDelete = true;
        $scope.showStudentData = false;
        $scope.showUserData = true;
        $scope.showCustodianData = true;
        $scope.showInitialize = false;
        $scope.passwordChanged = false;

        setPrivileges(data.privileges);
    }

    function setStudentData(data) {
        $scope.id = data.id;
        $scope.username = data.username;
        $scope.firstname = data.firstname;
        $scope.lastname = data.lastname;
        $scope.accessDenied = data.accessDenied;

        $scope.showSave = true;
        $scope.showPasswordArea = true;
        $scope.showSetNewPassword = true;
        $scope.showPasswordField = false;
        $scope.showDelete = true;
        $scope.showStudentData = true;
        $scope.showUserData = true;
        $scope.showCustodianData = false;
        $scope.showInitialize = false;
        $scope.passwordChanged = false;

        myHttpService
            .getSeminarGroups()
            .then(function (groups) {
                $scope.seminarGroups = groups;
                $scope.selectedSeminarGroup = data.seminarGroup;
            }, function (errorMessage) {
                showErrorMessage('Could not load seminar groups: ' + errorMessage);
            });
    }

    function initializeNewUser() {
        $scope.buttonInitializeText = 'New custodian';

        $scope.id = 0;
        $scope.showStudentData = false;
        $scope.showUserData = true;
        $scope.username = '';
        $scope.password = '';
        $scope.firstname = '';
        $scope.lastname = '';
        $scope.showPasswordArea = true;
        $scope.showPasswordField = true;
        $scope.showSetNewPassword = false;
        $scope.showDelete = false;
        $scope.showCustodianData = true;
        $scope.showInitialize = false;
        $scope.showSave = true;
        $scope.showInfo = false;
        $scope.showErrorMessage = false;
        $scope.passwordChanged = false;

        setPrivileges([]);
    }

    function initializeNewStudent() {
        $scope.buttonInitializeText = 'New student';

        $scope.id = 0;
        $scope.showStudentData = true;
        $scope.showUserData = true;
        $scope.username = '';
        $scope.password = '';
        $scope.firstname = '';
        $scope.lastname = '';
        $scope.showPasswordArea = true;
        $scope.showPasswordField = true;
        $scope.showSetNewPassword = false;
        $scope.showDelete = false;
        $scope.showCustodianData = false;
        $scope.showInitialize = false;
        $scope.showSave = true;
        $scope.showErrorMessage = false;
        $scope.passwordChanged = false;

        myHttpService
            .getSeminarGroups()
            .then(function (data) {
                $scope.seminarGroups = data;
                $scope.selectedSeminarGroup = null;
            }, function (errorMessage) {
                showErrorMessage('Could not load seminar groups: ' + errorMessage);
            });
    }

    function cancel() {
        $state.go('main.administration.overview');
    }

    function save() {
        startLoading();
        hideErrorMessage();
        hideInfo();
        $scope.requestInProgress = true;

        var data;

        if ($scope.isStudent) {
            data = {
                id: $scope.id,
                username: $scope.username,
                password: $scope.password,
                firstname: $scope.firstname,
                lastname: $scope.lastname,
                seminarGroupId: $scope.selectedSeminarGroup.id
            };

            myHttpService.addOrUpdateStudent(data).then(function () {
                showInfo('Student successfully saved.');
                $scope.requestInProgress = false;
                stopLoading();

                if ($scope.id === 0) {
                    showInitialize();
                }
            }, function (errorMessage) {
                hideInfo();
                showErrorMessage(errorMessage);
                $scope.requestInProgress = false;
                stopLoading();
            });
        }
        else {
            data = {
                id: $scope.id,
                username: $scope.username,
                password: $scope.password,
                firstname: $scope.firstname,
                lastname: $scope.lastname,
                privilegeIds: getPrivileges()
            };

            myHttpService.addOrUpdateUser(data).then(function () {
                showInfo('User successfully saved.');
                $scope.requestInProgress = false;
                stopLoading();

                if ($scope.id === 0) {
                    showInitialize();
                }
            }, function (errorMessage) {
                hideInfo();
                showErrorMessage(errorMessage);
                $scope.requestInProgress = false;
                stopLoading();
            });
        }
    }

    function deleteUser() {
        startLoading();
        hideErrorMessage();
        hideInfo();
        $scope.requestInProgress = true;

        var id = $scope.id;

        if ($scope.isStudent === true) {
            myHttpService.deleteStudent(id).then(function () {
                showInfo('Student successfully deleted.');
                $scope.requestInProgress = false;
                stopLoading();
                $scope.showSave = false;
                $scope.showDelete = false;
                $scope.showInitialize = false;
                $scope.showUserData = false;
                $scope.showStudentData = false;
            }, function (errorMessage) {
                hideInfo();
                showErrorMessage(errorMessage);
                $scope.requestInProgress = false;
                stopLoading();
            });
        }
        else {
            myHttpService.deleteUser(id).then(function () {
                showInfo('Custodian successfully deleted.');
                $scope.requestInProgress = false;
                stopLoading();
                $scope.showSave = false;
                $scope.showDelete = false;
                $scope.showInitialize = false;
                $scope.showUserData = false;
                $scope.showCustodianData = false;
            }, function (errorMessage) {
                hideInfo();
                showErrorMessage(errorMessage);
                $scope.requestInProgress = false;
                stopLoading();
            });
        }
    }

    function setNewPassword() {
        $scope.showNewPasswordPanel = true;
    }

    function cancelNewPassword() {
        $scope.showNewPasswordPanel = false;
    }

    function acceptNewPassword() {
        $scope.password = $scope.newPassword;
        $scope.showNewPasswordPanel = false;
        $scope.passwordChanged = true;
    }

    function showInfo(info) {
        $scope.info = info;
        $scope.showInfo = true;
    }

    function hideInfo() {
        $scope.info = '';
        $scope.showInfo = false;
    }

    function showErrorMessage(errorMessage) {
        $scope.errorMessage = errorMessage;
        $scope.showErrorMessage = true;
    }

    function hideErrorMessage() {
        $scope.errorMessage = '';
        $scope.showErrorMessage = false;
    }

    function showInitialize() {
        $scope.showInitialize = true;
        $scope.showDelete = false;
        $scope.showSave = false;
        $scope.showUserData = false;
    }

    function startLoading() {
        $scope.loading = true;
    }

    function stopLoading() {
        $scope.loading = false;
    }

    function setPrivileges(privileges) {
        if (privileges === null || privileges.length === 0) {
            $scope.adminSelected = false;
            $scope.personInAuthoritySelected = false;
        }

        angular.forEach(privileges, function (privilege) {
            $scope.adminSelected = privilege.id === 2;

            $scope.personInAuthoritySelected = privilege.id === 3;
        });
    }

    function getPrivileges() {
        var data = [];

        if ($scope.adminSelected === true) {
            data.push(2);
        }

        if ($scope.personInAuthoritySelected === true) {
            data.push(3);
        }

        return data;
    }
}

function editUserDirective() {
    return {
        restrict: 'E',
        scope: {
            id: '=?',
            showInfo: '=?',
            showErrorMessage: '=?',
            info: '=?',
            errorMessage: '=?',
            requestInProgress: '=?',
            showPasswordArea: '=?',
            showSetNewArea: '=?',
            showNewPasswordPanel: '=?',
            showUserData: '=?',
            showStudentData: '=?',
            firstname: '=?',
            lastname: '=?',
            seminarGroups: '=?',
            username: '=?',
            newPassword: '=?',
            passwordChanged: '=?',
            selectedSeminarGroup: '=?',
            showPasswordField: '=?'
        },
        controller: ['$scope', '$state', '$stateParams', 'myHttpService', editUserController],
        controllerAs: 'editUserCtrl',
        templateUrl: 'administration/template/edit-user-template.html'
    }
}

angular.module('ComNet').directive('editUser', editUserDirective);