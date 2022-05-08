package com.vivacon.service.impl;

import com.vivacon.common.enum_type.Privacy;
import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.request.PostRequest;
import com.vivacon.dto.response.DetailPost;
import com.vivacon.dto.response.NewsfeedPost;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.dto.sorting_filtering.PostFilter;
import com.vivacon.dto.sorting_filtering.PostSpecification;
import com.vivacon.dto.sorting_filtering.QueryCriteria;
import com.vivacon.entity.Account;
import com.vivacon.entity.Attachment;
import com.vivacon.entity.Comment;
import com.vivacon.entity.Post;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.mapper.PageDTOMapper;
import com.vivacon.mapper.PostMapper;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.AttachmentRepository;
import com.vivacon.repository.PostRepository;
import com.vivacon.service.PostService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostMapper postMapper;

    private AccountRepository accountRepository;

    private PostRepository postRepository;

    private AttachmentRepository attachmentRepository;

    public PostServiceImpl(PostMapper postMapper,
                           AccountRepository accountRepository,
                           PostRepository postRepository,
                           AttachmentRepository attachmentRepository) {
        this.postMapper = postMapper;
        this.accountRepository = accountRepository;
        this.postRepository = postRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    @Override
    public NewsfeedPost createPost(PostRequest postRequest) {
        Post post = postMapper.toPost(postRequest);
        post.setActive(true);
        Post savedPost = postRepository.save(post);

        List<Attachment> attachments = postRequest.getAttachments()
                .stream().map(attachment -> new Attachment(
                        attachment.getActualName(),
                        attachment.getUniqueName(),
                        attachment.getUrl(),
                        savedPost))
                .collect(Collectors.toList());
        attachmentRepository.saveAll(attachments);

        return postMapper.toNewsfeedPost(savedPost);
    }

    @Override
    public PageDTO<NewsfeedPost> getAll(PostFilter postFilter, Optional<String> keyword, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Post.class);
        Specification<Post> combinedSpecification = this.createTheCombiningPostSpecification(postFilter, keyword);
        Page<Post> pagePost = postRepository.findAll(combinedSpecification, pageable);
        return PageDTOMapper.toPageDTO(pagePost, post -> postMapper.toNewsfeedPost(post));
    }

    @Override
    public DetailPost getDetailPost(Long postId, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable commentPageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Comment.class);
        Post post = postRepository.findById(postId).orElse(null);
        return this.postMapper.toDetailPost(post, commentPageable);
    }

    private Specification<Post> createTheCombiningPostSpecification(PostFilter filter, Optional<String> keyword) {

        Specification<Post> combinedSpecification = Specification.where(null);
        if (!keyword.isEmpty()) {
            combinedSpecification = combinedSpecification.and(new PostSpecification(new QueryCriteria("content", keyword.get())));
        }
        combinedSpecification = combinedSpecification.and(this.getPrivacySpecifications(filter));
        if (filter.isOwn()) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Account account = accountRepository
                    .findByUsernameIgnoreCase(userDetails.getUsername())
                    .orElseThrow(RecordNotFoundException::new);
            combinedSpecification = combinedSpecification.and(new PostSpecification(new QueryCriteria("account", account.getId())));
        } else {
            combinedSpecification = combinedSpecification.and(this.getAuthorSpecifications(filter));
        }
        return combinedSpecification;
    }

    private Specification<Post> getPrivacySpecifications(PostFilter filter) {
        Specification<Post> typeSpecification = Specification.where(null);
        Optional<List<Privacy>> filterPrivacy = filter.getPrivacy();
        if (!filterPrivacy.isEmpty()) {
            for (Privacy privacy : filterPrivacy.get()) {
                typeSpecification = typeSpecification.or(new PostSpecification(new QueryCriteria("privacy", privacy)));
            }
        }
        return typeSpecification;
    }

    private Specification<Post> getAuthorSpecifications(PostFilter filter) {
        Specification<Post> projectNameSpecification = Specification.where(null);
        Optional<List<Long>> filterAuthor = filter.getAuthor();
        if (!filterAuthor.isEmpty()) {
            for (Long authorId : filterAuthor.get()) {
                projectNameSpecification = projectNameSpecification.or(new PostSpecification(new QueryCriteria("account", authorId)));
            }
        }
        return projectNameSpecification;
    }
}
