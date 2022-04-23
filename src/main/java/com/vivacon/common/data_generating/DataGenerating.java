package com.vivacon.common.data_generating;

import com.vivacon.common.constant.MockData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DataGenerating {

    public static void main(String[] args) {
        DataGenerating generator = new DataGenerating();
        generator.generateAccounts(7, 100000);
    }

    private final Random random = new Random();

    @FunctionalInterface
    public interface FileWritingOperation {
        void write(String filePath, String inputData);
    }

    public void writeMockNamesToFile(DataGenerating generator) {
        final String BASE_DIR = "./mock_data/";
        final String FINAL_FIRST_NAMES_PATH = BASE_DIR + "final_first_name.txt";

        generator.changeLineBreakIntoComma(BASE_DIR + "male_first_name.txt", FINAL_FIRST_NAMES_PATH, generator::writeDataToFile);

        generator.changeLineBreakIntoComma(BASE_DIR + "female_first_name.txt", FINAL_FIRST_NAMES_PATH, generator::writeDataToFile);

        generator.changeLineBreakIntoComma(BASE_DIR + "euro_first_name.txt", FINAL_FIRST_NAMES_PATH, generator::writeDataToFile);

        generator.changeLineBreakIntoComma(BASE_DIR + "raw_last_name.txt", BASE_DIR + "final_last_name.txt", generator::writeDataToFile);

        generator.changeLineBreakIntoComma(BASE_DIR + "raw_words.txt", BASE_DIR + "final_words.txt", generator::writeDataToFile);
    }

    public void changeLineBreakIntoComma(String inputFilePath, String outputFilePath, FileWritingOperation operation) {
        StringBuilder lines = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.append("\"");
                lines.append(line);
                lines.append("\"");
                lines.append(", ");
            }
            lines.delete(lines.length() - 2, lines.length() + 1);
            operation.write(outputFilePath, lines.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataToFile(String filePath, String names) {
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append("\n");
            writer.append(names);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateAccounts(int startIndex, int endIndex) {
        File file = new File("./accounts.sql");
        List<String> sqlStatements = generateSQLAccountInsertStatements(startIndex, endIndex);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String sqlStatement : sqlStatements) {
                writer.append(sqlStatement);
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generateSentence(int wordCount) {
        int numberOfWords = MockData.WORDS.length - 1;
        StringBuilder sentence = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            sentence.append(MockData.WORDS[random.nextInt(numberOfWords)]);
            sentence.append(" ");
        }
        return sentence.toString();
    }

    public List<String> generateSQLAccountInsertStatements(int startIndex, int endIndex) {
        List<String> values = new LinkedList<>();
        int firstNameLength = MockData.FIRST_NAME.length - 1;
        int lastNameLength = MockData.LAST_NAME.length - 1;
        String insertStatement = "INSERT INTO \"account\" (\"id\", \"full_name\", \"password\", \"refresh_token\", \"token_expired_date\", \"username\", \"role_id\", \"created_at\", \"created_by_account_id\", \"last_modified_at\", \"last_modified_by_account_id\", \"bio\", \"email\", \"verification_token\", verification_expired_date, \"active\") \nVALUES";
        values.add(insertStatement);

        for (int i = startIndex; i <= endIndex; i++) {

            String value = "([[id]], '[[full_name]]', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi', NULL, NULL, '[[username]]', 2, '[[create_at]]', NULL, NULL, NULL, '[[bio]]', '[[email]]', NULL, NULL, true),\n";

            String fullName = MockData.FIRST_NAME[random.nextInt(firstNameLength)] + " " + MockData.LAST_NAME[random.nextInt(lastNameLength)];
            String username = fullName.replace(" ", "") + UUID.randomUUID();
            String bio = generateSentence(10);
            String email = username + "@gmail.com";

            value = value.replace("[[id]]", String.valueOf(i));
            value = value.replace("[[full_name]]", fullName);
            value = value.replace("[[username]]", username);
            value = value.replace("[[bio]]", bio);
            value = value.replace("[[email]]", email);
            values.add(value);
        }
        return values;
    }
}
