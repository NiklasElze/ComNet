'use strict';

function loginTranslationService() {
    var that = this;

    return {
        getGermanLabels: getGermanLabels,
        getEnglishLabels: getEnglishLabels
    }

    function getGermanLabels() {
        var fields = {
            username: 'Nutzername',
            password: 'Passwort'
        }

        return fields;
    }

    function getEnglishLabels() {
        var fields = {
            username: 'Username',
            password: 'Password'
        }

        return fields;
    }
}

angular.module('ComNet').factory('loginTranslationService', loginTranslationService);