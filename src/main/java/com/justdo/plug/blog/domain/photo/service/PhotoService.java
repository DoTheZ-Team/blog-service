package com.justdo.plug.blog.domain.photo.service;

import com.justdo.plug.blog.domain.photo.Photo;
import com.justdo.plug.blog.domain.photo.repository.PhotoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;

    public void save(Photo photo){
        photoRepository.save(photo);
    }
}
