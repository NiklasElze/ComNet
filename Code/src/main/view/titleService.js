function titleService(){
    var title = 'ComNet';

    return{
        getTitle: getTitle,
        setTitle: setTitle
    };

    function getTitle(){
        return title;
    }

    function setTitle(newTitle){
        title = newTitle;
    }
}

angular.module('ComNet').factory('titleService', titleService);
