package coding.yu.autoact.util;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: yu on 18-10-16
 */
public class SparseArrayUtils {

    public static <T> List<T> array2List(SparseArray<T> array) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            list.add(array.valueAt(i));
        }
        return list;
    }
}
