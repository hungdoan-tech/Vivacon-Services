package com.vivacon.dao.impl;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dao.StatisticDAO;
import com.vivacon.dao.UserStatisticDAO;
import com.vivacon.dto.response.PostsQuantityInCertainTime;
import com.vivacon.dto.response.UserAccountMostFollower;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserStatisticDAOImpl extends StatisticDAO implements UserStatisticDAO {

    private EntityManager entityManager;

    public UserStatisticDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<UserAccountMostFollower> getTheTopAccountMostFollowerStatistic(int limit) {
        StoredProcedureQuery procedureQuery;
        procedureQuery = entityManager.createStoredProcedureQuery("getTopAccountMostFollowers");
        return this.fetchingTheTopAccountMostFollowerData(procedureQuery, limit);
    }

    private List<UserAccountMostFollower> fetchingTheTopAccountMostFollowerData(StoredProcedureQuery procedureQuery, int limit) {
        List<UserAccountMostFollower> userAccountMostFollowersList = new ArrayList<>();

        procedureQuery.registerStoredProcedureParameter("limit_value", Integer.class, ParameterMode.IN);
        procedureQuery.setParameter("limit_value", limit);

        if (procedureQuery.execute()) {
            List<Object[]> resultList = procedureQuery.getResultList();

            for (int currentIndex = 0; currentIndex < resultList.size(); currentIndex++) {
                BigInteger accountId = (BigInteger) resultList.get(currentIndex)[0];
                String userName = (String) resultList.get(currentIndex)[1];
                BigInteger accountQuantity = (BigInteger) resultList.get(currentIndex)[2];

                userAccountMostFollowersList.add(new UserAccountMostFollower(accountId, userName, accountQuantity));
            }

            return userAccountMostFollowersList;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<PostsQuantityInCertainTime> getTheUserQuantityStatisticInTimePeriods(TimePeriod timePeriodOption) {
        StoredProcedureQuery procedureQuery;
        switch (timePeriodOption) {
            case MONTH: {
                procedureQuery = entityManager.createStoredProcedureQuery("userQuantityStatisticInRecentMonths");
                break;
            }
            case QUARTER: {
                procedureQuery = entityManager.createStoredProcedureQuery("userQuantityStatisticInQuarters");
                break;
            }
            case YEAR: {
                procedureQuery = entityManager.createStoredProcedureQuery("userQuantityStatisticInYears");
                break;
            }
            default: {
                return new ArrayList<>();
            }
        }
        return this.fetchingTheQuantityPostStatisticData(procedureQuery);
    }
}
