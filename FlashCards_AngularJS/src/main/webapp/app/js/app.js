'use strict';

/* App Module */

var flashCardsApp = angular.module('flashCardsApp', [
    'ngRoute',
    'flashCardsAppFilters',
    'flashCardsAppControllers',
    'tagServices'
]);

flashCardsApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/tags', {
                templateUrl: 'partials/tag-list.html',
                controller: 'TagListCtrl'
            }).
            when('/tags/:tagId', {
                templateUrl: 'partials/tag-detail.html',
                controller: 'TagDetailCtrl'
            }).
            otherwise({
                redirectTo: '/tags'
            });
    }]);
