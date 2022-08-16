/**
 * This file contains util methods to throw exceptions
 */

package com.saveourtool.save.utils

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

/**
 * @param status passed to [ResponseStatusException]
 * @param messageCreator creates message for [ResponseStatusException]
 * @return current object or throw [ResponseStatusException] with http status [status] when object is null
 * @throws ResponseStatusException when object is null
 */
fun <T> T?.orResponseStatusException(status: HttpStatus, messageCreator: (() -> String?) = { null }): T =
        this ?: throw ResponseStatusException(status, messageCreator())

/**
 * @param messageCreator creates message for [ResponseStatusException]
 * @return current object or throw [ResponseStatusException] with status [HttpStatus.NOT_FOUND] when object is null
 * @throws ResponseStatusException when object is null
 */
fun <T> T?.orNotFound(messageCreator: (() -> String?) = { null }): T = orResponseStatusException(HttpStatus.NOT_FOUND, messageCreator)

/**
 * @param messageCreator creates message for [ResponseStatusException]
 * @return current object or throw [ResponseStatusException] with status [HttpStatus.CONFLICT] when object is null
 * @throws ResponseStatusException when object is null
 */
fun <T> T?.orConflict(messageCreator: (() -> String?) = { null }): T = orResponseStatusException(HttpStatus.CONFLICT, messageCreator)