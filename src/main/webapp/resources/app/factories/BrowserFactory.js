/**
 * Created by Who on 5/14/2017.
 */
myApp.factory('BrowserFactory', ['$window', function($window) {

    return {
        version: function () {
            var userAgent = $window.navigator.userAgent;

            var browsers = {chrome: /chrome/i, safari: /safari/i, firefox: /firefox/i, ie: /internet explorer/i};

            for(var key in browsers) {
                if (browsers[key].test(userAgent)) {
                    return key;
                }
            };

            return 'unknown';
        }

    };

}]);