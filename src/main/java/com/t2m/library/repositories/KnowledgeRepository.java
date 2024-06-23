package com.t2m.library.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.t2m.library.entities.Knowledge;

import filters.KnowledgeFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long>, JpaSpecificationExecutor<Knowledge> {

	record KnowledgeSpecification(@NotNull KnowledgeFilter filter) implements Specification<Knowledge> {
		@NotNull
		@Override
		public Predicate toPredicate(@NotNull Root<Knowledge> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder builder) {
			List<Predicate> exps = new ArrayList<>();
			filter.getTitle().ifPresent(title -> exps.add(builder.like(builder.lower(root.get("title")), "%"+title.toLowerCase()+"%")));

			filter.getCategories().ifPresent(categories -> {
				Predicate[] predicates = categories.stream()
						.map(category -> builder.equal(root.join("categories"), category))
						.toArray(Predicate[]::new);
				exps.add(builder.or(predicates));
			});

			filter.getDomains().ifPresent(domains -> {
				Predicate[] predicates = domains.stream()
						.map(domain -> builder.equal(root.join("categories").join("domains"), domain))
						.toArray(Predicate[]::new);
				exps.add(builder.or(predicates));
			});

			filter.getActive().ifPresent(active -> exps.add(builder.equal(root.get("active"), active)));

			filter.getNeedsReview().ifPresent(needsReview -> exps.add(builder.equal(root.get("needsReview"), needsReview)));


			return builder.and(exps.toArray(new Predicate[0]));
		};
	}

	Page<Knowledge> searchKnowledgesWithCategoriesAndCAndCollaboratorByNeedsReview(boolean needsReview, Pageable pageable);
}
