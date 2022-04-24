package com.vivacon.common.data_generator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

import static com.vivacon.common.constant.MockImage.IMAGES;

public class AttachmentGenerator extends DataGenerator {

    @Override
    public List<String> generateSQLStatements(int startPostIndex, int endPostIndex) {
        List<String> values = new LinkedList<>();

        String insertStatement = "INSERT INTO \"attachment\" (\"id\", \"actual_name\", \"unique_name\", \"url\", \"timestamp\", \"post_id\", NULL) \nVALUES ";
        values.add(insertStatement);

        String value;
        long counting = 1L;
        for (int postIndex = startPostIndex; postIndex <= endPostIndex; postIndex++) {

            int attachmentCount = ThreadLocalRandom.current().nextInt(1, 3);
            for (int attachmentIndex = 0; attachmentIndex < attachmentCount; attachmentIndex++) {

                value = "([[id]], '[[actual_name]]', '[[unique_name]]', '[[url]]', [[timestamp]], [[post_id]], [[profile_id]]),\n";

                String timestamp = getRandomTimestamp();
                String actualName = UUID.randomUUID().toString();
                String uniqueName = actualName + timestamp;
                String url = IMAGES[RANDOM.nextInt(IMAGES.length)];

                value = value.replace("[[id]]", String.valueOf(counting));
                value = value.replace("[[actual_name]]", actualName);
                value = value.replace("[[unique_name]]", uniqueName);
                value = value.replace("[[url]]", url);
                value = value.replace("[[timestamp]]", timestamp);
                value = value.replace("[[post_id]]", String.valueOf(postIndex));
//                value = value.replace("[[profile_id]]", null);
                values.add(value);

                counting++;
            }
        }
        return values;
    }
}
