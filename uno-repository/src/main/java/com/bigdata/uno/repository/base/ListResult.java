package com.bigdata.uno.repository.base;

import com.google.common.collect.Lists;
import lombok.*;

import java.util.Iterator;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListResult<T> implements Iterable<T> {
    private Long total;
    private List<T> rows;

    public static <T> ListResult<T> empty() {
        return new ListResult<>(0L, Lists.newLinkedList());
    }

    public int size() {
        return rows.size();
    }

    @Override
    public Iterator<T> iterator() {
        return rows.iterator();
    }

    public static <T> ListResult<T> of(Long total, List<T> rows) {
        return new ListResult<>(total, rows);
    }

    public static <T> ListResult<T> of(Integer total, List<T> rows) {
        return new ListResult<>((long) total, rows);
    }
}
