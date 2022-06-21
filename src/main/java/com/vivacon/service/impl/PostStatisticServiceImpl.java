package com.vivacon.service.impl;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dao.PostStatisticDAO;
import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.response.PostInteraction;
import com.vivacon.dto.response.PostNewest;
import com.vivacon.dto.response.PostsQuantityInCertainTime;
import com.vivacon.repository.AttachmentRepository;
import com.vivacon.service.PostStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostStatisticServiceImpl implements PostStatisticService {

    private PostStatisticDAO postStatisticDAO;
    private AttachmentRepository attachmentRepository;

    @Autowired
    public PostStatisticServiceImpl(
            PostStatisticDAO postStatisticDAO, AttachmentRepository attachmentRepository
    ) {
        this.postStatisticDAO = postStatisticDAO;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public List<PostsQuantityInCertainTime> getThePostQuantityStatisticInTimePeriods(TimePeriod timePeriodOption) {
        return this.postStatisticDAO.getThePostQuantityStatisticInTimePeriods(timePeriodOption);
    }

    @Override
    public List<PostInteraction> getTheTopPostInteraction(Integer limit) {
        List<PostInteraction> postInteractionList = new ArrayList<>();
        postInteractionList = this.postStatisticDAO.getTheTopPostInteraction(limit);
        for (PostInteraction postInteraction : postInteractionList) {
            List<AttachmentDTO> attachmentDTOS = attachmentRepository
                    .findByPostId(postInteraction.getPostId().longValue())
                    .stream().map(attachment -> new AttachmentDTO(attachment.getActualName(), attachment.getUniqueName(), attachment.getUrl()))
                    .collect(Collectors.toList());
            postInteraction.setLstAttachmentDTO(attachmentDTOS);
        }

        return postInteractionList;
    }

    @Override
    public List<PostNewest> getTopNewestPost(Integer limit) {
        return this.postStatisticDAO.getTopNewestPost(limit);
    }
}
