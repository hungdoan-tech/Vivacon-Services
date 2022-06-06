package com.vivacon.service;

import com.vivacon.dto.response.TopHashTagResponse;

import java.util.List;

public interface HashTagService {

    List<TopHashTagResponse> findTopHashTag();

}
