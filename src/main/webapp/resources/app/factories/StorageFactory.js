/**
 * Created by saman on 4/23/17.
 */
angular.module('myApp')
    .factory('StorageFactory', function (localStorageService) {
        var storageService = {};

        storageService.set = function (key, value) {
            if (localStorageService.isSupported) localStorageService.set(key, value);
            if (localStorageService.cookie.isSupported) localStorageService.cookie.set(key, value);
        };
        storageService.get = function (key) {
            var storageVal = localStorageService.get(key);
            var cookieVal = localStorageService.cookie.get(key);
            return storageVal || cookieVal;

        };
        storageService.remove = function (key) {
            localStorageService.cookie.remove(key);
            localStorageService.remove(key);
        }
        storageService.clear = function () {
            localStorageService.cookie.clearAll();
            localStorageService.clearAll();
        }
        return storageService;
    });