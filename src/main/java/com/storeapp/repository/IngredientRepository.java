package com.storeapp.repository;

import com.storeapp.domain.Ingredients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Post entity.
 *
 * When extending this class, extend PostRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface IngredientRepository extends PostRepositoryWithBagRelationships, JpaRepository<Ingredients, Long> {
    default Optional<Ingredients> findOneWithEagerRelationships(Long id) {
       // return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
        return null;
    }

    default List<Ingredients> findAllWithEagerRelationships() {
        //return this.fetchBagRelationships(this.findAllWithToOneRelationships());
        return null;
    }

    default Page<Ingredients> findAllWithEagerRelationships(Pageable pageable) {
       // return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
        return null;
    }


/*    @Query(value = "select post from Post post left join fetch post.blog", countQuery = "select count(post) from Post post")
    Page<Ingredients> findAllWithToOneRelationships(Pageable pageable);*/

  /*  @Query("select post from Post post left join fetch post.blog")
    List<Ingredients> findAllWithToOneRelationships();

    @Query("select post from Post post left join fetch post.blog where post.id =:id")
    Optional<Ingredients> findOneWithToOneRelationships(@Param("id") Long id);*/
}
