package main.domain;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private List<ReportItem> items;
    private int              blocksTransferSumm;
    private int              min;
    private int              max;

    public int getMin() {
        return items.stream().map(it -> it.getBlocksTransferCount()).min(Integer::compare).get();
    }

    public int getMax() {
        return items.stream().map(it -> it.getBlocksTransferCount()).max(Integer::compare).get();
    }

    public Report() {
        this.items = new ArrayList<>();
        blocksTransferSumm = 0;
    }

    public List<ReportItem> getItems() {
        return items;
    }

    public void addItem(ReportItem item) {
        this.items.add(item);
    }

    public int getBlocksTransferSumm() {
        return blocksTransferSumm;
    }

    public void setBlocksTransferSumm(int blocksTransferSumm) {
        this.blocksTransferSumm = blocksTransferSumm;
    }

    public void addCount(int count) {
        this.blocksTransferSumm += count;
    }

    @Override
    public String toString() {
        return ("----Report----" +
                "\n" + items +
                "\n blocksTransferSummary: " + blocksTransferSumm +
                "\n max blocks transfer per key: " + getMax() +
                "\n min blocks transfer per key: " + getMin()).replace(",", "");
    }
}
