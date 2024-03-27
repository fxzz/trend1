package com.trend.service;

import com.trend.controller.form.PostingForm;
import com.trend.domain.*;

import java.util.List;

public interface PostingService {


    void savePosting(PostingForm postingForm, Integer accountId);


    CursorResponse<Posting> getCursorPage(CursorRequest cursorRequest);



    PageResponse<Account> getUserList(PageRequest pageRequest);

    void saveLike(Integer accountId, int postingId);


    List<Posting> selectAll(Integer accountId, PageRequest pageRequest);

    PageResponse pageHandler(Integer accountId, PageRequest pageRequest);
}
