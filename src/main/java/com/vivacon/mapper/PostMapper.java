package com.vivacon.mapper;

import com.vivacon.common.AuditableHelper;
import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.request.PostRequest;
import com.vivacon.dto.response.PostResponse;
import com.vivacon.entity.Account;
import com.vivacon.entity.Post;
import com.vivacon.repository.AttachmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

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

    public Post toPost(PostRequest postResponse, Account account) {
        Post post = this.mapper.map(postResponse, Post.class);
        post.setAuthor(account);
        return (Post) auditableHelper.updateAuditingCreatedFields(post, null);
    }

    public PostResponse toResponse(Post post) {

        PostResponse postResponse = mapper.map(post, PostResponse.class);
        List<AttachmentDTO> attachmentDTOS = attachmentRepository.findAllByPostId(post.getId())
                .stream().map(attachment -> new AttachmentDTO(attachment.getActualName(), attachment.getUniqueName(), attachment.getUrl()))
                .collect(Collectors.toList());

        postResponse.setAttachments(attachmentDTOS);
        return postResponse;
    }
}
