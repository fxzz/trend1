package com.trend.service;

import com.trend.controller.form.PostingForm;
import com.trend.domain.*;
import com.trend.mapper.AccountMapper;
import com.trend.mapper.ImageMapper;
import com.trend.mapper.PostingMapper;
import com.trend.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PostingServiceImpl implements PostingService {

    private final PostingMapper postingMapper;
    private final CustomFileUtil fileUtil;
    private final ImageMapper imageMapper;
    private final AccountMapper accountMapper;

    @Transactional
    @Override
    public void savePosting(PostingForm postingForm, Integer accountId) {

        Posting posting = postingForm.toPosting(accountId);
        postingMapper.insertPosting(posting);

        List<MultipartFile> files = postingForm.getFiles();
        List<String> uploadFileNames = fileUtil.saveFiles(files);

        if (uploadFileNames.size() != 0) {
            savePostingImages(posting, uploadFileNames);

        }

    }





    @Override
    public CursorResponse<Posting> getCursorPage(CursorRequest cursorRequest) {

        List<Posting> postings;

        // 첫 번째 페이지를 가져오거나 다음 페이지를 가져옵니다.
        if (!cursorRequest.hasPostingId()) {
            // 첫 번째 페이지를 가져오는 쿼리 실행
            postings = postingMapper.selectFirstPage(cursorRequest);
        } else {
            // 다음 페이지를 가져오는 쿼리 실행
            postings = postingMapper.selectNextPage(cursorRequest);
        }

        Long nextPostingId = postings.stream()
                .mapToLong(Posting::getPostingId)
                .min()
                .orElse(CursorRequest.NONE_postingId);

        // nextPostingId가 null이 아니라면 Integer로 변환
        Integer nextPostingIdInteger = nextPostingId != null ? nextPostingId.intValue() : null;

        return new CursorResponse<>(cursorRequest.next(nextPostingIdInteger), postings);
    }





    @Override
    public PageResponse<Account> getUserList(PageRequest pageRequest) {

        List<Account> accountList = accountMapper.selectAllAccount(pageRequest);

        int totalCount = accountMapper.accountCount();

        return new PageResponse<Account>(accountList, pageRequest, totalCount);
    }

    @Override
    public void saveLike(Integer accountId, int postingId) {
        Map<String, Object> map = new HashMap();
        map.put("accountId", accountId);
        map.put("postingId", postingId);
        postingMapper.insertLike(map);
    }

    @Override
    public List<Posting> selectAll(Integer accountId, PageRequest pageRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", accountId);
        map.put("offset", pageRequest.getOffset());
        map.put("keyword", pageRequest.getKeyword());
        map.put("pageSize", pageRequest.getPageSize());

        return postingMapper.selectAllMyPosting(map);
    }

    @Override
    public PageResponse pageHandler(Integer accountId, PageRequest pageRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", pageRequest.getKeyword());
        map.put("accountId", accountId);

        Integer totalCount = postingMapper.searchResultCnt(map);
        return new PageResponse(null, pageRequest, totalCount);
    }


    private void savePostingImages(Posting posting, List<String> fileNames) {
        for (int i = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            Image image = new Image();
            image.setFileName(fileName);
            image.setOrd(i);
            image.setPostingId(posting.getPostingId());
            try {
            imageMapper.insertImage(image);
            } catch (Exception e) {
                // 데이터베이스에 이미지 정보 삽입 중 예외 발생 시 이미지 파일 삭제
                if (fileName != null) {
                    // 파일 삭제
                }
            }
        }
    }
}
