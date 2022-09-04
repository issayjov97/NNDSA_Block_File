package test;

import main.domain.Word;
import main.service.BlockFile;
import main.service.ImportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ImportServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldLoadWordFromFile() throws IOException {
        BlockFile<String, Word> blockFile = new BlockFile<>("test.txt", 100, 800,7);
        ImportService.loadDictionary(blockFile);
        Assertions.assertNotEquals(0, blockFile.getControlBlock().getActualDataBlock());
    }
}