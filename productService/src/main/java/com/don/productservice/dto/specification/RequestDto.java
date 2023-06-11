package com.don.productservice.dto.specification;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDto {
    private List<SearchRequestDto> searchRequestDto;
}
