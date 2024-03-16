package com.storeapp.web.rest;

import com.storeapp.domain.Ingredients;
import com.storeapp.domain.Sandwich;
import com.storeapp.repository.SandwichRepository;
import com.storeapp.repository.IngredientRepository;
import com.storeapp.repository.PostRepositoryWithBagRelationshipsImpl;
import com.storeapp.security.AuthoritiesConstants;
import com.storeapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * REST controller for managing {@link com.okta.developer.blog.domain.Blog}.
 */
@RestController
@RequestMapping("/api/sandwich")
@Transactional
public class SandwichResource {

    private final Logger log = LoggerFactory.getLogger(SandwichResource.class);

    private static final String ENTITY_NAME = "blogBlog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SandwichRepository blogRepository;
    private final IngredientRepository postRepository;
    private final PostRepositoryWithBagRelationshipsImpl postRepositoryWithBagRelationships;

    public SandwichResource(SandwichRepository blogRepository, IngredientRepository postRepository,
                            PostRepositoryWithBagRelationshipsImpl postRepositoryWithBagRelationships        ) {
        this.blogRepository = blogRepository;
        this.postRepository = postRepository;
        this.postRepositoryWithBagRelationships = postRepositoryWithBagRelationships;
    }

    /**
     * {@code POST  /blogs} : Create a new blog.
     *
     * @param sandwich the blog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blog, or with status {@code 400 (Bad Request)} if the blog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sandwich> createSandwich(@Valid @RequestBody Sandwich sandwich) throws URISyntaxException {
        log.debug("REST request to save Blog : {}", sandwich);
        if (sandwich.getId() != null) {
            throw new BadRequestAlertException("A new blog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sandwich result = blogRepository.save(sandwich);
        return ResponseEntity
            .created(new URI("/api/blogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    @PostMapping("/order")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.USER + "\")")
    public Sandwich order(@RequestBody List<Ingredients> ingredientsDTO) {
        log.debug("REST request the message : {} to send to Kafka topic ", ingredientsDTO);
        Set<Ingredients> ingrs = new HashSet<>();
        long cost = 0 ;
        if(ingredientsDTO!=null){
            for (Ingredients e : ingredientsDTO) {
                Optional<Ingredients> base = postRepository.findById(e.getId());
                if(base!=null) {
                    Ingredients res = base.get();
                    Long y = res.getQty()-e.getQty();
                    if(y<0)
                        throw new BadRequestAlertException("Quantity was exceeded", e.getName(), "Qty");
                    res.setQty(y);
                    cost = cost +(e.getQty()*e.getPrice());
                    //     postRepository.save(res);
                    ingrs.add(res);
                }

            }
            postRepository.saveAll(ingrs);
            Sandwich s = new Sandwich("sandwich",ingrs);
            s.setCost(cost + cost*30/100);
s.setDate(Date.valueOf("2024-01-02"));
            return  blogRepository.save(s);
        }
        return null;
    }
    @GetMapping("/counSandwichSolde")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public long countSandwishSolde( ) {
        log.debug("REST request to get a page of Posts");
        return blogRepository.count();
    }
    @GetMapping("/bestDay")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public long getBestDay(
        @RequestParam("date")  Date date ) {
        log.debug("REST request to get a page of Posts");
       List<Long> s = postRepositoryWithBagRelationships.getBestDaySandwich(date);
       AtomicLong profit = new AtomicLong(0);
       if(s!=null)
           s.stream().forEach(e->profit.set(profit.get()+e));
        return profit.get();
    }

    /**
     * {@code GET  /blogs} : get all the blogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blogs in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.USER + "\")")
    public List<Sandwich> getAllSandwich() {
        log.debug("REST request to get all Blogs");
        return blogRepository.findAll();
    }

    /**
     * {@code GET  /blogs/:id} : get the "id" blog.
     *
     * @param id the id of the blog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sandwich> getSandwich(@PathVariable("id") Long id) {
        log.debug("REST request to get Blog : {}", id);
        Optional<Sandwich> blog = blogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(blog);
    }

    /**
     * {@code DELETE  /blogs/:id} : delete the "id" blog.
     *
     * @param id the id of the blog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSandwich(@PathVariable("id") Long id) {
        log.debug("REST request to delete Blog : {}", id);
        blogRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
