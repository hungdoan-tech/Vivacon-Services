package com.vivacon.controller;

import com.vivacon.common.FileUtils;
import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.ResponseDTO;
import com.vivacon.exception.UploadAttachmentException;
import com.vivacon.service.AWSS3Service;
import com.vivacon.common.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseDTO<AttachmentDTO> uploadImage(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            AttachmentDTO attachment = this.awsS3Service.uploadFile(file);
            return new ResponseDTO<>(HttpStatus.OK, Constants.UPLOAD_ATTACHMENT_SUCCESSFULLY, attachment);
        }
        throw new UploadAttachmentException(Constants.EMPTY_FILE_UPLOAD_MESSAGE);
    }

    @ApiOperation(value = "Upload attachments which relate to innovation to the cloud storage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.UPLOAD_ATTACHMENT_SUCCESSFULLY),
            @ApiResponse(code = 400, message = Constants.EMPTY_FILE_UPLOAD_MESSAGE)})
    @PostMapping(value = Constants.API_V1 + "/innovation" + "/attachments")
    public ResponseDTO<List<AttachmentDTO>> uploadImages(@RequestParam("files") MultipartFile[] files) {
        List<File> listImages = FileUtils.convertAndValidateListImages(files);
        List<AttachmentDTO> attachmentDTOList = this.awsS3Service.uploadFile(listImages);
        return new ResponseDTO<>(HttpStatus.OK, Constants.UPLOAD_ATTACHMENT_SUCCESSFULLY, attachmentDTOList);
    }
}
