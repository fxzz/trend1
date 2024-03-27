package com.trend.service;


import com.trend.util.MailSendUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Async
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final MailSendUtil mailSendUtil;


    @Override
    public void mailSend(String title, String content, String email) {

        String to = "fxzzy@naver.com"; // 내메일
        String subject = "급상승 서비스 문의";
        String text = "제목: " + title + "\n내용: " + content + "\n이메일: " + email;


//        try {
            mailSendUtil.sendMail(to, subject, text);
//        } catch (Exception e) {
//            throw new MailSendException("메일 전송 중 오류가 발생했습니다.");
//        }
    }
}
