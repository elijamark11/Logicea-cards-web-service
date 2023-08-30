package com.logicea.cards.repositories;

import com.logicea.cards.entities.cards.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CardsRepository extends PagingAndSortingRepository<Card, Long>, JpaSpecificationExecutor<Card> {
    List<Card> findAll();

    Optional<Card> findById(long id);

    Optional<Card> findByIdAndCreatedBy(long id, long createdBy);

    @Override
    Page<Card> findAll(Specification<Card> spec, Pageable pageable);
}