package com.chathall.springchatserver.models;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@Builder
public class SliceResponse<T> {
    private int page;

    private int size;

    private int numberOfElements;

    private boolean isFirst;

    private boolean isLast;

    private boolean hasNext;

    private boolean hasPrevious;

    private List<T> content;

    public static <T> SliceResponse<T> fromSlice(Slice<T> slice) {
        return SliceResponse.<T>builder()
                .page(slice.getNumber())
                .size(slice.getSize())
                .numberOfElements(slice.getNumberOfElements())
                .isFirst(slice.isFirst())
                .isLast(slice.isLast())
                .hasNext(slice.hasNext())
                .hasPrevious(slice.hasPrevious())
                .content(slice.getContent())
                .build();
    }
}
