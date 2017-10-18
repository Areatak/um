angular.module('myApp')
    .controller('FaqController', ['$scope','$translate','$sce', function ($scope,$translate,$sce) {
        $scope.toggle = [];
        var title = $translate.instant('title');

        $scope.changeToggle = function(i){
            $scope.toggle[i] = !$scope.toggle[i];
        };

        $scope.changeText = function(text){
            var mamad =text.replace(title,"<span style=\"color: #2962ff;\">" + title + "</span>");
            return $sce.trustAsHtml(mamad);
        };
    }]);