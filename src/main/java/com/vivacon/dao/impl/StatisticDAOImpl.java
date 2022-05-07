package com.vivacon.dao.impl;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dao.StatisticDAO;
import com.vivacon.dto.response.PostsQuantityInCertainTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class StatisticDAOImpl implements StatisticDAO {
    private EntityManager entityManager;

    @Autowired
    public StatisticDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * This method is used to set the right store procedure calling name to fetch the quantity post in each
     * time period(recent months or quarter or years)
     *
     * @param timePeriodOption
     * @return
     */
    @Override
    public List<PostsQuantityInCertainTime> getThePostQuantityStatisticInTimePeriods(TimePeriod timePeriodOption) {
        StoredProcedureQuery procedureQuery;
        switch (timePeriodOption) {
            case MONTH: {
                procedureQuery = entityManager.createStoredProcedureQuery("postQuantityStatisticInRecentMonths");
                break;
            }
            case QUARTER: {
                procedureQuery = entityManager.createStoredProcedureQuery("postQuantityStatisticInQuarters");
                break;
            }
            case YEAR: {
                procedureQuery = entityManager.createStoredProcedureQuery("postQuantityStatisticInYears");
                break;
            }
            default: {
                return new ArrayList<>();
            }
        }
        return this.fetchingTheQuantityPostStatisticData(procedureQuery);
    }

    /**
     * This method is used to fetch the quantity post in each
     * time period (recent months or quarter or years)
     * to see more about the output of database query, let's go to the script.sql descriptions
     *
     * @param procedureQuery
     * @return List<PostsQuantityInCertainTime>
     */
    private List<PostsQuantityInCertainTime> fetchingTheQuantityPostStatisticData(StoredProcedureQuery procedureQuery) {
        List<PostsQuantityInCertainTime> postsQuantityList = new ArrayList<>();
        if (procedureQuery.execute()) {

            List<Object[]> resultList = procedureQuery.getResultList();
            for (int currentIndex = 0; currentIndex < resultList.size(); currentIndex++) {
                Integer time = (Integer) resultList.get(currentIndex)[0];
                Integer year = (Integer) resultList.get(currentIndex)[1];
                BigInteger quantity = (BigInteger) resultList.get(currentIndex)[2];
                postsQuantityList.add(new PostsQuantityInCertainTime(time, year, quantity));
            }
            return postsQuantityList;

        } else {
            return new ArrayList<>();
        }
    }
}
