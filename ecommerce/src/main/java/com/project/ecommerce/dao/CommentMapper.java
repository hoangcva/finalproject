package com.project.ecommerce.dao;

import com.project.ecommerce.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {
    boolean createComment(CommentDto commentDto);
    boolean saveComment(CommentDto commentDto);
    List<CommentDto> getCommentByProduct(@Param("productId") Long productId, @Param("vendorId") Long vendorId);
    boolean deleteComment(@Param("id") Long commentId);
}
