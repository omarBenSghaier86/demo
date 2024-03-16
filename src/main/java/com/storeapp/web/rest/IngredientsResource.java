package com.storeapp.web.rest;

import com.storeapp.domain.Ingredients;
import com.storeapp.repository.IngredientRepository;
import com.storeapp.repository.PostRepositoryWithBagRelationshipsImpl;
import com.storeapp.security.AuthoritiesConstants;
import com.storeapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/ingredients")
@Transactional
public class IngredientsResource {

    private final Logger log = LoggerFactory.getLogger(IngredientsResource.class);

    private static final String ENTITY_NAME = "blogPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IngredientRepository postRepository;
    private final PostRepositoryWithBagRelationshipsImpl postRepositoryWithBagRelationships;


    public IngredientsResource(IngredientRepository postRepository,
                               PostRepositoryWithBagRelationshipsImpl postRepositoryWithBagRelationships) {
        this.postRepository = postRepository;
        this.postRepositoryWithBagRelationships = postRepositoryWithBagRelationships;
    }

    /**
     * {@code POST  /posts} : Create a new post.
     *
     * @param ingredients the post to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new post, or with status {@code 400 (Bad Request)} if the post has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ingredients> createIngredients(@Valid @RequestBody Ingredients ingredients) throws URISyntaxException {
        log.debug("REST request to save Post : {}", ingredients);
        if (ingredients.getId() != null) {
            throw new BadRequestAlertException("A new post cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ingredients result = postRepository.save(ingredients);
        return ResponseEntity
            .created(new URI("/api/posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }



    /**
     * {@code GET  /posts} : get all the posts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of posts in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.USER + "\")")
    public List<Ingredients> getAllIngredients(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Posts");
        return postRepository.findAll();
    }

    @GetMapping("/bestIngr")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Ingredients getBestSellingIngredint(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Posts");

       return postRepositoryWithBagRelationships.selectIngrAndSand();
    }

    /**
     * {@code GET  /posts/:id} : get the "id" post.
     *
     * @param id the id of the post to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the post, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ingredients> getIngredients(@PathVariable("id") Long id) {
        log.debug("REST request to get Post : {}", id);
        Optional<Ingredients> post = postRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(post);
    }

    /**
     * {@code DELETE  /posts/:id} : delete the "id" post.
     *
     * @param id the id of the post to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredients(@PathVariable("id") Long id) {
        log.debug("REST request to delete Post : {}", id);
        postRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
