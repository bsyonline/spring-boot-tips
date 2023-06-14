package com.rolex.tips;

import java.util.Comparator;
import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class DataComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        List list1 = (List)o1;
        List list2 = (List)o2;
        return ((Integer)list1.get(0)).compareTo(((Integer)list2.get(0)));
    }
}
