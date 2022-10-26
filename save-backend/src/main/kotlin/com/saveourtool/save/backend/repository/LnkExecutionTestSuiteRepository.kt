package com.saveourtool.save.backend.repository

import com.saveourtool.save.entities.Execution
import com.saveourtool.save.entities.LnkExecutionTestSuite
import com.saveourtool.save.spring.repository.BaseEntityRepository
import org.springframework.stereotype.Repository

/**
 * Repository of [LnkExecutionTestSuite]
 */
@Repository
interface LnkExecutionTestSuiteRepository : BaseEntityRepository<LnkExecutionTestSuite> {
    /**
     * @param execution execution that is connected to testSuite
     * @return [LnkExecutionTestSuite] by [execution]
     */
    fun findByExecution(execution: Execution): List<LnkExecutionTestSuite>

    /**
     * @param executionId execution id execution that is connected to testSuite
     * @return [LnkExecutionTestSuite] by [executionId]
     */
    fun findByExecutionId(executionId: Long): List<LnkExecutionTestSuite>

    /**
     * @param testSuiteId manageable test suite
     * @return [LnkExecutionTestSuite] by [testSuiteId]
     */
    fun findByTestSuiteId(testSuiteId: Long): List<LnkExecutionTestSuite>
}