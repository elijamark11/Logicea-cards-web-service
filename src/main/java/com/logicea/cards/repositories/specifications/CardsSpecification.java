package com.logicea.cards.repositories.specifications;

import com.logicea.cards.entities.cards.Card;
import com.logicea.cards.entities.users.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.logicea.cards.helper.HelperUtils.endDate;
import static com.logicea.cards.helper.HelperUtils.startDate;

public class CardsSpecification implements Specification<Card> {
    private final static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ROOT);

    public static Specification<Card> filterCards(String filterBy, String filterValue, User loggedInUser) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (filterBy.equalsIgnoreCase("status")) {
                list.add(criteriaBuilder.equal(root.get("status"), filterValue));
            }
            if (filterBy.equalsIgnoreCase("color")) {
                list.add(criteriaBuilder.equal(root.get("color"), filterValue));
            }
            if (filterBy.equalsIgnoreCase("name")) {
                list.add(criteriaBuilder.equal(root.get("name"), filterValue));
            }
            if (filterBy.equalsIgnoreCase("createdOn")) {
                try {
                    var date = formatter.parse(filterValue);
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdOn"), startDate(date)));
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdOn"), endDate(date)));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            if (loggedInUser.getUserTypeId() == 2L) {// if is member
                list.add(criteriaBuilder.equal(root.get("createdBy"), loggedInUser.getId()));
            }

            query.distinct(true);

            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        };
    }

    @Override
    public Specification<Card> and(Specification<Card> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Card> or(Specification<Card> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Card> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
