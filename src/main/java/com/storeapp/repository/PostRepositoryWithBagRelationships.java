package com.storeapp.repository;

import com.storeapp.domain.Post;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryWithBagRelationships {
    Optional<Post> fetchBagRelationships(Optional<Post> post);

    List<Post> fetchBagRelationships(List<Post> posts);

    Page<Post> fetchBagRelationships(Page<Post> posts);
}
