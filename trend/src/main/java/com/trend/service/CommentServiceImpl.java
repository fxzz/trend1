package com.trend.service;

import com.trend.domain.Comment;
import com.trend.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;


    @Override
    public void saveComment(Comment comment, Integer accountId) {
        comment.setAccountId(accountId);
        commentMapper.insertComment(comment);
    }
}
