package com.vivacon.controller;

import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.response.DetailProfile;
import com.vivacon.dto.response.OutlinePost;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ProfileController {

    private AccountService accountService;

    public ProfileController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Get list following of an account")
    @GetMapping("/profile/{id}")
    public DetailProfile getTheDetailProfile(
            @PathVariable(value = "id") Long accountId,
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex) {
        return accountService.getProfileByAccountId(accountId, order, sort, pageSize, pageIndex);
    }

    @ApiOperation(value = "Get list following of an account")
    @GetMapping("/profile/{id}/outline-post")
    public PageDTO<OutlinePost> getAll(
            @PathVariable(value = "id") Long accountId,
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex) {
        return accountService.getOutlinePostByAccountId(accountId, order, sort, pageSize, pageIndex);
    }

    @ApiOperation(value = "Change the profile avatar")
    @PutMapping("/profile/avatar")
    public AttachmentDTO changeProfileAvatar(@Valid @RequestBody AttachmentDTO avatar) {
        return this.accountService.changeProfileAvatar(avatar);
    }
}
