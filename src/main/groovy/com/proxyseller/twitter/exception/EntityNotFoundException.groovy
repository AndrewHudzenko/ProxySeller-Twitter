package com.proxyseller.twitter.exception

class EntityNotFoundException extends RuntimeException {
    EntityNotFoundException(String message) {
        super(message)
    }
}
