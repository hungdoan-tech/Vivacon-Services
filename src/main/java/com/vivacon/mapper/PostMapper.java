package com.vivacon.mapper;

import com.vivacon.common.AuditableHelper;
import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.request.PostRequest;
import com.vivacon.dto.response.OutlinePost;
import com.vivacon.dto.response.PostResponse;
import com.vivacon.entity.Attachment;
import com.vivacon.entity.Post;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.repository.AttachmentRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostMapper.class);

    private ModelMapper mapper;

    private AuditableHelper auditableHelper;

    private AttachmentRepository attachmentRepository;

    public PostMapper(ModelMapper mapper,
                      AuditableHelper auditableHelper,
                      AttachmentRepository attachmentRepository) {
        this.mapper = mapper;
        this.auditableHelper = auditableHelper;
        this.attachmentRepository = attachmentRepository;
    }

    public Post toPost(PostRequest postResponse) {
        Post post = this.mapper.map(postResponse, Post.class);
        return (Post) auditableHelper.updateAuditingCreatedFields(post, null);
    }

    public PostResponse toResponse(Object object) {
        try {
            Post post = (Post) object;
            PostResponse postResponse = mapper.map(post, PostResponse.class);
            List<AttachmentDTO> attachmentDTOS = attachmentRepository.findByPost_Id(post.getId())
                    .stream().map(attachment -> new AttachmentDTO(attachment.getActualName(), attachment.getUniqueName(), attachment.getUrl()))
                    .collect(Collectors.toList());
            postResponse.setAttachments(attachmentDTOS);
            return postResponse;
        } catch (ClassCastException ex) {
            LOGGER.info(ex.getMessage());
            return null;
        }
    }

    public OutlinePost toOutlinePost(Object object) {
        try {
            Post post = (Post) object;
            Attachment firstImage = attachmentRepository.findFirstByPost_IdOrderByTimestampAsc(post.getId()).orElseThrow(RecordNotFoundException::new);
            boolean isMultipleImages = attachmentRepository.getAttachmentCountByPostId(post.getId()) > 0;
            Long likeCount = 0L;
            Long commentCount = 0L;
            return new OutlinePost(post.getId(), firstImage.getUrl(), isMultipleImages, likeCount, commentCount);
        } catch (ClassCastException ex) {
            LOGGER.info(ex.getMessage());
            return null;
        }
    }
}
