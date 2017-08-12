package com.example.demo.api.common

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class EntityNotFoundException(message: String) : RuntimeException(message)

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class BadRequestException(message: String) : RuntimeException(message)

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
class EntityAlreadyExistException(message: String) : RuntimeException(message)