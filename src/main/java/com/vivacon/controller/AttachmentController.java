package com.vivacon.controller;

import com.vivacon.common.FileUtils;
import com.vivacon.common.constant.Constants;
import com.vivacon.dto.AttachmentDTO;
import com.vivacon.exception.UploadAttachmentException;
import com.vivacon.service.AWSS3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Api(value = "Innovation Controller")
@RestController
public class AttachmentController {

    private AWSS3Service awsS3Service;

    @Autowired
    public AttachmentController(AWSS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    @ApiOperation(value = "Upload attachment which relate to innovation to the cloud storage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.UPLOAD_ATTACHMENT_SUCCESSFULLY),
            @ApiResponse(code = 400, message = Constants.EMPTY_FILE_UPLOAD_MESSAGE)})
    @PostMapping(value = Constants.API_V1 + "/innovation" + "/attachment")
    public AttachmentDTO uploadImage(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            return this.awsS3Service.uploadFile(file);
        }
        throw new UploadAttachmentException(Constants.EMPTY_FILE_UPLOAD_MESSAGE);
    }

    @ApiOperation(value = "Upload attachments which relate to innovation to the cloud storage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.UPLOAD_ATTACHMENT_SUCCESSFULLY),
            @ApiResponse(code = 400, message = Constants.EMPTY_FILE_UPLOAD_MESSAGE)})
    @PostMapping(value = Constants.API_V1 + "/innovation" + "/attachments")
    public List<AttachmentDTO> uploadImages(@RequestParam("files") MultipartFile[] files) {
        List<File> listImages = FileUtils.convertAndValidateListImages(files);
        return this.awsS3Service.uploadFile(listImages);
    }
}
