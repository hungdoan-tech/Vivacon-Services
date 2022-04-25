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

    public static final int AMOUNT_OF_USER = 1000;

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

    protected int exportMockDataToSQLFile(int startIndex, int endIndex, String domain) {
        File baseDir = new File(BASE_DIR);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        File file = new File(BASE_DIR + domain + ".sql");

        List<String> sqlStatements = generateSQLStatements(startIndex, endIndex);

        int lastCommaIndex = sqlStatements.get(sqlStatements.size() - 1).lastIndexOf(",");
        sqlStatements.set(sqlStatements.size() - 1, sqlStatements.get(sqlStatements.size() - 1).substring(0, lastCommaIndex) + ";");

        String setSequenceIdStatement = "\nSELECT setval('" + domain + "_id_seq', (SELECT MAX(id) FROM " + domain + ") + 1);";
        sqlStatements.add(setSequenceIdStatement);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String sqlStatement : sqlStatements) {
                writer.append(sqlStatement);
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } finally {
            return sqlStatements.size();
        }
    }

    public static void main(String[] args) {

        System.out.println("Start generating the account domain data");
        DataGenerator generator = new AccountGenerator();
        generator.exportMockDataToSQLFile(1, AMOUNT_OF_USER, "account");

        System.out.println("Start generating the following domain data");
        generator = new FollowingGenerator();
        generator.exportMockDataToSQLFile(1, AMOUNT_OF_USER, "following");

        System.out.println("Start generating the post domain data");
        generator = new PostGenerator();
        int amountOfPost = generator.exportMockDataToSQLFile(1, AMOUNT_OF_USER, "post") - 2;

        System.out.println("Start generating the attachment domain data");
        generator = new AttachmentGenerator();
        generator.exportMockDataToSQLFile(1, amountOfPost, "attachment");

        System.out.println("Start generating the comment domain data");
        generator = new CommentGenerator();
        generator.exportMockDataToSQLFile(1, amountOfPost, "comment");

        System.out.println("Start generating the like data");
        generator = new LikingGenerator();
        generator.exportMockDataToSQLFile(1, amountOfPost, "liking");
    }
}
