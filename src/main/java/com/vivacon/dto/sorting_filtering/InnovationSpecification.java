package com.vivacon.dto.sorting_filtering;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Locale;

//public class InnovationSpecification implements Specification<Innovation> {
//
//    private transient QueryCriteria criteria;
//
//    public InnovationSpecification(QueryCriteria criteria) {
//        this.criteria = criteria;
//    }
//
//    @Override
//    public Predicate toPredicate(Root<Innovation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//        switch (criteria.getKey()) {
//            case "content": {
//                Expression<String> titleLowerCase = criteriaBuilder.lower(root.get("content"));
//                return criteriaBuilder.like(titleLowerCase, "%" + criteria.getValue().toString().toLowerCase(Locale.ROOT) + "%");
//            }
//            case "typeId": {
//                return criteriaBuilder.equal(root.get("type").get("id"), criteria.getValue());
//            }
//            case "projectName": {
//                return criteriaBuilder.equal(root.get("team").get("projectName"), criteria.getValue());
//            }
//            case "status": {
//                return criteriaBuilder.equal(root.get("status"), criteria.getValue());
//            }
//            case "accountId": {
//                return criteriaBuilder.equal(root.get("createdBy").get("id"), criteria.getValue());
//            }
//            case "areas": {
//                Subquery<InnovationArea> subQuery = criteriaQuery.subquery(InnovationArea.class);
//                Root<InnovationArea> subQueryRoot = subQuery.from(InnovationArea.class);
//                Predicate inAreaPredicate = criteriaBuilder.equal(subQueryRoot.get("area").get("id"), criteria.getValue());
//                Predicate matchInnovationIdPredicate = criteriaBuilder.equal(subQueryRoot.get("innovation").get("id"), root.get("id"));
//                subQuery.select(subQueryRoot).where(inAreaPredicate, matchInnovationIdPredicate);
//                return criteriaBuilder.exists(subQuery);
//            }
//            default: {
//                throw new IllegalArgumentException();
//            }
//        }
//    }
//}
