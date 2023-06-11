package com.don.productservice.service;

import com.don.productservice.dto.specification.SearchRequestDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilterSpecification<T> {
    public Specification<T> getSearchSpecification(SearchRequestDto searchRequestDto) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
    }

    public Specification<T> getSearchSpecification(List<SearchRequestDto> searchRequestDto) {
        return (root, query, criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();

            for (SearchRequestDto requestDto : searchRequestDto) {
                Predicate equal = criteriaBuilder.equal(root.get(requestDto.getColumn()), requestDto.getValue());
                predicates.add(equal);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
