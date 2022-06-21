package com.vivacon.mapper;

import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.response.AccountAdminResponse;
import com.vivacon.entity.Account;
import com.vivacon.repository.AttachmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountAdminMapper {

    private ModelMapper mapper;

    private AttachmentRepository attachmentRepository;

    public AccountAdminMapper(ModelMapper mapper, AttachmentRepository attachmentRepository) {
        this.mapper = mapper;
        this.attachmentRepository = attachmentRepository;
    }

    public AccountAdminResponse toUserAccountMostFollower(Account account) {
        AccountAdminResponse response = mapper.map(account, AccountAdminResponse.class);
        List<AttachmentDTO> attachmentDTOS = attachmentRepository
                .findByProfileId(account.getId().longValue())
                .stream().map(attachment -> new AttachmentDTO(attachment.getActualName(), attachment.getUniqueName(), attachment.getUrl()))
                .collect(Collectors.toList());
        response.setAttachments(attachmentDTOS);

        return response;
    }
}
