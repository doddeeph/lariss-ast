package id.lariss.web.rest;

import id.lariss.repository.CaseSizeRepository;
import id.lariss.service.CaseSizeService;
import id.lariss.service.dto.CaseSizeDTO;
import id.lariss.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link id.lariss.domain.CaseSize}.
 */
@RestController
@RequestMapping("/api/case-sizes")
public class CaseSizeResource {

    private static final Logger LOG = LoggerFactory.getLogger(CaseSizeResource.class);

    private static final String ENTITY_NAME = "caseSize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaseSizeService caseSizeService;

    private final CaseSizeRepository caseSizeRepository;

    public CaseSizeResource(CaseSizeService caseSizeService, CaseSizeRepository caseSizeRepository) {
        this.caseSizeService = caseSizeService;
        this.caseSizeRepository = caseSizeRepository;
    }

    /**
     * {@code POST  /case-sizes} : Create a new caseSize.
     *
     * @param caseSizeDTO the caseSizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caseSizeDTO, or with status {@code 400 (Bad Request)} if the caseSize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CaseSizeDTO> createCaseSize(@Valid @RequestBody CaseSizeDTO caseSizeDTO) throws URISyntaxException {
        LOG.debug("REST request to save CaseSize : {}", caseSizeDTO);
        if (caseSizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new caseSize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        caseSizeDTO = caseSizeService.save(caseSizeDTO);
        return ResponseEntity.created(new URI("/api/case-sizes/" + caseSizeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, caseSizeDTO.getId().toString()))
            .body(caseSizeDTO);
    }

    /**
     * {@code PUT  /case-sizes/:id} : Updates an existing caseSize.
     *
     * @param id the id of the caseSizeDTO to save.
     * @param caseSizeDTO the caseSizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caseSizeDTO,
     * or with status {@code 400 (Bad Request)} if the caseSizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caseSizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CaseSizeDTO> updateCaseSize(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CaseSizeDTO caseSizeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CaseSize : {}, {}", id, caseSizeDTO);
        if (caseSizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caseSizeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caseSizeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        caseSizeDTO = caseSizeService.update(caseSizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caseSizeDTO.getId().toString()))
            .body(caseSizeDTO);
    }

    /**
     * {@code PATCH  /case-sizes/:id} : Partial updates given fields of an existing caseSize, field will ignore if it is null
     *
     * @param id the id of the caseSizeDTO to save.
     * @param caseSizeDTO the caseSizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caseSizeDTO,
     * or with status {@code 400 (Bad Request)} if the caseSizeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the caseSizeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the caseSizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CaseSizeDTO> partialUpdateCaseSize(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CaseSizeDTO caseSizeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CaseSize partially : {}, {}", id, caseSizeDTO);
        if (caseSizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caseSizeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caseSizeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CaseSizeDTO> result = caseSizeService.partialUpdate(caseSizeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caseSizeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /case-sizes} : get all the caseSizes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of caseSizes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CaseSizeDTO>> getAllCaseSizes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of CaseSizes");
        Page<CaseSizeDTO> page = caseSizeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /case-sizes/:id} : get the "id" caseSize.
     *
     * @param id the id of the caseSizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caseSizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CaseSizeDTO> getCaseSize(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CaseSize : {}", id);
        Optional<CaseSizeDTO> caseSizeDTO = caseSizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caseSizeDTO);
    }

    /**
     * {@code DELETE  /case-sizes/:id} : delete the "id" caseSize.
     *
     * @param id the id of the caseSizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCaseSize(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CaseSize : {}", id);
        caseSizeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}