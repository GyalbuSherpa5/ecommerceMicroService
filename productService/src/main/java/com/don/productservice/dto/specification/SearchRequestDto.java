package com.don.productservice.dto.specification;

import com.don.productservice.eunm.Operation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDto {
    private String column;
    private String value;
    private Operation operation;
}
