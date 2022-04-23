package com.vivacon.common.data_generating;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class DataGenerating {

    @FunctionalInterface
    public interface FileWritingOperation {
        void write(String filePath, String inputData);
    }

    public static void main(String[] args) {
        DataGenerating generator = new DataGenerating();
        generator.writeMockNamesToFile(generator);
    }

    public void writeMockNamesToFile(DataGenerating generator) {
        final String BASE_DIR = "./mock_data/";
        final String FINAL_FIRST_NAMES_PATH = BASE_DIR + "final_first_name.txt";

        generator.changeLineBreakIntoComma(BASE_DIR + "male_first_name.txt", FINAL_FIRST_NAMES_PATH,
                (filePath, inputData) -> generator.writeDataToFile(filePath, inputData));

        generator.changeLineBreakIntoComma(BASE_DIR + "female_first_name.txt", FINAL_FIRST_NAMES_PATH,
                (filePath, inputData) -> generator.writeDataToFile(filePath, inputData));

        generator.changeLineBreakIntoComma(BASE_DIR + "euro_first_name.txt", FINAL_FIRST_NAMES_PATH,
                (filePath, inputData) -> generator.writeDataToFile(filePath, inputData));

        generator.changeLineBreakIntoComma(BASE_DIR + "raw_last_name.txt", BASE_DIR + "final_last_name.txt",
                (filePath, inputData) -> generator.writeDataToFile(filePath, inputData));
    }

    public void changeLineBreakIntoComma(String inputFilePath, String outputFilePath, FileWritingOperation operation) {
        StringBuffer lines = new StringBuffer();
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

    public void generateAccounts() {
        File file = new File("./accounts.sql");
        List<String> sqlStatements = generateSQLForAccounts();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String sqlStatement : sqlStatements) {
                writer.append(sqlStatement);
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> generateSQLForAccounts() {
        List<String> statements = Arrays.asList("Select * from account\n", "Select * from account where account.id = 12");
        return statements;
    }
}
