/**
 * Created by Mehrdad 1396/02/24.
 */
myApp.factory('EmailContactFactory', ['$location', '$rootScope', function ($location, $rootScope) {
    var factory = {};

    factory.getContacts = function () {
        return contactList;
    };

    factory.fetchContacts = function () {
        auth(function(emails, photos) {
            factory.saveContacts(emails, photos);
            $location.path('/invite');
            $rootScope.$apply();
        });
    };
    factory.saveContacts = function (emails, photos) {
        contactList = [];
        for (var idx in emails) {
            var data = {
                id: emails[idx],
                email: emails[emails[idx]],
                photo: photos[emails[idx]]
            };
            contactList.push(data);
        }
    };

    var contactList = [];

    return factory;
}]);