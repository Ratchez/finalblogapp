package com.codewithratchez.blog.services.impl;

import com.codewithratchez.blog.entities.Comment;
import com.codewithratchez.blog.entities.Post;
import com.codewithratchez.blog.exceptions.ResourceNotFoundException;
import com.codewithratchez.blog.payloads.CommentDto;
import com.codewithratchez.blog.repositories.CommentRepo;
import com.codewithratchez.blog.repositories.PostRepo;
import com.codewithratchez.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId){
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post"));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);

        Comment savedComment = commentRepo.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }
    @Override
    public void deleteComment(Integer commentId){
        Comment com = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment"));
        commentRepo.delete(com);
    }
}
