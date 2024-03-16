package com.storeapp.repository;

import com.storeapp.domain.Ingredients;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryWithBagRelationships {
    Optional<Ingredients> fetchBagRelationships(Optional<Ingredients> post);

    List<Ingredients> fetchBagRelationships(List<Ingredients> ingredients);

    Page<Ingredients> fetchBagRelationships(Page<Ingredients> posts);
}
