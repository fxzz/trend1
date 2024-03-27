package com.trend.service;

import com.trend.controller.form.SignUpForm;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {

   void saveAccount(SignUpForm signUpForm);

   void updateProfileImage(MultipartFile croppedImage, Integer accountId);
}
