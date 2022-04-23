package com.vivacon.common.data_generator;

import com.vivacon.common.constant.MockData;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

public abstract class DataGenerator {

    protected final Random RANDOM = new Random();

    protected final long START_OFFSET = Timestamp.valueOf("2020-01-01 00:00:00").getTime();

    protected final long END_OFFSET = Timestamp.valueOf("2022-02-01 00:00:00").getTime();

    protected final long DIFF_OFFSET = END_OFFSET - START_OFFSET;

    public abstract void exportMockDataToSQLFile(int startIndex, int endIndex);

    public abstract List<String> generateSQLStatements(int startIndex, int endIndex);

    protected String generateSentence(int wordCount) {
        int numberOfWords = MockData.WORDS.length - 1;
        StringBuilder sentence = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            sentence.append(MockData.WORDS[RANDOM.nextInt(numberOfWords)]);
            sentence.append(" ");
        }
        return sentence.toString();
    }

    protected String getRandomTimestamp() {
        Timestamp rand = new Timestamp(START_OFFSET + (long) (Math.random() * DIFF_OFFSET));
        return rand.toString();
    }

    public static void main(String[] args) {
        AccountGenerator accountGenerator = new AccountGenerator();
        accountGenerator.exportMockDataToSQLFile(7, 100000);
    }
}
