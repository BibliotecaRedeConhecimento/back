package com.t2m.library.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.t2m.library.entities.Category;
import com.t2m.library.projections.CategoryProjection;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query(nativeQuery = true, value = """
			SELECT * FROM (
			SELECT DISTINCT c.id, c.name
			FROM tb_category c
			LEFT JOIN tb_category_domain as cd ON cd.category_id = c.id
			WHERE (:domainIds IS NULL OR cd.domain_id IN :domainIds)
			AND (LOWER(c.name) LIKE LOWER(CONCAT('%',:name,'%')))
			AND c.active = :active
			) AS tb_result
			""",
				countQuery = """
			SELECT COUNT(*) FROM (
			SELECT DISTINCT c.id, c.name
			FROM tb_category c
			LEFT JOIN tb_category_domain as cd ON cd.category_id = c.id
			WHERE (:domainIds IS NULL OR cd.domain_id IN :domainIds)
			AND (LOWER(c.name) LIKE LOWER(CONCAT('%',:name,'%')))
			AND c.active = :active
			) AS tb_result
			""")
	Page<CategoryProjection> searchCategories(List<Long> domainIds, String name, Boolean active, Pageable pageable);
	
	@Query("SELECT obj FROM Category obj LEFT JOIN FETCH obj.domains WHERE obj.id IN :categoryIds ORDER BY obj.id")
	List<Category> searchCategoriesWithDomains(List<Long> categoryIds);
}
