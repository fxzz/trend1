package com.trend.service;

import com.trend.controller.form.SignUpForm;
import com.trend.domain.Account;
import com.trend.mapper.AccountMapper;
import com.trend.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomFileUtil fileUtil;

    @Override
    public void saveAccount(SignUpForm signUpForm) {

        Account account = signUpForm.toAccount();
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountMapper.insertAccount(account);
    }


    @Override
    public void updateProfileImage(MultipartFile croppedImage, Integer accountId) {

        if (croppedImage.isEmpty()) {
            return;
        }

        String uploadFileName = fileUtil.saveFile(croppedImage);
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", accountId);
        map.put("profileImage", uploadFileName);
        try {
            accountMapper.updateProfileImage(map);
        } catch (Exception e) {
            // 데이터베이스 업데이트 중 예외 발생 시 파일 삭제
            if (uploadFileName != null) {
                // 파일 삭제 로직 추가
                // fileUtil.deleteFile(uploadFileName);
            }
        }
    }
}
