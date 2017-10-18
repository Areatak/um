angular.module('myApp')
	.directive('particles', function ($window, $timeout) {
		return {
			restrict: 'A',
			replace: true,
			template: '<div class="particleJs" id="particleJs"></div>',
			link: function (scope, element, attrs, fn) {
				scope.$on('$routeChangeStart', function (next, current) {
					element[0].remove();
				});
				setTimeout(function () {
					var el = document.getElementById('particleJs');
					el.className += " particles-ease-in";
				}, 2000);

				$timeout(function () {
					particlesJS('particleJs', {
						"particles": {
							"number": {
								"value": 90,
								"density": {
									"enable": true,
									"value_area": 1500
								}
							},
							"color": {
								"value": "#2962ff"
							},
							"shape": {
								"type": "polygon",
								"stroke": {
									"width": 0,
									"color": "#000000"
								},
								"polygon": {
									"nb_sides": 5
								},
								"image": {
									// "src": "img/github.svg",
									"width": 20,
									"height": 20
								}
							},
							"opacity": {
								"value": 0.7,
								"random": false

							},
							"size": {
								"value": 1,
								"random": false

							},
							"line_linked": {
								"enable": true,
								"distance": 140,
								"color": "#2962ff",
								"opacity": 0.4,
								"width": 1.4
							},
							"move": {
								"enable": true,
								"speed": 1,
								"direction": "none",
								"random": false,
								"straight": false,
								"out_mode": "out",
								"bounce": false,
								"attract": {
									"enable": false,
									"rotateX": 600,
									"rotateY": 1200
								}
							}
						},
						"interactivity": {
							"detect_on": "canvas",
							"events": {
								"onhover": {
									"enable": true,
									"mode": "grab"
								},
								"onclick": {
									"enable": false,
									"mode": "push"
								},
								"resize": true
							},
							"modes": {
								"grab": {
									"distance": 170.53621458328246,
									"line_linked": {
										"opacity": 0.518254059656801
									}
								},
								"bubble": {
									"distance": 400,
									"size": 40,
									"duration": 2,
									"opacity": 8,
									"speed": 3
								},
								"repulse": {
									"distance": 200,
									"duration": 0.4
								},
								"push": {
									"particles_nb": 4
								},
								"remove": {
									"particles_nb": 2
								}
							}
						},
						"retina_detect": false
					});
					// pJS.canvas.ctx.arc(p.x, p.y, radius, 0, Math.PI * 2, false);
				}, 2000)
			}
		};
	});
angular.module('myApp')
	.controller('TransactionValidateController', ['$scope', 'UtilsFactory', 'StorageFactory', '_',
		function ($scope, UtilsFactory, StorageFactory, _) {
		}]);


