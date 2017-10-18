/**
 * Created by saman on 4/22/17.
 */
angular.module('myApp')
    .factory('UtilsFactory', function ($http, $mdToast) {
        return {
            SHA256: function (token) {
                return asmCrypto.SHA256.hex(token);
            },
            HttpPost: function (url, params) {
                return $http({
                    url: url,
                    method: "POST",
                    data: params,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
                    },
                    transformRequest: function (obj) {
                        var str = [];
                        for (var p in obj)
                            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                        return str.join("&");
                    },
                });
            },
            HttpPostWithFile: function (url, params) {
                var formData = new FormData();
                angular.forEach(params, function (value, key) {
                    formData.append(key, value);
                });
                return $http({
                    method: 'POST',
                    url: url,
                    headers: {
                        'Content-Type': undefined
                    },
                    data: formData,
                    transformRequest: angular.identity
                });
            },
            HttpGet: function (url, params) {
                return $http({
                    url: url,
                    method: "GET",
                    params: params,
                    responseType: 'arraybuffer'
                });
            },
            Toast: function (msg) {
                return $mdToast.show({
                    hideDelay: 3000,
                    position: 'top right',
                    controller: 'ErrorToastController',
                    templateUrl: '/resources/app/errorToast/errorToast.html',
                    locals: {
                        msg: msg
                    }
                });
            }
        };
    });
