'use strict';

/**
 * @ngdoc overview
 * @name webAdminApp
 * @description
 * # webAdminApp
 *
 * Main module of the application.
 */
var app = angular.module('webAdminApp', ['ngAnimate','ngCookies','ngFileUpload','ngResource','ngRoute','ngSanitize','ngTouch', 'ui.bootstrap', 'ui.tinymce']);

app.constant('config', {
    apiUrl: 'http://localhost:8080'
});

app.config(function ($routeProvider, $locationProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'views/main.html',
            controller: 'MainCtrl'
        })
        .when('/service', {
            templateUrl: 'views/service.html',
            controller: 'ServiceCtrl'
        })
        .otherwise({
            redirectTo: '/'
        });

    // enable html5Mode for pushstate ('#'-less URLs)
    $locationProvider.html5Mode(true);
});
