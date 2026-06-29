package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Hands on 1: Spring Data JPA - Quick Example.
 * Extending JpaRepository gives findAll(), findById(), save(), deleteById()
 * etc. for free, with no boilerplate implementation needed.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
}
