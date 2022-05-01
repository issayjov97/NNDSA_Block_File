package main.domain;

public class ControlBlock implements IBlock {

    private final Integer dataBlocksCount;
    private final Integer dataBlockSize;
    private final Integer controlBlockSize = 400;
    private  Integer actualDataBlock  = 0;


    public ControlBlock(Integer blocksCount, Integer dataBlockMaxSize) {
        this.dataBlocksCount = blocksCount;
        this.dataBlockSize = dataBlockMaxSize;
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
