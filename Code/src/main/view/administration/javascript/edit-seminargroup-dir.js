'use strict';
function editSeminarGroupController($scope, $state, $stateParams, myHttpService) {
    var that = this;
    that.cancel = cancel;
    that.save = save;
    that.delete = deleteGroup;
    that.orderFunction = orderFunction;
    that.hideErrorMessage = hideErrorMessage;
    that.initializeNew = initializeNew();

    initialize();

    function initialize() {
        $scope.id = $stateParams.id;
        $scope.name = $stateParams.name;
        startLoading();

        $scope.showSave = true;
        $scope.showContent = true;
        $scope.showInitialize = false;
        $scope.showDelete = $scope.id !== 0;

        loadSeminarGroups();
    }

    function initializeNew(){
        if ($scope.id === 0){
            startLoading();
            $scope.showDelete = false;
            $scope.showSave = true;
            $scope.showInitialize = false;
            $scope.showContent = true;
            $scope.name = '';
            $scope.selectedSeminarGroup = null;
            loadSeminarGroups();
        }
    }

    function cancel() {
        $state.go('main.administration.overview');
    }

    function deleteGroup(){
        startLoading();
        hideInfoMessage();
        hideErrorMessage();

        myHttpService.deleteSeminarGroup($scope.id).then(function (){
            showInfoMessage('Successfully deleted.');
            stopLoading();
            $scope.showContent = false;
            $scope.showSave = false;
            $scope.showInitialize = false;
            $scope.showDelete = false;
        }, function (errorMessage){
            showErrorMessage(errorMessage);
            stopLoading();
        })
    }

    function save() {
        hideInfoMessage();
        hideErrorMessage();
        startLoading();

        var fatherGroupId = null;

        if ($scope.selectedSeminarGroup.id > 0) {
            fatherGroupId = $scope.selectedSeminarGroup.id;
        }

        var data = {
            id: $scope.id,
            name: $scope.name,
            fatherGroupId: fatherGroupId
        };

        myHttpService
            .addOrUpdateSeminarGroup(data)
            .then(function () {
                $scope.requestInProgress = false;
                showInfoMessage('Seminar group successfully saved.');
                hideErrorMessage();
                stopLoading();

                if ($scope.id === 0){
                    $scope.showContent = false;
                    $scope.showDelete = false;
                    $scope.showSave = false;
                    $scope.showInitialize = true;
                }
            }, function (errorMessage) {
                $scope.requestInProgress = false;
                showErrorMessage('Could not save seminar group: ' + errorMessage);
                hideInfoMessage();
                stopLoading();
            });
    }

    function orderFunction(seminarGroup) {
        if (seminarGroup.id === -1) {
            return '';
        }

        return seminarGroup.name;
    }

    function showErrorMessage(message) {
        $scope.errorMessage = message;
        $scope.showErrorMessage = true;
    }

    function hideErrorMessage() {
        $scope.errorMessage = '';
        $scope.showErrorMessage = false;
    }

    function showInfoMessage(message) {
        $scope.info = message;
        $scope.showInfo = true;
    }

    function hideInfoMessage() {
        $scope.info = '';
        $scope.showInfo = false;
    }

    function startLoading(){
        $scope.loading = true;
    }

    function stopLoading(){
        $scope.loading = false;
    }

    function loadSeminarGroups() {
        myHttpService
            .getSeminarGroups()
            .then(function (data) {
                var defaultEntry = {
                    id: -1,
                    name: '- none -'
                };

                $scope.seminarGroups = data;
                $scope.seminarGroups.splice(0, 0, defaultEntry);

                if ($stateParams.fatherGroup) {
                    $scope.selectedSeminarGroup = $stateParams.fatherGroup;
                }
                else {
                    $scope.selectedSeminarGroup = defaultEntry;
                }

                stopLoading();
            }, function (message) {
                hideInfoMessage();
                showErrorMessage(message);
                stopLoading();
            });
    }
}

function editSeminarGroupDirective() {
    return {
        restrict: 'E',
        scope: {
            id: '=?',
            name: '=?',
            seminarGroups: '=?',
            selectedSeminarGroup: '=?',
            requestInProgress: '=?'
        },
        controller: ['$scope', '$state', '$stateParams', 'myHttpService', editSeminarGroupController],
        controllerAs: 'editSeminarGroupCtrl',
        templateUrl: 'administration/template/edit-seminargroup-template.html'
    }
}

angular.module('ComNet').directive('editSeminarGroup', editSeminarGroupDirective);