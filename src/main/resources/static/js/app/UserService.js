'use strict';

angular.module('app.services', []).factory('UserService',
    ['$localStorage', '$http', '$q',
        function ($localStorage, $http, $q) {

            var factory = {
                loadAllUsers: loadAllUsers,
                getAllUsers: getAllUsers,
                getUser: getUser,
                createUser: createUser,
                updateUser: updateUser,
                removeUser: removeUser
            };

            return factory;

            function loadAllUsers() {

                console.log('Fetching all users');

                var deferred = $q.defer();
                $http.get("http://localhost:8080/app/api/v1/users/")
                    .then(
                        function (response) {
                            console.log('Fetched successfully all users');
                            $localStorage.users = response.data;
                            console.log($localStorage.users);
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading users');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllUsers(){
            console.log('Service - getAllUsers function call');
                return $localStorage.users;
            }

            function getUser(id) {
                console.log('Fetching users with id :'+id);
                var deferred = $q.defer();
                $http.get("http://localhost:8080/app/api/v1/users/" + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully users with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading users with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createUser(users) {
                console.log('Creating users');
                var deferred = $q.defer();
                $http.post("http://localhost:8080/app/api/v1/users/", users)
                    .then(
                        function (response) {
                            loadAllUsers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating users : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateUser(users, id) {
                console.log('Updating users with id '+id);
                var deferred = $q.defer();
                $http.put("http://localhost:8080/app/api/v1/users/", users)
                    .then(
                        function (response) {
                            loadAllUsers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating users with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeUser(id) {
                console.log('Removing users with id '+id);
                var deferred = $q.defer();
                $http.delete("http://localhost:8080/app/api/v1/users/" + id)
                    .then(
                        function (response) {
                            loadAllUsers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing users with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);