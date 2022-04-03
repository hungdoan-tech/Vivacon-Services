package com.vivacon.mapper;

import com.vivacon.common.AuditableHelper;
import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.request.PostRequest;
import com.vivacon.dto.response.PostResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Post;
import com.vivacon.repository.AttachmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public Post toPost(PostRequest postResponse) {
        Post post = this.mapper.map(postResponse, Post.class);
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

    public List<PostResponse> toDTOs(List<Post> oldContent) {
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post innovation : oldContent) {
            postResponses.add(this.toResponse(innovation));
        }
        return postResponses;
    }

    public PageDTO<PostResponse> toPageDTO(Page<Post> pageEntity) {

        List<Post> oldContent = pageEntity.getContent();
        List<PostResponse> newContent = this.toDTOs(oldContent);

        PageDTO<PostResponse> pageResponse = new PageDTO<>();
        pageResponse.setContent(newContent);
        pageResponse.setSize(pageEntity.getSize());
        pageResponse.setTotalPages(pageEntity.getTotalPages());
        pageResponse.setCurrentPage(pageEntity.getNumber());
        pageResponse.setTotalElements(pageEntity.getTotalElements());
        pageResponse.setNumberOfElement(pageEntity.getNumberOfElements());
        pageResponse.setFirst(pageResponse.getCurrentPage() == 0);
        pageResponse.setLast(pageResponse.getCurrentPage() >= pageEntity.getTotalPages() - 1);
        pageResponse.setEmpty(pageEntity.getTotalElements() == 0);
        return pageResponse;
    }
}
