angular.module('myApp')
    .service('uploadService', ['$http', 'UtilsFactory', function ($http, UtilsFactory) {

        this.digestFile = function (file) {
            if (file) {
                return new Promise(function (resolve, reject) {
                    var fileReader = new FileReader();
                    fileReader.readAsBinaryString(file);
                    fileReader.onload = function (event) {
                        var content
                        if (!event) {
                            content = fileReader.content;
                        }
                        else {
                            content = event.target.result;
                        }
                        var hashedContent = UtilsFactory.SHA256(content)
                        resolve(hashedContent)
                    };
                    fileReader.onerror = function (evt) {
                        reject('There was an error in reading file, please check the file and try again.')
                    }
                })
            }
        };
    }]);