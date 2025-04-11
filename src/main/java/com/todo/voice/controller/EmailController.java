package com.todo.voice.controller;

import com.todo.voice.model.EmailRequest;
import com.todo.voice.service.impl.EmailServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/email")
@CrossOrigin(origins = "http://localhost:5500")
public class EmailController {
    @Autowired
    private final EmailServiceImpl emailService;

    @PostMapping("send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest){
        try{
            if(emailRequest.getTo().isEmpty() || emailRequest.getMessage().isEmpty() || emailRequest.getSubject().isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            emailService.sendTaskNotification(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getMessage() );
            return ResponseEntity.ok().body("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send email "+e.getMessage());
        }
    }
}
