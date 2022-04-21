package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.response.DetailProfile;
import com.vivacon.dto.response.OutlinePost;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.service.ProfileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = Constants.API_V1)
public class ProfileController {

    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @ApiOperation(value = "Get list following of an account")
    @GetMapping("/profile/{username}")
    public DetailProfile getTheDetailProfile(
            @PathVariable(value = "username") String username,
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex) {
        return profileService.getProfileByUsername(username, order, sort, pageSize, pageIndex);
    }

    @ApiOperation(value = "Get list outlinePost of an account")
    @GetMapping("/profile/{username}/outline-post")
    public PageDTO<OutlinePost> getAll(
            @PathVariable(value = "username") String username,
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex) {
        return profileService.getOutlinePostByUsername(username, order, sort, pageSize, pageIndex);
    }

    @ApiOperation(value = "Get the list of the profile avatar of an specific account")
    @GetMapping("/profile/{id}/avatar")
    public PageDTO<AttachmentDTO> getListProfileAvatars(@PathVariable(value = "id") Long accountId,
                                                        @RequestParam(value = "_order", required = false) Optional<String> order,
                                                        @RequestParam(value = "_sort", required = false) Optional<String> sort,
                                                        @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
                                                        @RequestParam(value = "page", required = false) Optional<Integer> pageIndex) {
        return profileService.getProfileAvatarsByAccountId(accountId, order, sort, pageSize, pageIndex);
    }

    @ApiOperation(value = "Change the profile avatar")
    @PostMapping("/profile/avatar")
    public AttachmentDTO changeProfileAvatar(@Valid @RequestBody AttachmentDTO avatar) {
        return profileService.changeProfileAvatar(avatar);
    }
}
