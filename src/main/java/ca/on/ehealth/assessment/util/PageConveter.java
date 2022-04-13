package ca.on.ehealth.assessment.util;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.StringUtils;

/**
 * create page object
 * @author yuxundu
 */
public class PageConveter {

    public static Pageable convert(Integer page, Integer size, String sort) {
        if (page == null) {
            page = 0;
        }

        if (size == null) {
            size = 20;
        }

        Sort s = null;
        if (sort != null) {
            s = parseParameterIntoSort(sort.split(";"), ",");
        }

        return sort != null? PageRequest.of(page, size, s): PageRequest.of(page, size);
    }

    static Sort parseParameterIntoSort(String[] source, String delimiter) {
        List<Order> allOrders = new ArrayList();
        String[] var3 = source;
        int var4 = source.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String part = var3[var5];
            if (part != null) {
                String[] elements = part.split(delimiter);
                Direction direction = elements.length == 0 ? null : Direction.fromString(elements[elements.length - 1]);

                for(int i = 0; i < elements.length; ++i) {
                    if (i != elements.length - 1 || direction == null) {
                        String property = elements[i];
                        if (StringUtils.hasText(property)) {
                            allOrders.add(new Order(direction, property));
                        }
                    }
                }
            }
        }

        return allOrders.isEmpty() ? null :  Sort.by(allOrders);
    }
}
