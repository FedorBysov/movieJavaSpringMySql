package com.example.movie.movie.service;

import java.io.File;

public interface EmailService {

    public void sendEmail(String toUser, String subject, String message);
    public void sendEmilWithFile(String toUser, String subject, String message, File file);

}
