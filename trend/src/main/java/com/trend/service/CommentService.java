package com.trend.service;

import com.trend.domain.Comment;

public interface CommentService {
    void saveComment(Comment comment, Integer accountId);
}
