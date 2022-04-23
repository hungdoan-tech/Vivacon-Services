package com.vivacon.common.data_generator;

import com.vivacon.common.constant.MockData;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class AccountGenerator extends DataGenerator {

    @Override
    public List<String> generateSQLStatements(int startIndex, int endIndex) {
        List<String> values = new LinkedList<>();
        int firstNameLength = MockData.FIRST_NAME.length - 1;
        int lastNameLength = MockData.LAST_NAME.length - 1;
        String insertStatement = "INSERT INTO \"account\" (\"id\", \"full_name\", \"password\", \"refresh_token\", \"token_expired_date\", \"username\", \"role_id\", \"created_at\", \"created_by_account_id\", \"last_modified_at\", \"last_modified_by_account_id\", \"bio\", \"email\", \"verification_token\", verification_expired_date, \"active\") \nVALUES ";
        values.add(insertStatement);

        String value;
        for (int i = startIndex; i <= endIndex; i++) {
            value = "([[id]], '[[full_name]]', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi', NULL, NULL, '[[username]]', 2, '[[created_at]]', NULL, NULL, NULL, '[[bio]]', '[[email]]', NULL, NULL, true),\n";

            String fullName = MockData.FIRST_NAME[RANDOM.nextInt(firstNameLength)] + " " + MockData.LAST_NAME[RANDOM.nextInt(lastNameLength)];
            String username = fullName.replace(" ", "") + UUID.randomUUID();
            String bio = generateSentence(10);
            String email = username + "@gmail.com";
            String createdAt = getRandomTimestamp();

            value = value.replace("[[id]]", String.valueOf(i));
            value = value.replace("[[full_name]]", fullName);
            value = value.replace("[[username]]", username);
            value = value.replace("[[created_at]]", createdAt);
            value = value.replace("[[bio]]", bio);
            value = value.replace("[[email]]", email);
            values.add(value);
        }
        return values;
    }
}
