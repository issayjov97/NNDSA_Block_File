package main.service;

import main.domain.ControlBlock;
import main.domain.DataBlock;
import main.domain.IBlock;
import main.domain.Keyable;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class BlockFile<K extends Comparable<K>, T extends Serializable & Keyable<K>> {
    private ControlBlock         controlBlock;
    private RandomAccessFile     randomAccessFile;
    private SerializationService serializationService;

    public BlockFile(String filename, int blocksCount, int dataBlockMaxSize) throws IOException {
        this.randomAccessFile = new RandomAccessFile(filename, "rw");
        this.controlBlock = new ControlBlock(blocksCount, dataBlockMaxSize);
        this.serializationService = new SerializationService();
        saveControlBlock(this.controlBlock);
    }

    public IBlock loadDataBlock(Integer index) {
        return loadBlock(index);
    }

    public boolean saveDataBlock(DataBlock<K, T> dataBlock) {
        if (dataBlock.getDataList().size() > controlBlock.getDataBlockSize())
            throw new RuntimeException("Out of data block memory");
        try {
            randomAccessFile.seek(getBlockOffset(controlBlock.getActualDataBlock()));
            randomAccessFile.write(serializationService.serialize(dataBlock));
        } catch (IOException e) {
            e.printStackTrace();
        }
        controlBlock.setActualDataBlock(controlBlock.getActualDataBlock() + 1);
        return true;
    }

    public IBlock loadControlBlock() throws IOException {
        randomAccessFile.seek(0);
        byte[] bytes = new byte[controlBlock.getControlBlockSize()];
        randomAccessFile.read(bytes, 0, controlBlock.getControlBlockSize());
        return serializationService.deserialize(bytes);
    }

    private void saveControlBlock(ControlBlock controlBlock) {
        try {
            randomAccessFile.seek(0);
            byte[] bytes = serializationService.serialize(controlBlock);
            randomAccessFile.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private IBlock loadBlock(int blockIndex) {
        try {
            randomAccessFile.seek(getBlockOffset(blockIndex));
            byte[] bytes = new byte[controlBlock.getDataBlockSize()];
            randomAccessFile.read(bytes);
            return serializationService.deserialize(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getBlockOffset(int index) {
        return controlBlock.getControlBlockSize() + ((index) * controlBlock.getDataBlockSize());
    }

    public ControlBlock getControlBlock() {
        return controlBlock;
    }

    public Integer getActualBlocksCount() {
        return controlBlock.getActualDataBlock() + 1;
    }

}
