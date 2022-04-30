package com.vivacon.common.data_generator;

import com.vivacon.common.constant.MockData;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static com.vivacon.common.constant.MockData.FIRST_NAME;

public class AccountGenerator extends DataGenerator {

    @Override
    public List<String> generateSQLStatements(int startAccountIndex, int endAccountIndex) {
        List<String> statements = new LinkedList<>();
        int firstNameLength = FIRST_NAME.size() - 1;
        int lastNameLength = MockData.LAST_NAME.size() - 1;
        String insertStatement = "INSERT INTO \"account\" (\"id\", \"full_name\", \"password\", \"refresh_token\", \"token_expired_date\", \"username\", \"role_id\", \"created_at\", \"created_by_account_id\", \"last_modified_at\", \"last_modified_by_account_id\", \"bio\", \"email\", \"verification_token\", verification_expired_date, \"active\") \nVALUES ";
        statements.add(insertStatement);

        String statement;
        for (int accountId = startAccountIndex; accountId <= endAccountIndex; accountId++) {
            statement = "([[id]], '[[full_name]]', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi', NULL, NULL, '[[username]]', 2, '[[created_at]]', NULL, NULL, NULL, '[[bio]]', '[[email]]', NULL, NULL, true),\n";

            String fullName = FIRST_NAME.get(RANDOM.nextInt(firstNameLength)) + " " + MockData.LAST_NAME.get(RANDOM.nextInt(lastNameLength));
            String username = fullName.replace(" ", "") + UUID.randomUUID();
            String bio = generateSentence(10);
            String email = username + "@gmail.com";
            String createdAt = getRandomTimestamp();

            statement = statement.replace("[[id]]", String.valueOf(accountId));
            statement = statement.replace("[[full_name]]", fullName);
            statement = statement.replace("[[username]]", username);
            statement = statement.replace("[[created_at]]", createdAt);
            statement = statement.replace("[[bio]]", bio);
            statement = statement.replace("[[email]]", email);
            statements.add(statement);
        }
        return statements;
    }
}
