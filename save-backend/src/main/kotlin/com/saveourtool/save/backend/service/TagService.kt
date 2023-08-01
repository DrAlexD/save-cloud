package com.saveourtool.save.backend.service

import com.saveourtool.save.backend.repository.TagRepository
import com.saveourtool.save.backend.repository.vulnerability.LnkVulnerabilityTagRepository
import com.saveourtool.save.backend.repository.vulnerability.VulnerabilityRepository
import com.saveourtool.save.entities.Tag
import com.saveourtool.save.entities.vulnerabilities.LnkVulnerabilityTag
import com.saveourtool.save.entities.vulnerabilities.Vulnerability
import com.saveourtool.save.utils.orNotFound
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * [Service] for [Tag] entity
 *
 * @property tagRepository
 */
@Service
class TagService(
    private val tagRepository: TagRepository,
    private val vulnerabilityRepository: VulnerabilityRepository,
    private val lnkVulnerabilityTagRepository: LnkVulnerabilityTagRepository,
) {
    /**
     * @param vulnerabilityName [Vulnerability.name]
     * @param tagName tag to add
     * @return new [LnkVulnerabilityTag]
     */
    @Transactional
    fun addVulnerabilityTag(vulnerabilityName: String, tagName: String): LnkVulnerabilityTag {
        val vulnerability = vulnerabilityRepository.findByName(vulnerabilityName).orNotFound {
            "Could not find vulnerability $vulnerabilityName"
        }
        val tag = tagRepository.findByName(tagName) ?: tagRepository.save(Tag(tagName))

        return lnkVulnerabilityTagRepository.save(
            LnkVulnerabilityTag(vulnerability, tag)
        )
    }

    /**
     * @param vulnerabilityName [Vulnerability.name]
     * @param tagName tag to delete
     * @return updated [Vulnerability]
     */
    @Transactional
    fun deleteVulnerabilityTag(vulnerabilityName: String, tagName: String) {
        val vulnerability = vulnerabilityRepository.findByName(vulnerabilityName).orNotFound {
            "Could not find vulnerability $vulnerabilityName"
        }

        val link = lnkVulnerabilityTagRepository.findByVulnerabilityIdAndTagName(
            vulnerability.requiredId(),
            tagName
        ).orNotFound { "Tag '$tagName' is not linked with vulnerability $vulnerabilityName." }

        lnkVulnerabilityTagRepository.delete(link)
    }
}