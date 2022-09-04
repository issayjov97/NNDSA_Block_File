package test;

import main.domain.DataBlock;
import main.domain.Word;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DataBlockTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldAddDataInSortedOrder() {
        DataBlock<String, Word> dataBlock = new DataBlock<>();
        dataBlock.addData(new Word("cau", "cau", "cau"));
        dataBlock.addData(new Word("bye", "bye", "bye"));
        dataBlock.addData(new Word("ahoj", "hello", "cus"));

        Assertions.assertEquals(3, dataBlock.getDataList().size());
        Assertions.assertEquals("ahoj", dataBlock.getDataList().get(0).getKey());
    }

    @Test
    void shouldRemoveData() {
        DataBlock<String, Word> dataBlock = new DataBlock<>();
        dataBlock.addData(new Word("cau", "cau", "cau"));
        dataBlock.addData(new Word("bye", "bye", "bye"));
        dataBlock.addData(new Word("ahoj", "hello", "cus"));

        Assertions.assertEquals(3, dataBlock.getDataList().size());
        Assertions.assertTrue(dataBlock.removeData("ahoj"));
        Assertions.assertEquals(2, dataBlock.getDataList().size());

    }
}