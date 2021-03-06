package com.github.jntakpe.users.endpoint

import com.github.jntakpe.users.mapping.toEntity
import com.github.jntakpe.users.mapping.toResponse
import com.github.jntakpe.users.proto.ReactorUsersServiceGrpc
import com.github.jntakpe.users.proto.Users
import com.github.jntakpe.users.service.UserService
import org.bson.types.ObjectId
import reactor.core.publisher.Mono
import javax.inject.Singleton

@Singleton
class UsersEndpoint(private val service: UserService) : ReactorUsersServiceGrpc.UsersServiceImplBase() {

    override fun findById(request: Mono<Users.ByIdRequest>): Mono<Users.UserResponse> {
        return request
            .flatMap { service.findById(ObjectId(it.id)) }
            .map { it.toResponse() }
    }

    override fun findByUsername(request: Mono<Users.UsersByUsernameRequest>): Mono<Users.UserResponse> {
        return request
            .flatMap { service.findByUsername(it.username) }
            .map { it.toResponse() }
    }

    override fun create(request: Mono<Users.UserRequest>): Mono<Users.UserResponse> {
        return request
            .flatMap { service.create(it.toEntity()) }
            .map { it.toResponse() }
    }
}
