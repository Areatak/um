/**
 * Created by Who on 5/6/2017.
 */
myApp
    .factory('UserFactory', function (StorageFactory, UtilsFactory) {
        var userFactory = {};

        userFactory.signIn = function (params) {
            return UtilsFactory.HttpPost('/login', {
                email: params.email,
                passwordHash: params.passwordHash,
                platform: params.platform
            })
                .then(function (payload) {
                    if (payload.status === 200) {
                        StorageFactory.set('userId', payload.data.userId);
                        StorageFactory.set('email', payload.data.email);
                        StorageFactory.set('token', payload.data.token);
                        StorageFactory.set('isLogin', true);
                        StorageFactory.set('passHash', params.passwordHash);
                        StorageFactory.set('fname', payload.data.name);
                        StorageFactory.set('lname', payload.data.lastName);
                        StorageFactory.set('pic', payload.data.profilePicAddress);
                        StorageFactory.set('credit', payload.data.credit);
                    }
                    return payload;
                });
        }
        userFactory.getUserCredit = function () {
            return UtilsFactory.HttpPost('/getUser', {
                token: StorageFactory.get('token')
            })
                .then(function (payload) {
                    return payload.data.credit
                })
                .catch(function (err) {
                    return new Error(500)
                })
        }
        return userFactory;
    });