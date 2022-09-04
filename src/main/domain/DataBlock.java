package main.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataBlock<K extends Comparable<K>, T extends Keyable<K>> implements IBlock {
    private final List<T> dataList;

    public DataBlock() {
        this.dataList = new ArrayList<>();
    }

    public boolean addData(T data) {
        dataList.add(data);
        dataList.sort(Comparator.comparing(Keyable::getKey));
        return true;
    }

    public boolean removeData(K key) {
        for (T data : dataList) {
            if (data.getKey().equals(key)) {
                return dataList.remove(data);
            }
        }
        return false;
    }

    public List<T> getDataList() {
        return dataList;
    }
}
