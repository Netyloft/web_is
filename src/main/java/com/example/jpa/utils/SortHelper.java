package com.example.jpa.utils;

import com.example.api.resolver.paging.PagingParameters;
import lombok.experimental.UtilityClass;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class SortHelper {

    public static List<Order> getSorting(PagingParameters paging, CriteriaBuilder criteriaBuilder, Root<?> root) {
        Map<String, String> fields = paging.getFields();
        List<Order> orders = new ArrayList<>();
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            Path<?> field = getPath(entry.getKey(), root);
            if (entry.getValue().equalsIgnoreCase("DESC")) {
                orders.add(criteriaBuilder.desc(field));
                continue;
            }
            orders.add(criteriaBuilder.asc(field));
        }

        return orders;
    }

    public static List<Order> extendOrderByField(
        List<Order> orders, String checkedName,
        String extendedName, Root<?> root,
        CriteriaBuilder criteriaBuilder, PagingParameters paging
    ) {
        if (checkIfOrderCanBeExtended(orders, checkedName)) {
            if (paging.getFields().keySet().stream().findFirst().get().equalsIgnoreCase("DESC")) {
                orders.add(criteriaBuilder.desc(root.get(extendedName)));
            }
            else {
                orders.add(criteriaBuilder.asc(root.get(extendedName)));
            }
            return orders;
        }

        return orders;
    }

    private static Boolean checkIfOrderCanBeExtended(List<Order> orders, String name) {
        return Objects.equals(orders.get(0).getExpression().getAlias(), name);
    }

    private static Path<?> getPath(String key, Root<?> root) {
        String[] keys = key.split("\\.");

        Path<?> path = null;

        for (String k: keys) {
            if (path == null) {
                path = root.get(k);
            } else {
                path = path.get(k);
            }
        }

        return path;
    }


}
