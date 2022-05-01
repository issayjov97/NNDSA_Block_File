package main.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataBlock<K extends Comparable<K>, T extends Keyable<K>> implements IBlock {
    private final Integer dataPerBlock;
    private final List<T> dataList;

    public DataBlock(Integer dataPerBlock) {
        this.dataPerBlock = dataPerBlock;
        this.dataList = new ArrayList<>();
    }

    public boolean addData(T data) {
        if (isFree()) {
            dataList.add(data);
            dataList.sort(Comparator.comparing(Keyable::getKey));
            return true;
        }
        throw new RuntimeException("Block is full");
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

    public boolean isFree() {
        return dataPerBlock > dataList.size();
    }
}
