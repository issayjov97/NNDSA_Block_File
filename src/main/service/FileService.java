package main.service;

import main.domain.DataBlock;
import main.domain.IBlock;
import main.domain.Keyable;

import java.io.IOException;
import java.io.Serializable;

public class FileService<K extends Comparable<K>, T extends Serializable & Keyable<K>> {

    private final BlockFile<K, T> blockFile;
    private       int             blockTransferCount = 0;

    public FileService(String filename, int blocksCount, int dataBlockMaxSize) throws IOException {
        this.blockFile = new BlockFile<>(filename, blocksCount, dataBlockMaxSize);
    }

    public IBlock loadDataBlock(Integer index) {
        return blockFile.loadDataBlock(index);
    }

    public boolean saveDataBlock(DataBlock<K, T> dataBlock) {
        return blockFile.saveDataBlock(dataBlock);
    }

    public IBlock loadControlBlock() throws IOException {
        return blockFile.loadControlBlock();
    }

    public int blockBinarySearch(K key) throws IOException {
        int low = 0;
        int high = blockFile.getControlBlock().getActualDataBlock() - 1;
        while (low <= high) {
            int mid = low + ((high - low) / 2);
            DataBlock<K, T> foundBlock = (DataBlock<K, T>) blockFile.loadDataBlock(mid);
            blockTransferCount += 1;
            int result = BinarySearch.binarySearch(key, foundBlock.getDataList());
            if (result == 0)
                return mid;
            if (result > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public int blockInterpolationSearch(K key) throws IOException {
        int lowEnd = 0;
        int highEnd = blockFile.getControlBlock().getActualDataBlock() - 1;
        DataBlock<K, T> mostLeftDataBlock = ((DataBlock<K, T>) blockFile.loadDataBlock(lowEnd));
        DataBlock<K, T> mostRightDataBlock = ((DataBlock<K, T>) blockFile.loadDataBlock(highEnd));
        K leftKey = mostLeftDataBlock.getDataList().get(0).getKey();
        K rightKey = mostRightDataBlock.getDataList().get(mostRightDataBlock.getDataList().size() - 1).getKey();
        DataBlock<K, T> probeDataBlock;
        while (key.compareTo(leftKey) >= 0 && key.compareTo(rightKey) <= 0) {
            int probe = lowEnd + InterpolationSearch.interpolate((String) leftKey, (String) rightKey, (String) key, highEnd - lowEnd);
            System.out.println("Probe: " + probe);
            probeDataBlock = (DataBlock<K, T>) blockFile.loadDataBlock(probe);
            blockTransferCount += 1;
            int result = InterpolationSearch.interpolationSearch(key, probeDataBlock.getDataList());
            if (result == 0) {
                return probe;
            }
            if (result > 0) {
                lowEnd = probe + 1;
            } else {
                highEnd = probe - 1;
            }
        }
        return -1;
    }

    @Deprecated
    private int getKey(String value) {
        int key = 0;
        for (char a : value.toCharArray()) {
            key += (int) a;
        }
        return key;
    }

    public Integer getBlockTransferCount() {
        return blockTransferCount;
    }

    public void setBlockTransferCount(Integer blockTransferCount) {
        this.blockTransferCount = blockTransferCount;
    }

    public BlockFile<K, T> getBlockFile() {
        return blockFile;
    }

    public Integer getActualBlocksCount() {
        return blockFile.getActualBlocksCount();
    }

}
