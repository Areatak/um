angular.module('myApp')
    .controller('InviteController',
        ['$scope', '$filter', 'EmailContactFactory', 'UtilsFactory', 'StorageFactory','$translate','$location',
            function ($scope, $filter, EmailContactFactory, UtilsFactory, StorageFactory,$translate,$location) {
                $scope.contacts = EmailContactFactory.getContacts();

                this.selectContact = function (selectedContact) {
                    if (selectedContact) {
                        var index = $scope.contacts.indexOf(selectedContact);
                        if ($scope.contacts[index].selected)
                            $scope.contacts[index].selected = false;
                        else
                            $scope.contacts[index].selected = true;
                        // alert(selectedContact["email"]);
                    }
                };
                this.invite = function () {
                    var list = $filter('filter')($scope.contacts, {selected: true});
                    var emailList = [];
                    for(var index in list){
                        emailList.push(list[index]["email"]);
                    }
                    //var list = _.filter($scope.contacts,'selected'  true);
                    $scope.loading = true;
                    UtilsFactory.HttpPost('/invite', {
                        token: StorageFactory.get('token'),
                        emails: emailList
                    })
                        .then(function (payload) {
                            $scope.loading = false;
                            if (payload.status === 200) {
                                $location.path('/');

                                // window.location = '/';
                            }

                        })
                        .catch(function (err) {
                            UtilsFactory.Toast({
                                text: $translate.instant('auth.google.inviteError'),
                                type: 'error'
                            });
                            $scope.loading = false;
                        })
                };
            }]);