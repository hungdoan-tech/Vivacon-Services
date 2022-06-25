package com.vivacon.dao.impl;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dao.PostStatisticDAO;
import com.vivacon.dao.StatisticDAO;
import com.vivacon.dto.response.PostInteraction;
import com.vivacon.dto.response.PostNewest;
import com.vivacon.dto.response.PostsQuantityInCertainTime;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostStatisticDAOImpl extends StatisticDAO implements PostStatisticDAO {

    private EntityManager entityManager;

    public PostStatisticDAOImpl(EntityManager entityManager) {
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

    @Override
    public List<PostInteraction> getTheTopPostInteraction(int limit, int pageIndex) {
        StoredProcedureQuery procedureQuery;
        procedureQuery = entityManager.createStoredProcedureQuery("getTopPostInteraction");
        return this.fetchingTheTopPostInteractionData(procedureQuery, limit, pageIndex);
    }

    @Override
    public List<PostNewest> getTopNewestPost(int limit) {
        StoredProcedureQuery procedureQuery;
        procedureQuery = entityManager.createStoredProcedureQuery("getTopNewestPost");
        return this.fetchingTheNewestPostData(procedureQuery, limit);
    }

    private List<PostInteraction> fetchingTheTopPostInteractionData(StoredProcedureQuery procedureQuery, int limit, int pageIndex) {
        List<PostInteraction> postsTopInteractionList = new ArrayList<>();

        procedureQuery.registerStoredProcedureParameter("limit_value", Integer.class, ParameterMode.IN);
        procedureQuery.setParameter("limit_value", limit);
        procedureQuery.registerStoredProcedureParameter("page_index", Integer.class, ParameterMode.IN);
        procedureQuery.setParameter("page_index", pageIndex);

        if (procedureQuery.execute()) {
            List<Object[]> resultList = procedureQuery.getResultList();

            for (int currentIndex = 0; currentIndex < resultList.size(); currentIndex++) {
                BigInteger postId = (BigInteger) resultList.get(currentIndex)[0];
                String caption = (String) resultList.get(currentIndex)[1];
                Timestamp createdAt = (Timestamp) resultList.get(currentIndex)[2];
                String userName = (String) resultList.get(currentIndex)[3];
                String fullName = (String) resultList.get(currentIndex)[4];
                String url = (String) resultList.get(currentIndex)[5];
                BigInteger totalComment = (BigInteger) resultList.get(currentIndex)[6];
                BigInteger totalLike = (BigInteger) resultList.get(currentIndex)[7];
                BigInteger totalInteraction = (BigInteger) resultList.get(currentIndex)[8];

                postsTopInteractionList.add(new PostInteraction(postId, caption, createdAt.toLocalDateTime(), userName, fullName, url,
                        totalComment, totalLike, totalInteraction));
            }

            return postsTopInteractionList;
        } else {
            return new ArrayList<>();
        }
    }

    private List<PostNewest> fetchingTheNewestPostData(StoredProcedureQuery procedureQuery, int limit) {
        List<PostNewest> postsNewestList = new ArrayList<>();

        procedureQuery.registerStoredProcedureParameter("limit_value", Integer.class, ParameterMode.IN);
        procedureQuery.setParameter("limit_value", limit);

        if (procedureQuery.execute()) {
            List<Object[]> resultList = procedureQuery.getResultList();

            for (int currentIndex = 0; currentIndex < resultList.size(); currentIndex++) {
                BigInteger postId = (BigInteger) resultList.get(currentIndex)[0];
                Boolean isActived = (Boolean) resultList.get(currentIndex)[1];
                Timestamp createdAt = (Timestamp) resultList.get(currentIndex)[2];
                Timestamp lastModifiedAt = (Timestamp) resultList.get(currentIndex)[3];
                String caption = (String) resultList.get(currentIndex)[4];
                Integer privacy = (Integer) resultList.get(currentIndex)[5];
                String userName = (String) resultList.get(currentIndex)[6];
                String fullName = (String) resultList.get(currentIndex)[7];
                String url = (String) resultList.get(currentIndex)[8];

                postsNewestList.add(new PostNewest(postId, isActived, createdAt.toLocalDateTime(), lastModifiedAt.toLocalDateTime(), caption, privacy,
                        userName, fullName, url));
            }

            return postsNewestList;
        } else {
            return new ArrayList<>();
        }
    }
}
