package com.todo.voice.service.impl;

import com.todo.voice.model.Task;
import com.todo.voice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl {
    @Autowired
    EmailServiceImpl emailService;
    public void sendTaskNotification(Task task, User user, boolean isUpdated){
        if(user!=null){
            String action=isUpdated?"updated":"created";
            String emailMessage=String.format("Task %s: %s\n Urgency %s\n Due: %s",
                    action,task.getTask(),task.getUrgency(),task.getDateTime());
            String subject="Task "+action.substring(0,2).toUpperCase()+action.substring(2).toUpperCase();
            emailService.sendTaskNotification(user.getEmail(), subject,emailMessage);
        }
    }

}
