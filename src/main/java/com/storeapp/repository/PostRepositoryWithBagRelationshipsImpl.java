package com.storeapp.repository;

import com.storeapp.domain.Ingredients;
import com.storeapp.domain.Sandwich;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PostRepositoryWithBagRelationshipsImpl implements PostRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Ingredients> fetchBagRelationships(Optional<Ingredients> post) {
        return post.map(this::fetchTags);
    }

    @Override
    public Page<Ingredients> fetchBagRelationships(Page<Ingredients> posts) {
        return new PageImpl<>(fetchBagRelationships(posts.getContent()), posts.getPageable(), posts.getTotalElements());
    }

    @Override
    public List<Ingredients> fetchBagRelationships(List<Ingredients> ingredients) {
        return Optional.of(ingredients).map(this::fetchTags).orElse(Collections.emptyList());
    }

    Ingredients fetchTags(Ingredients result) {
        return entityManager
            .createQuery("select post from Post post left join fetch post.tags where post.id = :id", Ingredients.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

   public Ingredients selectIngrAndSand() {
        return entityManager.createQuery("select c from Ingredients c join c.sandwich group by c order by count(*) desc limit 1", Ingredients.class).getSingleResult();
    }

    public List<Long> getBestDaySandwich(Date date) {
        return entityManager.createQuery("SELECT cost FROM Sandwich WHERE date in :date", Long.class).setParameter("date",date).getResultList();
    }

    List<Ingredients> fetchTags(List<Ingredients> ingredients) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, ingredients.size()).forEach(index -> order.put(ingredients.get(index).getId(), index));
        List<Ingredients> result = entityManager
            .createQuery("select post from Post post left join fetch post.tags where post in :posts", Ingredients.class)
            .setParameter("posts", ingredients)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
