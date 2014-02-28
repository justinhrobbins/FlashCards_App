'use strict';

/* Controllers */

var flashCardsAppControllers = angular.module('flashCardsAppControllers', []);

flashCardsAppControllers.controller('TagListCtrl', ['$scope', 'Tag',
    function ($scope, Tag) {
        $scope.tags = Tag.query();
        $scope.orderProp = 'id';
    }]);

flashCardsAppControllers.controller('TagDetailCtrl', ['$scope', '$routeParams', 'Tag',
    function ($scope, $routeParams, Tag) {
        $scope.tag = Tag.get({tagId: $routeParams.tagId});
    }]);