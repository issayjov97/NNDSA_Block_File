package main.service;

import main.domain.DataBlock;
import main.domain.Word;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ImportService {
    private static List<Word> words;

    public static List<Word> getWords() {
        if (words == null){
            words = importDictionary();
        }
        return words;
    }

    public static void loadDictionary(BlockFile blockFile) {
        DataBlock<String, Word> dataBlock = null;
        List<Word> words = getWords();
        for (int i = 0; i < words.size(); i++) {
            if (i % blockFile.getControlBlock().getDataPerDataBlock() == 0) {
                if (dataBlock != null && dataBlock.getDataList().size() > 0) {
                    blockFile.saveDataBlock(dataBlock);
                }
                dataBlock = new DataBlock<>();
            }

            dataBlock.addData(words.get(i));
        }
        blockFile.saveDataBlock(dataBlock);
    }

    private static List<Word> importDictionary()  {
        List<Word> words = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File("src/words"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.nextLine();
        while (sc.hasNext()) {
            String[] tmp = sc.nextLine().split(",");
            String cz = tmp[0].toLowerCase();
            String en = tmp[1].toLowerCase();
            String de = tmp[2].toLowerCase();
            words.add(new Word(cz, en, de));
        }
        sc.close();
        words.sort(Comparator.comparing(Word::getKey));
        return words;
    }
}
