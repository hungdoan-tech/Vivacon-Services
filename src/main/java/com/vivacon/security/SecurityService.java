package com.vivacon.security;

import com.vivacon.common.enum_type.RoleType;
import com.vivacon.entity.Account;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.CommentRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private AccountRepository accountRepository;
    private CommentRepository commentRepository;

    public SecurityService(AccountRepository accountRepository,
                           CommentRepository commentRepository) {
        this.accountRepository = accountRepository;
        this.commentRepository = commentRepository;
    }

    public boolean isAccessibleToCommentResource(Long commentId) {
        try {
            if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                return false;
            }
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String role = userDetails.getAuthorities().stream().findFirst().orElseThrow(NullPointerException::new).getAuthority();
            if (RoleType.ADMIN.toString().equals(role)) {
                return true;
            }
            Account account = accountRepository
                    .findByUsernameIgnoreCase(userDetails.getUsername())
                    .orElseThrow(RecordNotFoundException::new);
            Account author = commentRepository
                    .findByIdAndActive(commentId, true)
                    .orElseThrow(RecordNotFoundException::new)
                    .getCreatedBy();
            return (account != null) && (author != null) && (account.getId() == author.getId());
        } catch (Exception ex) {
            return false;
        }
    }
}