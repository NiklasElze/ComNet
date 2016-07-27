function mainController($scope, $state, principalService) {
    $scope.logout = function () {
        principalService.authenticate(null);
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
            roles: ['user']
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
            roles: ['user']
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
            roles: ['user']
        },
        views: {
            'main-content': {
                templateUrl: 'messages/html/messages.html',
                controller: 'mainCtrl'
            }
        }
    }).state('main.administration', {
        parent: 'main',
        url: '/administration',
        data: {
            roles: ['user']
        },
        views: {
            'main-content': {
                templateUrl: 'administration/html/administration.html',
                controller: 'mainCtrl'
            },
            'administration-content': {
                templateUrl: 'administration/html/administration-overview.html',
                controller: 'mainCtrl'
            }
        }
    }).state('main.administration.overview', {
        parent: 'main.administration',
        url: '/overview',
        data: {
            roles: ['user']
        },
        views: {
            'administration-content': {
                templateUrl: 'administration/html/administration-overview.html',
                controller: 'mainCtrl'
            }
        }
    }).state('main.administration.seminargroup', {
        parent: 'main.administration',
        url: '/seminargroup',
        data: {
            roles: ['user']
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
            roles: ['user']
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

        var authenticated = principalService.isAuthenticated();

        if (!authenticated && toState.name !== 'login' && toState.name !== 'logout') {
            event.preventDefault();

            $timeout(function () {
                authorizationService.authorize();
            }, 300);
        }
    });
}

angular.module('ComNet', ['ui.router'])
    .controller('loginCtrl', ['$scope', '$state', 'principalService', loginController])
    .controller('mainCtrl', ['$scope', '$state', 'principalService', mainController])
    .config(['$stateProvider', '$urlRouterProvider', '$locationProvider', appConfig])
    .run(['$rootScope', '$state', '$stateParams', '$timeout', 'authorizationService', 'principalService', appRun]);