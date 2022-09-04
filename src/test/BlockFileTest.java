package test;

import main.domain.ControlBlock;
import main.domain.DataBlock;
import main.domain.Word;
import main.service.BlockFile;
import main.service.ImportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.Collectors;

class BlockFileTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldSavedDataBlocks() throws IOException {
        BlockFile<String, Word> blockFile = new BlockFile<>("test.txt", 100, 800, 7);
        DataBlock<String, Word> dataBlock = new DataBlock<>();
        dataBlock.addData(new Word("cau", "cau", "cau"));
        dataBlock.addData(new Word("bye", "bye", "bye"));
        dataBlock.addData(new Word("ahoj", "hello", "cus"));

        DataBlock<String, Word> dataBlock2 = new DataBlock<>();
        dataBlock2.addData(new Word("abc", "bca", "cba"));
        dataBlock2.addData(new Word("def", "efd", "fde"));
        dataBlock2.addData(new Word("klm", "lmk", "mkl"));
        blockFile.saveDataBlock(dataBlock2);

        Assertions.assertTrue(blockFile.saveDataBlock(dataBlock));
        Assertions.assertTrue(blockFile.saveDataBlock(dataBlock2));
    }

    @Test
    void shouldLoadSavedDataBlocks() throws IOException {
        BlockFile<String, Word> blockFile = new BlockFile<>("test.txt", 100, 800, 7);
        DataBlock<String, Word> dataBlock = new DataBlock<>();
        dataBlock.addData(new Word("cau", "cau", "cau"));
        dataBlock.addData(new Word("bye", "bye", "bye"));
        dataBlock.addData(new Word("ahoj", "hello", "cus"));

        DataBlock<String, Word> dataBlock2 = new DataBlock<>();
        dataBlock2.addData(new Word("abc", "bca", "cba"));
        dataBlock2.addData(new Word("def", "efd", "fde"));
        dataBlock2.addData(new Word("klm", "lmk", "mkl"));
        blockFile.saveDataBlock(dataBlock);
        blockFile.saveDataBlock(dataBlock2);

        DataBlock<String, Word> loadedDataBlock = (DataBlock<String, Word>) blockFile.loadDataBlock(0);
        DataBlock<String, Word> loadedDataBlock2 = (DataBlock<String, Word>) blockFile.loadDataBlock(1);

        Assertions.assertEquals(
                dataBlock.getDataList().stream().map(Word::getKey).collect(Collectors.toList()),
                loadedDataBlock.getDataList().stream().map(Word::getKey).collect(Collectors.toList())
        );
        Assertions.assertEquals(
                dataBlock2.getDataList().stream().map(Word::getKey).collect(Collectors.toList()),
                loadedDataBlock2.getDataList().stream().map(Word::getKey).collect(Collectors.toList())
        );
    }

    @Test
    void shouldSaveControlBlock() throws IOException {
        BlockFile<String, Word> blockFile = new BlockFile<>("test.txt", 1000, 400, 7);
        Assertions.assertNotNull(blockFile.getControlBlock());
        Assertions.assertEquals(1000, blockFile.getControlBlock().getDataBlocksCount());
        Assertions.assertEquals(400, blockFile.getControlBlock().getDataBlockSize());
    }

    @Test
    void shouldLoadControlBlock() throws IOException {
        BlockFile<String, Word> blockFile = new BlockFile<>("test.txt", 1000, 400,7);
        ControlBlock loadedControlBlock = (ControlBlock) blockFile.loadControlBlock();
        Assertions.assertNotNull(loadedControlBlock);
        Assertions.assertEquals(1000, loadedControlBlock.getDataBlocksCount());
        Assertions.assertEquals(400, loadedControlBlock.getDataBlockSize());
    }
}