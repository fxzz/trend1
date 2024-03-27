package com.trend.controller.form;

import com.trend.domain.Account;
import com.trend.domain.AccountRole;
import com.trend.domain.Posting;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostingForm {

    @NotBlank
    private String content;


    private List<MultipartFile> files;




    public Posting toPosting(Integer accountId) {
        return Posting.builder()
                .content(this.getContent())
                .accountId(accountId)
                .build();

    }
}
