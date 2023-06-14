package com.rolex.tips;

import java.util.Collections;
import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class Sorter {
    public static List sort(List<List> list) {
        DataComparator comparator = new DataComparator();
        Collections.sort(list, comparator);
        return list;
    }
}
