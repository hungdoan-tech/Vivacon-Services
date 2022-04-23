package com.vivacon.service;

import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.response.DetailProfile;
import com.vivacon.dto.response.OutlinePost;
import com.vivacon.dto.sorting_filtering.PageDTO;

import java.util.Optional;

public interface ProfileService {
    
    DetailProfile getProfileByUsername(String username, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);

    AttachmentDTO changeProfileAvatar(AttachmentDTO avatarDto);

    PageDTO<AttachmentDTO> getProfileAvatarsByAccountId(Long accountId, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);

    PageDTO<OutlinePost> getOutlinePostByUsername(String username, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);
}