package com.trend.controller;

import com.trend.controller.form.PostingForm;
import com.trend.domain.*;
import com.trend.security.auth.CurrentAccount;
import com.trend.service.PostingService;
import com.trend.util.CustomFileUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;
    private final CustomFileUtil fileUtil;


    @GetMapping("/posting")
    public String posting(Model model) {
        return "posting";
    }


        @GetMapping("/posting-list")
        @ResponseBody
        public CursorResponse<Posting> posting(@RequestParam(required = false) Integer postingId, int size) {
            return postingService.getCursorPage(new CursorRequest(postingId, size));
    }


    @PostMapping("/posting")
    public String postingSubmit(@Valid PostingForm postingForm, @CurrentAccount Account account, RedirectAttributes attributes) {

        postingService.savePosting(postingForm, account.getAccountId());

        attributes.addFlashAttribute("message", "성공적으로 전송했습니다.");

        return "redirect:/posting";
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("fileName") String fileName) {
        return fileUtil.getFile(fileName);
    }



    @GetMapping("/posting-user")
    @ResponseBody
    public PageResponse<Account> userList(PageRequest pageRequest) {
        return postingService.getUserList(pageRequest);
    }


    @PostMapping("/posting-like")
    @ResponseBody
    public  Map<String, String> likeSubmit(@RequestBody String request, @CurrentAccount Account account) {
        // request 에서 "postingId=72" 이렇게 들어와서 postingId=를 교체
        String postingIdString = request.replace("postingId=", "");
        int postingId = Integer.parseInt(postingIdString);


        postingService.saveLike(account.getAccountId(), postingId);

        return Map.of("RESULT", "SUCCESS");
    }


    @GetMapping("/my-posting-list")
    public String myPostingList(PageRequest pageRequest ,@CurrentAccount Account account, Model model) {
        List<Posting> myPosting = postingService.selectAll(account.getAccountId(), pageRequest);
        model.addAttribute("list", myPosting);

        PageResponse pageResponse = postingService.pageHandler(account.getAccountId(), pageRequest);
        model.addAttribute("pr", pageResponse);

        return "myPostingList";
    }


}
