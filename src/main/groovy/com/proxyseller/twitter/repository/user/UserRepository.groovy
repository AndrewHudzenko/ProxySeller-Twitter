package com.proxyseller.twitter.repository.user


import com.proxyseller.twitter.model.user.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByIdAndIsDeletedFalse(String id)

    Optional<User> findByEmailAndIsDeletedFalse(String email)

    Optional<User> findByUsernameAndIsDeletedFalse(String username)

    List<User> findAllByIsDeletedFalse()

    List<User> findAllByIdInAndIsDeletedFalse(Set<String> ids)



}