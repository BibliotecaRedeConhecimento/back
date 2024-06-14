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
			SELECT DISTINCT k.id, k.title
			FROM tb_knowledge k
			LEFT JOIN tb_knowledge_category as kc ON kc.knowledge_id = k.id
			WHERE (:categoryIds IS NULL OR kc.category_id IN :categoryIds)
			AND (LOWER(k.title) LIKE LOWER(CONCAT('%',:title,'%')))
			AND k.active = :active
			ORDER BY k.id
			""",
				countQuery = """
			SELECT COUNT(*) FROM (
			SELECT DISTINCT k.id, k.title
			FROM tb_knowledge k
			LEFT JOIN tb_knowledge_category as kc ON kc.knowledge_id = k.id
			WHERE (:categoryIds IS NULL OR kd.category_id IN :categoryIds)
			AND (LOWER(k.title) LIKE LOWER(CONCAT('%',:title,'%')))
			AND k.active = :active
			ORDER BY k.id
			""")
	Page<KnowledgeProjection> searchKnowledges(List<Long> categoryIds, String title, Boolean active, Pageable pageable);
	
	@Query("SELECT obj FROM Knowledge obj LEFT JOIN FETCH obj.categories WHERE obj.id IN :knowledgeIds ORDER BY obj.id")
	List<Knowledge> searchKnowledgesWithCategories(List<Long> knowledgeIds);
}
