package com.storeapp.repository;


import com.storeapp.domain.Sandwich;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Blog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SandwichRepository extends JpaRepository<Sandwich, Long> {

}
