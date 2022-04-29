package com.vivacon.mapper;

import com.vivacon.dto.sorting_filtering.PageDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageMapper {

    private PageMapper() {
    }

    public static <I, O> PageDTO<O> toPageDTO(Page<I> pageEntity, Class<O> classDto, Function<I, O> responseConverter) {

        List<I> oldContent = pageEntity.getContent();
        List<O> newContent = toDTOs(oldContent, classDto, responseConverter);

        PageDTO<O> pageResponse = new PageDTO<>();
        pageResponse.setContent(newContent);
        pageResponse.setSize(pageEntity.getSize());
        pageResponse.setTotalPages(pageEntity.getTotalPages());
        pageResponse.setCurrentPage(pageEntity.getNumber());
        pageResponse.setTotalElements(pageEntity.getTotalElements());
        pageResponse.setNumberOfElement(pageEntity.getNumberOfElements());
        pageResponse.setFirst(pageResponse.getCurrentPage() == 0);
        pageResponse.setLast(pageResponse.getCurrentPage() >= pageEntity.getTotalPages() - 1);
        pageResponse.setEmpty(pageEntity.getTotalElements() == 0);
        return pageResponse;
    }

    public static <I, O> List<O> toDTOs(List<I> oldContent, Class<O> classDto, Function<I, O> responseConverter) {
        return oldContent.stream()
                .map(entity -> classDto.cast(responseConverter.apply(entity)))
                .collect(Collectors.toList());
    }
}
