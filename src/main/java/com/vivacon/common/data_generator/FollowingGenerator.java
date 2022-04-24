package com.vivacon.common.data_generator;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FollowingGenerator extends DataGenerator {

    @Override
    public List<String> generateSQLStatements(int startAccountIndex, int endAccountIndex) {
        List<String> statements = new LinkedList<>();

        String insertStatement = "INSERT INTO \"following\" (\"id\", \"from_account\", \"to_account\") \nVALUES ";
        statements.add(insertStatement);

        String statement;
        long counting = 1L;
        for (int currentAccountId = startAccountIndex; currentAccountId <= endAccountIndex; currentAccountId++) {

            int followingCount = ThreadLocalRandom.current().nextInt(0, 49);
            for (int followingIndex = 0; followingIndex < followingCount; followingIndex++) {
                int randomAccountId = ThreadLocalRandom.current().nextInt(1, endAccountIndex);
                statement = "([[id]], [[from_account]], [[to_account]]),\n";

                statement = statement.replace("[[id]]", String.valueOf(counting++));
                statement = statement.replace("[[from_account]]", String.valueOf(currentAccountId));
                statement = statement.replace("[[to_account]]", String.valueOf(randomAccountId));
                statements.add(statement);
            }
        }
        return statements;
    }
}
