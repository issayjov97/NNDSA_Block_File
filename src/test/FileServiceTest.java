package test;

import main.domain.DataBlock;
import main.domain.Word;
import main.service.FileService;
import main.service.ImportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class FileServiceTest {
    private FileService<String, Word> fileService;

    @BeforeEach
    void setUp() throws IOException {
        this.fileService = new FileService<>("test.txt", 100, 800);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldFindRecordUsingBinarySearch() throws IOException {
        String key = "Turecko";
        ImportService.loadDictionary(fileService.getBlockFile(), 7);
        int index = fileService.blockBinarySearch(key);
        System.out.println(((DataBlock)fileService.loadDataBlock(index)).getDataList());
        Assertions.assertTrue(index >= 0);
    }

    @Test
    void interpolationSearch() throws IOException {
        String key = "Zuzana";
        System.out.println(dist("Anna", "Pavel"));
        ImportService.loadDictionary(fileService.getBlockFile(), 7);
        int index = fileService.blockInterpolationSearch(key);
        System.out.println(index);
        System.out.println(((DataBlock)fileService.loadDataBlock(index)).getDataList());
        Assertions.assertTrue(index >= 0);
    }

    private  BigDecimal dist(String s1, String s2) {
        int maxlen = Math.max(s1.length(), s2.length());
        BigDecimal d = BigDecimal.ZERO;
        for (int i = 0; i < maxlen; i++) {
            int dist;
            if (i < s1.length() && i < s2.length()) {
                dist = s1.charAt(i) - s2.charAt(i);
            } else if (i < s1.length()) {
                dist = s1.charAt(i);
            } else {
                dist = -s2.charAt(i);
            }
            d = d.add(BigDecimal.valueOf(dist * Math.pow(2, -i * 8)));
        }
        return d;
    }

    @Test
    void test() {
        List<Word> words = ImportService.getWords();
        Set<Integer> hashCodes = new HashSet<>();
        words.forEach(it->{
            hashCodes.add(it.hashCode());
        });
        Assertions.assertEquals(words.size(), hashCodes.size());
        char a = 'a';
        System.out.println((int)a);
    }
}