package com.vivacon.common.data_generator;

import com.vivacon.common.constant.MockData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

public abstract class DataGenerator {

    protected final String BASE_DIR = "./mock_data/sql/";

    protected final Random RANDOM = new Random();

    protected final long START_OFFSET = Timestamp.valueOf("2020-01-01 00:00:00").getTime();

    protected final long END_OFFSET = Timestamp.valueOf("2022-02-01 00:00:00").getTime();

    protected final long DIFF_OFFSET = END_OFFSET - START_OFFSET;

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

    protected void exportMockDataToSQLFile(int startIndex, int endIndex, String fileName) {

        File file = new File(BASE_DIR + fileName);
        List<String> sqlStatements = generateSQLStatements(startIndex, endIndex);

        int lastCommaIndex = sqlStatements.get(sqlStatements.size() - 1).lastIndexOf(",");
        sqlStatements.set(sqlStatements.size() - 1, sqlStatements.get(sqlStatements.size() - 1).substring(0, lastCommaIndex) + ";");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String sqlStatement : sqlStatements) {
                writer.append(sqlStatement);
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        DataGenerator generator = new AccountGenerator();
        generator.exportMockDataToSQLFile(1, 10000, "accounts.sql");

        generator = new PostGenerator();
        generator.exportMockDataToSQLFile(1, 10000, "post.sql");
    }
}
