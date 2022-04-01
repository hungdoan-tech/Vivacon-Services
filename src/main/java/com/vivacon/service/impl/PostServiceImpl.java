package com.vivacon.service.impl;

import com.vivacon.dto.request.PostRequest;
import com.vivacon.dto.response.PostResponse;
import com.vivacon.entity.Account;
import com.vivacon.entity.Attachment;
import com.vivacon.entity.Post;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.mapper.PostMapper;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.AttachmentRepository;
import com.vivacon.repository.PostRepository;
import com.vivacon.security.UserDetailImpl;
import com.vivacon.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public PostResponse createPost(PostRequest postRequest) {
        UserDetails userDetail = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByUsernameIgnoreCase(userDetail.getUsername()).orElseThrow(() ->
                new RecordNotFoundException("Not found a valid account to perform anything"));

        Post savedPost = postRepository.save(postMapper.toPost(postRequest, account));
        List<Attachment> attachments = postRequest.getAttachments()
                .stream().map(attachment -> new Attachment(
                        attachment.getActualName(),
                        attachment.getUniqueName(),
                        attachment.getUrl(),
                        savedPost))
                .collect(Collectors.toList());
        attachmentRepository.saveAll(attachments);
        return postMapper.toResponse(savedPost);
    }
}
