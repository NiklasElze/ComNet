'use strict';

function loginController($scope, $state, loginTranslationService, titleService, myHttpService) {
    var that = this;

    that.hideErrorMessage = hideErrorMessage;
    that.login = login;

    initialize();

    function initialize() {
        titleService.setTitle('ComNet | Login');
        $scope.username = '';
        $scope.password = '';
        $scope.labels = {};
        $scope.showLoggingIn = false;
        $scope.showErrorMessage = false;

        changeLanguage(true);
    }

    function login() {
        startLoading();
        hideErrorMessage();

        if ($scope.username.trim() === ''){
            showErrorMessage('Please enter username.');
            stopLoading();
            return;
        }

        if ($scope.password.trim() === ''){
            showErrorMessage('Please enter password.');
            stopLoading();
            return;
        }

        var credentials = {
            'username': $scope.username,
            'password': $scope.password
        };

        myHttpService.login(credentials).then(function (result) {
            if (result.goToParams === null) {
                $state.go(result.goTo);
            }
            else {
                $state.go(result.goTo, result.goToParams);
            }
        }, function (errorMessage) {
            stopLoading();
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

    function startLoading() {
        $scope.loading = true;
    }

    function stopLoading() {
        $scope.loading = false;
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
            loading: '=?'
        },
        templateUrl: 'login/template/login-template.html',
        controller: ['$scope', '$state', 'loginTranslationService', 'titleService', 'myHttpService', loginController],
        controllerAs: 'loginCtrl'
    }
}

angular.module('ComNet').directive('login', loginDirective);