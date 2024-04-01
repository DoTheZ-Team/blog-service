package com.justdo.plug.blog.domain.photo.repository;
import com.justdo.plug.blog.domain.photo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long>{

}
