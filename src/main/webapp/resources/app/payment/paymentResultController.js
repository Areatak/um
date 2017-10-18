angular.module('myApp')
	.controller('PaymentResultController', ['$scope', 'data', '$mdDialog', '$translate',
		function ($scope, data, $mdDialog, $translate) {
			$scope.result = JSON.parse(data.response);
			$scope.uuid = data.uuid;
			$scope.waitingForPayment = true;

			var socket = new SockJS('/listen');
			var client = Stomp.over(socket);
			client.connect({}, function (frame) {
				// console.log("Connected: " + frame);
				client.subscribe('/user/' + $scope.uuid + '/queue/received', function (message) {
					var map = JSON.parse(message.body);
					if (map.seq === seq) {
						$scope.waitingForPayment = false;
					}
				});
			})
			$scope.cancel = function () {
				$mdDialog.hide('cancel');
			};
		}]);