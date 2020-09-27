package com.problemfighter.pfspring.webtestmodule.example.repository;

import com.problemfighter.pfspring.webtestmodule.example.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
