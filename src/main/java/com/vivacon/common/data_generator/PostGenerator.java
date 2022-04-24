package com.vivacon.common.data_generator;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PostGenerator extends DataGenerator {

    @Override
    public List<String> generateSQLStatements(int startAccountIndex, int endAccountIndex) {
        List<String> values = new LinkedList<>();

        String insertStatement = "INSERT INTO \"post\" (\"id\", \"created_at\", \"last_modified_at\", \"caption\", \"privacy\", \"created_by_account_id\", \"last_modified_by_account_id\", \"active\") \nVALUES ";
        values.add(insertStatement);

        String value;
        long counting = 1L;
        for (int accountId = startAccountIndex; accountId <= endAccountIndex; accountId++) {

            int postCount = ThreadLocalRandom.current().nextInt(5, 20);
            for (int postIndex = 0; postIndex < postCount; postIndex++) {

                value = "([[id]], '[[created_at]]', '[[last_modified_at]]', '[[caption]]', [[privacy]], [[created_by_account_id]], NULL, true),\n";

                String createdAt = getRandomTimestamp();
                String last_modified_at = getRandomTimestamp();
                String caption = generateSentence(10);
                int privacy = RANDOM.nextInt(2);

                value = value.replace("[[id]]", String.valueOf(counting));
                value = value.replace("[[created_at]]", createdAt);
                value = value.replace("[[last_modified_at]]", last_modified_at);
                value = value.replace("[[caption]]", caption);
                value = value.replace("[[privacy]]", String.valueOf(privacy));
                value = value.replace("[[created_by_account_id]]", String.valueOf(accountId));
                values.add(value);

                counting++;
            }
        }
        return values;
    }
}
