package main.domain;

public class ReportItem {
    private String  key;
    private Integer blocksTransferCount;

    public ReportItem(String key, Integer blocksTransferCount) {
        this.key = key;
        this.blocksTransferCount = blocksTransferCount;
    }

    public String getKey() {
        return key;
    }

    public Integer getBlocksTransferCount() {
        return blocksTransferCount;
    }

    @Override
    public String toString() {
        return  key + "; blocksTransferCount=" + blocksTransferCount + "\n";
    }
}
