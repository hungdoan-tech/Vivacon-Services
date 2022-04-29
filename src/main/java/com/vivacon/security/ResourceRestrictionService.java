package com.vivacon.security;

import com.vivacon.common.enum_type.RoleType;
import com.vivacon.entity.Account;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.CommentRepository;
import com.vivacon.repository.PostRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class ResourceRestrictionService {
    private AccountRepository accountRepository;
    private CommentRepository commentRepository;

    private PostRepository postRepository;

    public ResourceRestrictionService(AccountRepository accountRepository,
                                      CommentRepository commentRepository,
                                      PostRepository postRepository) {
        this.accountRepository = accountRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public boolean isAccessibleToResource(Supplier<Account> getResourceAuthor) {
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
            Account author = getResourceAuthor.get();
            return (account != null) && (author != null) && (account.getId() == author.getId());
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isAccessibleToCommentResource(Long id) {
        return this.isAccessibleToResource(() -> commentRepository
                .findByIdAndActive(id, true)
                .orElseThrow(RecordNotFoundException::new)
                .getCreatedBy()
        );
    }

    public boolean isAccessibleToPostResource(Long id) {
        return this.isAccessibleToResource(() -> postRepository
                .findByIdAndActive(id, true)
                .orElseThrow(RecordNotFoundException::new)
                .getCreatedBy()
        );
    }
}