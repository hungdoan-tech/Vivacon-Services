package com.vivacon.common.data_generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

public class MockDataConverter {

    private final String BASE_DIR = "./mock_data/txt/";

    @FunctionalInterface
    public interface FileWritingOperation {
        void write(String filePath, String inputData);
    }

    public void writeMockNamesToFile() {
        final String FINAL_FIRST_NAMES_PATH = BASE_DIR + "final_first_name.txt";

        changeLineBreakIntoComma(BASE_DIR + "male_first_name.txt", FINAL_FIRST_NAMES_PATH, this::writeDataToFile);

        changeLineBreakIntoComma(BASE_DIR + "female_first_name.txt", FINAL_FIRST_NAMES_PATH, this::writeDataToFile);

        changeLineBreakIntoComma(BASE_DIR + "euro_first_name.txt", FINAL_FIRST_NAMES_PATH, this::writeDataToFile);

        changeLineBreakIntoComma(BASE_DIR + "raw_last_name.txt", BASE_DIR + "final_last_name.txt", this::writeDataToFile);

        changeLineBreakIntoComma(BASE_DIR + "raw_words.txt", BASE_DIR + "final_words.txt", this::writeDataToFile);
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

    public static void writeMockImagesToFile(String[] args) {
        final String BASE_DIR = "./mock_data/txt/";
        final String FINAL_IMAGES_PATH = BASE_DIR + "final_image.txt";
        MockDataConverter converter = new MockDataConverter();
        converter.changeImageUrlToString(FINAL_IMAGES_PATH, converter::writeDataToFile);
    }

    public void changeImageUrlToString(String outputFilePath, FileWritingOperation operation) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 2000; i++) {
            String imageUrl = getRandomImageUrl();
            builder.append("\"");
            builder.append(imageUrl);
            builder.append("\", ");
            System.out.println("attachment" + i);
        }
        operation.write(outputFilePath, builder.toString());
    }

    private String getRandomImageUrl() {
        try {
            HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://picsum.photos/1080.jpg"))
                    .build();

            return client.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                    .thenApply(HttpResponse::uri)
                    .thenApply(uri -> uri.toString())
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
