'use strict';

/* Services */

var tagServices = angular.module('tagServices', ['ngResource']);

tagServices.factory('Tag', ['$resource',
    function ($resource) {
        return $resource('json/:tagId.json', {}, {
            query: {method: 'GET', params: {tagId: 'tags'}, isArray: true}
        });
    }]);
