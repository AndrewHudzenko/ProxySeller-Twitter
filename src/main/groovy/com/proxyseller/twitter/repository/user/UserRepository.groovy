package com.proxyseller.twitter.repository.user


import com.proxyseller.twitter.model.user.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByIdAndIsDeletedFalse(String id)

    Optional<User> findByEmailAndIsDeletedFalse(String email)

    Optional<User> findByUsernameAndIsDeletedFalse(String username)

    // method to find all users with id_deleted = false
    List<User> findAllByIsDeletedFalse()

    // method to find all users by list of ids with id_deleted = false
    List<User> findAllByIdInAndIsDeletedFalse(Set<String> ids)



}