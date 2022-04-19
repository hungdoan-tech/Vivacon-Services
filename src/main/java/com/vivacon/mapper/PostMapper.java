package com.vivacon.mapper;

import com.vivacon.common.utility.AuditableHelper;
import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.request.PostRequest;
import com.vivacon.dto.response.CommentResponse;
import com.vivacon.dto.response.DetailPost;
import com.vivacon.dto.response.NewsfeedPost;
import com.vivacon.dto.response.OutlinePost;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;
import com.vivacon.entity.Attachment;
import com.vivacon.entity.AuditableEntity;
import com.vivacon.entity.Comment;
import com.vivacon.entity.Like;
import com.vivacon.entity.Post;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.AttachmentRepository;
import com.vivacon.repository.CommentRepository;
import com.vivacon.repository.FollowingRepository;
import com.vivacon.repository.LikeRepository;
import com.vivacon.security.UserDetailImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostMapper.class);

    private ModelMapper mapper;

    private AuditableHelper auditableHelper;

    private AttachmentRepository attachmentRepository;

    private CommentRepository commentRepository;

    private LikeRepository likeRepository;

    private FollowingRepository followingRepository;

    private AccountRepository accountRepository;

    public PostMapper(ModelMapper mapper,
                      AuditableHelper auditableHelper,
                      AttachmentRepository attachmentRepository,
                      CommentRepository commentRepository,
                      LikeRepository likeRepository,
                      FollowingRepository followingRepository,
                      AccountRepository accountRepository) {
        this.mapper = mapper;
        this.auditableHelper = auditableHelper;
        this.attachmentRepository = attachmentRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.followingRepository = followingRepository;
        this.accountRepository = accountRepository;
    }

    public Post toPost(PostRequest postResponse) {
        Post post = this.mapper.map(postResponse, Post.class);
        AuditableEntity auditableEntity = auditableHelper.updateAuditingCreatedFields(post, null);
        auditableEntity.setLastModifiedAt(LocalDateTime.now());
        return (Post) auditableEntity;
    }

    public NewsfeedPost toNewsfeedPost(Object object) {
        try {
            Post post = (Post) object;
            NewsfeedPost newsfeedPost = mapper.map(post, NewsfeedPost.class);
            List<AttachmentDTO> attachmentDTOS = attachmentRepository
                    .findByPost_Id(post.getId())
                    .stream().map(attachment -> new AttachmentDTO(attachment.getActualName(), attachment.getUniqueName(), attachment.getUrl()))
                    .collect(Collectors.toList());
            newsfeedPost.setAttachments(attachmentDTOS);
            newsfeedPost.setLikeCount(0L);
            newsfeedPost.setCommentCount(0L);
            auditableHelper.setupDisplayAuditableFields(post, newsfeedPost);
            return newsfeedPost;
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

    public DetailPost toDetailPost(Object object, Pageable pageable) {
        try {
            Post post = (Post) object;
            DetailPost detailPost = mapper.map(post, DetailPost.class);
            auditableHelper.setupDisplayAuditableFields(post, detailPost);

            List<AttachmentDTO> attachmentDTOS = attachmentRepository
                    .findByPost_Id(post.getId())
                    .stream().map(attachment -> new AttachmentDTO(attachment.getActualName(), attachment.getUniqueName(), attachment.getUrl()))
                    .collect(Collectors.toList());
            detailPost.setAttachments(attachmentDTOS);

            Page<Comment> allFirstLevelComments = commentRepository.findAllFirstLevelComments(post.getId(), pageable);
            PageDTO<CommentResponse> commentResponsePageDTO = PageDTOMapper.toPageDTO(allFirstLevelComments, CommentResponse.class, entity -> this.toResponse(entity));

            Long commentCount = commentRepository.getCountingCommentsByPost(post.getId());
            detailPost.setCommentCount(commentCount);
            detailPost.setComments(commentResponsePageDTO);

            Long likeCount = likeRepository.getCountingLike(post.getId());
            UserDetailImpl principal = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Account currentAccount = accountRepository.findByUsernameIgnoreCase(principal.getUsername())
                    .orElseThrow(RecordNotFoundException::new);
            Optional<Like> like = likeRepository.findByIdComposition(currentAccount.getId(), post.getId());
            detailPost.setLikeCount(likeCount);
            detailPost.setLiked(like.isPresent());

            return detailPost;
        } catch (ClassCastException ex) {
            LOGGER.info(ex.getMessage());
            return null;
        }
    }

    public CommentResponse toResponse(Object object) {
        try {
            Comment comment = (Comment) object;
            CommentResponse postResponse = mapper.map(comment, CommentResponse.class);
            Long postId = Long.valueOf(0);
            if (comment != null && comment.getPost() != null) {
                postId = comment.getPost().getId();
            } else {
                postId = null;
            }
            long totalCountComment = commentRepository.getCountingChildComments(comment.getId(), postId);
            postResponse.setTotalChildComments(totalCountComment);
            return postResponse;
        } catch (ClassCastException ex) {
            LOGGER.info(ex.getMessage());
            return null;
        }
    }

}















