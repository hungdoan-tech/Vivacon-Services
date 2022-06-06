package com.vivacon.service.impl;

import com.vivacon.dto.response.TopHashTagResponse;
import com.vivacon.repository.HashTagRelPostRepository;
import com.vivacon.service.HashTagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashTagServiceImpl implements HashTagService {

    private HashTagRelPostRepository hashTagRelPostRepository;

    public HashTagServiceImpl(HashTagRelPostRepository hashTagRelPostRepository) {
        this.hashTagRelPostRepository = hashTagRelPostRepository;
    }

    @Override
    public List<TopHashTagResponse> findTopHashTag() {
        return hashTagRelPostRepository.findTopHashTag();
    }
}
