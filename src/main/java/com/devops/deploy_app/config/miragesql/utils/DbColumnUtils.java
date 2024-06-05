/*******************************************************************************
 * Class        ：CommonColumnUtils
 * Created date ：2021/06/17
 * Lasted date  ：2021/06/17
 * Author       ：Tan Tai
 * Change log   ：2021/06/17：01-00 Tan Tai create a new
 ******************************************************************************/
package com.devops.deploy_app.config.miragesql.utils;

import com.devops.deploy_app.config.miragesql.enumdef.AbstractSortEnum;
import com.miragesql.miragesql.annotation.Column;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * CommonColumnUtils.
 *
 * @author Tan Tai
 * @version 01-00
 * @since 01-00
 */
public class DbColumnUtils {

    /** The column default. */
    private static final String COLUMN_DEFAULT = "ID";
    private static final String COLUMN_CREATED_DATE = "createdDate";
    private static final String DEFAUL_SORT_COLUMN = "ID";
    private static final String E101700_SORT_DYNAMIC_TABLE = "101700_SORT_DYNAMIC_TABLE";

    /**
     * <p>
     * Get column list from entity.
     * </p>
     *
     * @author Tan Tai
     * @param entityClass
     *            type {@link Class<?>}
     * @return {@link List<String>}
     */
    protected static List<String> getColumnListFromEntity(Class<?> entityClass) {
        List<String> result = new ArrayList<>();
        Field[] fields = entityClass.getDeclaredFields(); // private fields
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                result.add(column.name());
            }
        }
        return result;
    }

    /**
     * <p>
     * Get column list from entity.
     * </p>
     *
     * @author Tan Tai
     * @param entityClass
     *            type {@link Class<?>}
     * @param name
     *            type {@link String}
     * @return {@link String}
     * @throws NoSuchFieldException

     */
    public static String getColumnListFromEntity(Class<?> entityClass, String name) throws NoSuchFieldException {
        String result = COLUMN_DEFAULT;
        Field field;
        Class<?> current = entityClass;
        while (current.getSuperclass() != null) { // we don't want to process
                                                  // Object.class
            // do something with current's fields
            try {
                field = current.getDeclaredField(name);
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    result = column.name();
                    current = current.getSuperclass();
                }
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        return result;
    }

    /**
     * <p>
     * Builds the sort enums.
     * </p>
     *
     * @author Tan Tai
     * @param <T>
     *            the generic type
     * @param sort
     *            type {@link Sort}
     * @param enumsDatas
     *            type {@link T[]}
     * @param clazz
     *            type {@link Class<?>}
     * @return {@link Sort}
     *
     */
    public static <T extends Enum<T>> Sort buildSortEnums(Sort sort, T[] enumsDatas, Class<?> clazz)
            throws Exception {
        List<Order> ordersRs = new ArrayList<>();
        String propertiesError = StringUtils.EMPTY;
        try {
            for (Order order : sort) {
                String property = order.getProperty();
                propertiesError = property;
                String valueMapingEnum = Stream.of(enumsDatas).filter(item -> item.toString().equals(property))
                        .map(Enum::name).findFirst().orElse(null);
                String columnSort = StringUtils.isNotBlank(valueMapingEnum) ? valueMapingEnum
                        : getColumnListFromEntity(clazz, property);

                if (StringUtils.isNotBlank(columnSort)) {
                    order = order.isAscending() ? Order.asc(columnSort) : Order.desc(columnSort);
                    ordersRs.add(order);
                } else {
                    throw new Exception("Sort column {0} was not found");
                }
            }
        } catch (Exception e) {
            throw new Exception("Sort column {0} was not found");
        }

        return Sort.by(ordersRs);
    }

    public static Sort buildSortAlias(Sort sort, Class<?> clazz, String alias) throws  Exception {
        List<Order> ordersRs = new ArrayList<>();
        String propertiesError = StringUtils.EMPTY;
        try {
            for (Order order : sort) {
                String property = order.getProperty();
                propertiesError = property;
                String columnSort = DbColumnUtils.getColumnListFromEntity(clazz, property);
                if (StringUtils.isNotBlank(columnSort)) {
                    order = order.isAscending() ? Order.asc(alias.concat(".").concat(columnSort))
                            : Order.desc(alias.concat(".").concat(columnSort));
                    ordersRs.add(order);
                } else {
                    throw new Exception("Sort column {0} was not found");
                }
            }
            // Set default sort: created date,desc
            String columnCreatedDate = DbColumnUtils.getColumnListFromEntity(clazz, COLUMN_CREATED_DATE);
            if (StringUtils.isNotBlank(columnCreatedDate)) {
                if (!columnCreatedDate.equals(DEFAUL_SORT_COLUMN)) {
                    Order order = Order.desc(alias.concat(".").concat(columnCreatedDate));
                    ordersRs.add(order);
                }
            } else {
                throw new Exception("Sort column {0} was not found");
            }
        } catch (Exception e) {
            throw new Exception("Sort column {0} was not found");
        }

        return Sort.by(ordersRs);
    }

    public Sort buildSortAliasNotUseDefault(Sort sort, Class<?> clazz, String alias) throws Exception {
        List<Order> ordersRs = new ArrayList<>();
        String propertiesError = StringUtils.EMPTY;
        try {
            for (Order order : sort) {
                String property = order.getProperty();
                propertiesError = property;
                String columnSort = DbColumnUtils.getColumnListFromEntity(clazz, property);

                /**
                 * IF property not equal column default sort and property not
                 * found clazz then break function
                 */
                if (COLUMN_DEFAULT.equals(columnSort) && !columnSort.equals(property)) {
                    continue;
                }
                if (StringUtils.isNotBlank(columnSort)) {
                    order = order.isAscending() ? Order.asc(alias.concat(".").concat(columnSort))
                            : Order.desc(alias.concat(".").concat(columnSort));
                    ordersRs.add(order);
                } else {
                    throw new Exception("Sort column {0} was not found");
                }
            }
        } catch (Exception e) {
            throw new Exception("Sort column {0} was not found");
        }

        return Sort.by(ordersRs);
    }

    public static <T extends AbstractSortEnum> Sort buildSortEnums(Sort sort, T[] enumsDatas, Class<?> clazz, String alias)
            throws Exception {
        List<Order> ordersRs = new ArrayList<>();
        String propertiesError = StringUtils.EMPTY;
        try {
            for (Order order : sort) {
                String property = order.getProperty();
                propertiesError = property;
                String valueMapingEnum = Stream.of(enumsDatas).filter(item -> item.getValue().equals(property))
                        .map(AbstractSortEnum::getValueMapping).findFirst().orElse(null);
                String columnSort = valueMapingEnum;

                if (StringUtils.isBlank(columnSort)) {
                    String columnSortEntity = DbColumnUtils.getColumnListFromEntity(clazz, property);
                    if (StringUtils.isNotBlank(columnSortEntity)) {
                        columnSort = String.format("%s.%s", alias, columnSortEntity);
                    }
                }

                boolean isReverse = Stream.of(enumsDatas).filter(item -> item.getValue().equals(property))
                        .map(AbstractSortEnum::isReverse).findFirst().orElse(false);

                boolean isAscending = isReverse ? !order.isAscending() : order.isAscending();

                if (StringUtils.isNotBlank(columnSort)) {
                    order = isAscending ? Order.asc(columnSort) : Order.desc(columnSort);
                    ordersRs.add(order);
                } else {
                    throw new Exception("Sort column {0} was not found");
                }
            }
        } catch (Exception e) {
            throw new Exception("Sort column {0} was not found");
        }

        return Sort.by(ordersRs);
    }

}
