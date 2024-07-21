package com.facelink.service;

import com.facelink.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
    @Autowired private PostRepository postRepository;

    public Page<?> getAllVideos(){
        Pageable pageable = PageRequest.of(0, 7);
        return this.postRepository.getVideoLinksPublic(pageable);
    }
}
