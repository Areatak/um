'use strict';

// Declare app level module which depends on views, and components
var myApp = angular.module('myApp', [
    'ngRoute',
    'ngMaterial',
    'ngAnimate',
    // 'ui.bootstrap',
    'ngMessages',
    'ngFileUpload',
    'angular-growl',
    'LocalStorageModule',
    'md.data.table',
    'monospaced.qrcode',
    'pascalprecht.translate',
    "ngSanitize",
    "com.2fdevs.videogular",
    "com.2fdevs.videogular.plugins.controls",
    "com.2fdevs.videogular.plugins.overlayplay",
    "com.2fdevs.videogular.plugins.poster",
    "ngMap",
    'angular-loading-bar',
    "ADM-dateTimePicker",
    'vAccordion',
    // 'myApp.view1',
    // 'myApp.view2',
    'myApp.version',
    'myApp.home',
    'hl.sticky'
]).config(['$locationProvider', '$routeProvider', '$mdThemingProvider', 'growlProvider', 'ADMdtpProvider',
    function ($locationProvider, $routeProvider, $mdThemingProvider, growlProvider, ADMdtp) {
    //var dateformat = $translateProvider.instant('dateformat');
    ADMdtp.setOptions({
        calType: 'gregorian',
        dtpType: 'date&time',
        format: 'MM/DD/YYYY, HH:mm',
        // default: 'today',
        autoClose: true,
        multiple:false
    });

    $routeProvider
	    .when("/", {
		    templateUrl: "/resources/app/home/home.html",
		    controller: 'HomeController'
	    })
	    .when("/register", {
		    resolve: {
			    mode: function ($route) { $route.current.params.mode = 'register'; }
		    },
		    templateUrl: "/resources/app/home/home.html",
		    controller: 'HomeController'
	    })
        .when("/about", {
            templateUrl: "/resources/app/about/about.html",
            controller: 'AboutController'
        })
        .when("/help", {
            templateUrl: "/resources/app/help/help.html",
            controller: 'HelpController'
        })
        .when("/faq", {
            templateUrl: "/resources/app/faq/faq.html",
            controller: 'FaqController'
        })
        .when("/terms", {
            templateUrl: "/resources/app/terms/terms.html",
            controller: 'TermsController'
        })
        .when("/privacy", {
            templateUrl: "/resources/app/privacy/privacy.html",
            controller: 'PrivacyController'
        })
        .when("/whitepaper", {
            templateUrl: "/resources/app/whitepaper/whitepaper.html",
            controller: 'WhitePaperController'
        })
        .when("/enterprise", {
            templateUrl: "/resources/app/enterprise/enterprise.html",
            controller: 'EnterpriseController'
        })
        .when("/contact", {
            templateUrl: "/resources/app/contact/contact.html",
            controller: 'ContactController'
        })
        .when("/job", {
            templateUrl: "/resources/app/job/job.html",
            controller: 'JobController'
        })
        .when("/transaction/list", {
            templateUrl: "/resources/app/transaction/transactionList.html",
            controller: 'TransactionListController',
            resolve: {
                isAuthenticated: function (StorageFactory, $location, $mdDialog) {
                    if (!StorageFactory.get('isLogin')) {
                        $location.path('/');
                        $mdDialog.show({
                            controller: 'SigninController',
                            templateUrl: '/resources/app/signin/signin.html',
                            parent: angular.element(document.body),
                            clickOutsideToClose: true,
                        })
                    }
                }
            }
        })
        .when('/transaction/detail/:id', {
            templateUrl: "/resources/app/transaction/transactionDetail.html?v=2",
            controller: 'TransactionDetailController'
        })
        .when('/validate', {
            templateUrl: "/resources/app/transaction/transactionValidate.html",
            controller: 'TransactionValidateController'
        })
        .when("/invite", {
            templateUrl: "/resources/app/invite/invite.html",
            controller: 'InviteController'
        })
        .when("/recover", {
            templateUrl: "/resources/app/recover/recover.html"
        })
        .otherwise({redirectTo: '/'});
    // $locationProvider.html5Mode(true);
    $mdThemingProvider.definePalette('white', {
        '50': 'ffffff',
        '100': 'ffffff',
        '200': 'ffffff',
        '300': 'ffffff',
        '400': 'ffffff',
        '500': 'ffffff',
        '600': 'ffffff',
        '700': 'ffffff',
        '800': 'ffffff',
        '900': 'ffffff',
        'A100': 'ffffff',
        'A200': 'ffffff',
        'A400': 'ffffff',
        'A700': 'ffffff',
        'contrastDefaultColor': 'dark'
    });

    $mdThemingProvider
        .theme('default')
        .primaryPalette('blue')
        .accentPalette('indigo')
        // .warnPalette('yellow')
        .backgroundPalette('white');

    $locationProvider.hashPrefix('');
    growlProvider.globalTimeToLive({success: 1000, error: 2000, warning: 3000, info: 4000});
    // localStorageServiceProvider
    //     .setPrefix('myApp')
    // .setStorageType('sessionStorage')
    // .setNotify(true, true)
    // localStorageServiceProvider.setPrefix('gazzete')
    // localStorageServiceProvider.setStorageType('sessionStorage');
    // localStorageServiceProvider.setStorageCookie(30, '/', false)
    //sample for override time to live when calling the methods
    //growl.warning("Override global ttl setting", {ttl: 10000});
    //disable/enable count down
    //growl.warning("Does not have count down timer", {disableCountDown: true});
}]);
myApp.constant('_', window._);
myApp.config(function (localStorageServiceProvider) {
    localStorageServiceProvider.setPrefix('utadoc')

    // .setStorageCookie(30, '/', false)
    // .setNotify(true, true);
    localStorageServiceProvider.setStorageType('localStorage')
    localStorageServiceProvider.setNotify(true, true);
    localStorageServiceProvider.setStorageCookie(30, '/', false)
});

