package com.lms.util;

import com.lms.entity.common.PageResponse;
import java.util.List;

public class PageUtil {

    public static <T> PageResponse<T> paginate(
            List<T> list,
            int page,
            int size
    ) {
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, list.size());

        if (fromIndex >= list.size()) {
            return new PageResponse<>(
                    List.of(), page, size, list.size()
            );
        }

        return new PageResponse<>(
                list.subList(fromIndex, toIndex),
                page,
                size,
                list.size()
        );
    }
}
