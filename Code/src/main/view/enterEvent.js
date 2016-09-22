function enterEventDirective(){
    return{
        restrict: 'A',
        link: {
            pre: function (scope, element, attrs){
                element.bind('keydown keypress', function(event){
                    if (event.which === 13 && !event.shiftKey){
                        scope.$apply(function(){
                            scope.$eval(attrs.enterEvent);
                        });

                        event.preventDefault();
                    }
                });
            }
        }
    }
}

angular.module('ComNet').directive('enterEvent', enterEventDirective);
