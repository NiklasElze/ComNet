'use strict';

function loginController($scope, $state, loginTranslationService, myHttpService) {
    var that = this;

    init();

    return {
        submit: submit,
        changeLanguage : changeLanguage
    }

    function init() {
        $scope.username = '';
        $scope.password = '';
        $scope.labels = {};
        $scope.loggingInText = 'Loggin in...';
        $scope.showLoggingIn = false;
        $scope.showErrorMessage = false;

        changeLanguage(true);
    }

    function submit() {
        showLoggingIn();
        hideErrorMessage();
        $scope.loginInProcess = true;

        var credentials = {
            'username': $scope.username,
            'password': $scope.password
        };

        myHttpService.login(credentials).then(function (result) {
            hideLoggingIn();

            if (result.goToParams === null) {
                $state.go(result.goTo);
            }
            else {
                $state.go(result.goTo, result.goToParams);
            }
        }, function (errorMessage) {
            hideLoggingIn();
            $scope.loginInProcess = false;
            showErrorMessage('Could not log in: ' + errorMessage);
        });
    }

    function changeLanguage(toEnglish) {
        if (toEnglish === true) {
            that.labels = loginTranslationService.getEnglishLabels();
        }
        else {
            that.labels = loginTranslationService.getGermanLabels();
        }
        
        setLabels();
    }

    function setLabels() {
        $scope.labels.username = that.labels.username;
        $scope.labels.password = that.labels.password;
    }

    function showErrorMessage(message) {
        $scope.errorMessage = message;
        $scope.showErrorMessage = true;
    }

    function hideErrorMessage() {
        $scope.errorMessage = '';
        $scope.showErrorMessage = false;
    }

    function showLoggingIn() {
        $scope.showLoggingIn = true;
    }

    function hideLoggingIn() {
        $scope.showLoggingIn = false;
    }
}

function loginDirective() {
    return {
        restrict: 'E',
        scope : {
            username: '=?',
            password: '=?',
            labels: '=?',
            signin: '=?',
            errorMessage: '=?',
            showErrorMessage: '=?',
            showLoggingIn: '=?',
            logginInText: '=?'
        },
        templateUrl: 'login/template/login-template.html',
        controller: ['$scope', '$state', 'loginTranslationService', 'myHttpService', loginController],
        controllerAs: 'loginCtrl'
    }
}

angular.module('ComNet').directive('login', loginDirective);