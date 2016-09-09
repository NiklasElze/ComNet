'use strict';

function mainController($scope, $state, principalService) {
    $scope.logout = function () {
        principalService.setIdentity(null);
        $state.go('logout');
    };
}

function loginController($scope, $state, principalService) {
    $scope.signin = function (credentials) {

        principalService.authenticate(credentials);

        if ($scope.returnToState) {
            $state.go($scope.returnToState.name, $scope.returnToStateParams);
        }
        else {
            $state.go('main.home');
        }
    };
}

function appConfig($stateProvider, $urlRouterProvider, $locationProvider) {

    $urlRouterProvider.otherwise('/main/home');

    $stateProvider.state('site', {
        'abstract': true,
        resolve: {
            authorize: ['authorizationService',
                function (authorizationService) {
                    return authorizationService.authorize();
                }
            ]
        }
    }).state('logout', {
        parent: 'site',
        url: '/logout',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'logout/html/logout.html',
                controller: ''
            }
        }
    }).state('login', {
        parent: 'site',
        url: '/login',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'login/html/login.html',
                controller: 'loginCtrl'
            }
        }
    }).state('main', {
        parent: 'site',
        url: '/main',
        data: {
            roles: [1, 2, 3]
        },
        views: {
            'content@': {
                templateUrl: 'main/html/main.html',
                controller: 'mainCtrl'
            },
            'main-content': {
                templateUrl: 'home/html/home.html',
                controller: 'mainCtrl'
            }
        }
    }).state('main.home', {
        parent: 'main',
        url: '/home',
        data: {
            roles: [1]
        },
        views: {
            'main-content': {
                templateUrl: 'home/html/home.html',
                controller: 'mainCtrl'
            }
        }
    }).state('main.messages', {
        parent: 'main',
        url: '/messages',
        data: {
            roles: [1]
        },
        views: {
            'main-content': {
                templateUrl: 'messages/html/messages.html',
                controller: 'mainCtrl'
            }
        }
    }).state('main.messages.overview', {
        parent: 'main.messages',
        url: '/overview',
        data: {
            roles: [1]
        },
        views: {
            'messages-content':{
                templateUrl: 'messages/html/conversation-overview.html'
            }
        }
    }).state('main.messages.conversation', {
        parent: 'main.messages',
        url: '/conversation',
        data: {
            roles: [1]
        },
        params:{
            id: 0
        },
        views: {
            'messages-content':{
                templateUrl: 'messages/html/conversation.html'
            }
        }
    }).state('main.messages.new', {
        parent: 'main.messages',
        url: '/new-conversation',
        data: {
            roles: [1]
        },
        views: {
            'messages-content':{
                templateUrl: 'messages/html/new-conversation.html'
            }
        }
    }).state('main.groups', {
        parent: 'main',
        url: '/groups',
        data: {
            roles: [1]
        },
        views: {
            'main-content': {
                templateUrl: 'groups/html/groups.html',
                controller: 'mainCtrl'
            }
        }
    }).state('main.groups.overview', {
        parent: 'main.groups',
        url: '/overview',
        data: {
            roles: [1]
        },
        views: {
            'messages-content':{
                templateUrl: 'groups/html/group-overview.html'
            }
        }
    }).state('main.administration', {
        parent: 'main',
        url: '/administration',
        data: {
            roles: [2, 3]
        },
        views: {
            'main-content': {
                templateUrl: 'administration/html/administration.html',
                controller: 'mainCtrl'
            }
        }
    }).state('main.administration.overview', {
        parent: 'main.administration',
        url: '/overview',
        data: {
            roles: [2, 3]
        },
        views: {
            'administration-content': {
                templateUrl: 'administration/html/administration-overview.html'
            }
        }
    }).state('main.administration.seminargroup', {
        parent: 'main.administration',
        url: '/seminargroup',
        data: {
            roles: [3]
        },
        params: {
            id: 0,
            name: '',
            fatherGroup: null
        },
        views: {
            'administration-content': {
                templateUrl: 'administration/html/edit-seminargroup.html',
                controller: 'mainCtrl'
            }
        }
    }).state('main.administration.user', {
        parent: 'main.administration',
        url: '/user',
        data: {
            roles: [2, 3]
        },
        params: {
            id: 0,
            isStudent: true
        },
        views: {
            'administration-content': {
                templateUrl: 'administration/html/edit-user.html',
                controller: 'mainCtrl'
            }
        }
    });
}

function appRun($rootScope, $state, $stateParams, $timeout, authorizationService, principalService) {

    $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams, fromState, fromParams) {
        $rootScope.toState = toState;
        $rootScope.toStateParams = toStateParams;
        $rootScope.fromState = fromState;
        $rootScope.fromParams = fromParams;

        var authenticated = principalService.isAuthenticated();

        if (!authenticated && toState.name !== 'login' && toState.name !== 'logout') {
            event.preventDefault();

            $timeout(function () {
                authorizationService.authorize();
            }, 300);
        } else if (authenticated && toState.name !== 'login' && toState.name !== 'logout'){
            if (!authorizationService.isAuthorized()){
                event.preventDefault();
                $state.go('main.administration.overview');
            }
        }
    });
}

function scrollBottomController($scope){
    var that = this;
    that.scroll = scroll;

    initialize();

    function initialize(){
    }

    $scope.$on('scrollDown', function(e) {
        if ($scope.element){
            if ($scope.element[0].scrollTop === 0 || $scope.element[0].scrollTop >= $scope.element[0].scrollHeight * 0.80) {
                $scope.element.scrollTop($scope.element[0].scrollHeight);
            }
        }
    });

    $scope.$on('scrollDown-force', function(e){
        if ($scope.element) {
            $scope.element.scrollTop($scope.element[0].scrollHeight);
        }
    })
}

function scrollBottomDirective() {
    return {
        scope: {
            scrollBottom: "="
        },
        controller: ['$scope', scrollBottomController],
        controllerAs: 'scrollBottomCtrl',
        link: function (scope, element) {
            scope.$watchCollection('scrollBottom', function (newValue) {
                if (newValue) {
                    scope.element = $(element);
                }
            });
        }
    }
}

angular.module('ComNet', ['ui.router', 'ngStorage'])
    .controller('loginCtrl', ['$scope', '$state', 'principalService', loginController])
    .controller('mainCtrl', ['$scope', '$state', 'principalService', mainController])
    .config(['$stateProvider', '$urlRouterProvider', '$locationProvider', appConfig])
    .run(['$rootScope', '$state', '$stateParams', '$timeout', 'authorizationService', 'principalService', appRun])
    .directive('scrollBottom', scrollBottomDirective);