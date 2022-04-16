package com.example.jpa.utils;

import com.example.api.resolver.paging.PagingParameters;
import com.example.jpa.OffsetPagination;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class PaginationHelper {

    public OffsetPagination toPagination(PagingParameters pagingParameters) {
        Sort sort = null;
        Integer limit = pagingParameters.getLimit();
        Integer offset = pagingParameters.getOffset();
        Map<String, String> fields = pagingParameters.getFields();
        if (fields != null) {
            List<Order> orders = fields.entrySet().stream()
                .filter(entry -> entry.getKey() != null && entry.getValue() != null)
                .map(entry -> {
                        if (entry.getValue().equalsIgnoreCase("DESC")) {
                            return Order.desc(entry.getKey());
                        } else {
                            return Order.asc(entry.getKey());
                        }
                    }
                )
                .collect(Collectors.toList());

            sort = Sort.by(orders);
        }

        return new OffsetPagination(offset, limit, sort);
    }

}

