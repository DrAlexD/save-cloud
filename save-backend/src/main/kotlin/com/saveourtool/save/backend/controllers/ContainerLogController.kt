package com.saveourtool.save.backend.controllers

import com.saveourtool.save.backend.service.AgentService
import com.saveourtool.save.configs.ApiSwaggerSupport
import com.saveourtool.save.service.LogService
import com.saveourtool.save.utils.blockingToMono
import com.saveourtool.save.utils.toInstantAtDefaultZone
import com.saveourtool.save.v1
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.time.LocalDateTime

/**
 * Controller to fetch logs
 */
@ApiSwaggerSupport
@Tags(
    Tag(name = "logs"),
)
@RestController
@RequestMapping(path = ["/api/$v1/logs"])
class ContainerLogController(
    private val logService: LogService,
    private val agentService: AgentService,
) {
    @GetMapping("/by-application-name")
    @Operation(
        method = "GET",
        summary = "Get logs for containerName in provided time range.",
        description = "Get logs for containerName in provided time range.",
    )
    @Parameters(
        Parameter(name = "applicationName", `in` = ParameterIn.QUERY, description = "application name", required = true),
        Parameter(name = "from", `in` = ParameterIn.QUERY, description = "start of requested time range in ISO format with default time zone", required = true),
        Parameter(name = "to", `in` = ParameterIn.QUERY, description = "end of requested time range in ISO format with default time zone", required = true),
        Parameter(name = "limit", `in` = ParameterIn.QUERY, description = "limit for result", required = false),
    )
    @ApiResponse(responseCode = "200", description = "Successfully fetched logs for container.")
    fun getByApplicationName(
        @RequestParam applicationName: String,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) to: LocalDateTime,
        @RequestParam(required = false, defaultValue = LIMIT_DEFAULT) limit: Int,
    ): Mono<StringListResponse> = logService.getByApplicationName(applicationName, from.toInstantAtDefaultZone(), to.toInstantAtDefaultZone(), limit)
        .map { ResponseEntity.ok(it) }

    @GetMapping("/by-container-name")
    @Operation(
        method = "GET",
        summary = "Get logs for containerName in provided time range.",
        description = "Get logs for containerName in provided time range.",
    )
    @Parameters(
        Parameter(name = "containerName", `in` = ParameterIn.QUERY, description = "name of a container", required = true),
        Parameter(name = "from", `in` = ParameterIn.QUERY, description = "start of requested time range in ISO format with default time zone", required = true),
        Parameter(name = "to", `in` = ParameterIn.QUERY, description = "end of requested time range in ISO format with default time zone", required = true),
        Parameter(name = "limit", `in` = ParameterIn.QUERY, description = "limit for result", required = false),
    )
    @ApiResponse(responseCode = "200", description = "Successfully fetched logs for container.")
    fun getByContainerName(
        @RequestParam containerName: String,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) to: LocalDateTime,
        @RequestParam(required = false, defaultValue = LIMIT_DEFAULT) limit: Int,
    ): Mono<StringListResponse> = logService.getByContainerName(containerName, from.toInstantAtDefaultZone(), to.toInstantAtDefaultZone(), limit)
        .map { ResponseEntity.ok(it) }

    @GetMapping("/by-container-name/from-agent")
    @Operation(
        method = "GET",
        summary = "Get all logs from agent by container name.",
        description = "Get all logs from agent by container name.",
    )
    @Parameters(
        Parameter(name = "containerName", `in` = ParameterIn.QUERY, description = "name of a container", required = true),
        Parameter(name = "limit", `in` = ParameterIn.QUERY, description = "limit for result", required = false),
    )
    @ApiResponse(responseCode = "200", description = "Successfully fetched logs for container.")
    fun logs(
        @RequestParam containerName: String,
        @RequestParam(required = false, defaultValue = LIMIT_DEFAULT) limit: Int,
    ): Mono<StringListResponse> = blockingToMono {
        agentService.getAgentByContainerName(containerName)
    }
        .map {
            agentService.getAgentTimes(it)
        }
        .flatMap { (from, to) ->
            logService.getByContainerName(
                containerName,
                from.toInstantAtDefaultZone(),
                to.toInstantAtDefaultZone(),
                limit,
            )
        }
        .map { ResponseEntity.ok(it) }

    companion object {
        private const val LIMIT_DEFAULT = "1000"
    }
}