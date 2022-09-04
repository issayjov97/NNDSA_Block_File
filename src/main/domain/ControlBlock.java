package main.domain;

public class ControlBlock implements IBlock {

    private final Integer dataBlocksCount;
    private final Integer dataBlockSize;
    private final Integer dataPerDataBlock;
    private final Integer controlBlockSize = 300;
    private  Integer actualDataBlock  = 0;

    public Integer getDataPerDataBlock() {
        return dataPerDataBlock;
    }

    public ControlBlock(Integer blocksCount, Integer dataBlockMaxSize, Integer dataPerDataBlock) {
        this.dataBlocksCount = blocksCount;
        this.dataBlockSize = dataBlockMaxSize;
        this.dataPerDataBlock = dataPerDataBlock;
    }

    public Integer getDataBlocksCount() {
        return dataBlocksCount;
    }

    public Integer getDataBlockSize() {
        return dataBlockSize;
    }

    public Integer getControlBlockSize() {
        return controlBlockSize;
    }

    public Integer getActualDataBlock() {
        return actualDataBlock;
    }

    public void setActualDataBlock(Integer actualDataBlock) {
        if (actualDataBlock > dataBlocksCount)
            throw new RuntimeException("Max number of data blocks has been allocated");
        this.actualDataBlock = actualDataBlock;
    }


}
