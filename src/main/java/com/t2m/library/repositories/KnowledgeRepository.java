package com.t2m.library.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.t2m.library.entities.Knowledge;
import com.t2m.library.projections.KnowledgeProjection;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long>{

	@Query(nativeQuery = true, value = """
			SELECT * FROM (
			SELECT DISTINCT k.id, k.title
			FROM tb_knowledge k
			INNER JOIN tb_knowledge_category as kc ON kc.knowledge_id = k.id
			INNER JOIN tb_category as c ON kc.category_id = c.id
			INNER JOIN tb_category_domain as cd ON cd.category_id = c.id
			WHERE (:domainIds IS NULL OR cd.domain_id IN :domainIds)
			AND (:categoryIds IS NULL OR kc.category_id IN :categoryIds)
			AND (LOWER(k.title) LIKE LOWER(CONCAT('%',:title,'%')))
			AND k.active = :active
			AND k.needs_review = :needsReview
			) AS tb_result
			""",
				countQuery = """
			SELECT COUNT(*) FROM (
			SELECT DISTINCT k.id, k.title
			FROM tb_knowledge k
			INNER JOIN tb_knowledge_category as kc ON kc.knowledge_id = k.id
			INNER JOIN tb_category as c ON kc.category_id = c.id
			INNER JOIN tb_category_domain as cd ON cd.category_id = c.id
			WHERE (:domainIds IS NULL OR cd.domain_id IN :domainIds)
			AND (:categoryIds IS NULL OR kc.category_id IN :categoryIds)
			AND (LOWER(k.title) LIKE LOWER(CONCAT('%',:title,'%')))
			AND k.active = :active
			AND k.needs_review = :needsReview
			) AS tb_result
			""")
	Page<KnowledgeProjection> searchKnowledges(List<Long> domainIds, List<Long> categoryIds, String title, Boolean active, Boolean needsReview, Pageable pageable);
	
	@Query("SELECT obj FROM Knowledge obj LEFT JOIN FETCH obj.categories WHERE obj.id IN :knowledgeIds ORDER BY obj.id")
	List<Knowledge> searchKnowledgesWithCategories(List<Long> knowledgeIds);

	Page<Knowledge> searchKnowledgesWithCategoriesByNeedsReview(boolean needsReview, Pageable pageable);

}
