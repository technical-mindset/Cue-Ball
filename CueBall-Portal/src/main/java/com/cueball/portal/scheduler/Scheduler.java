package com.cueball.portal.scheduler;

import com.cueballdb.repository.TaskRepository;
import com.cueballdb.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class Scheduler {

    @Autowired
    private TaskRepository repository;

    private final SimpMessagingTemplate messagingTemplate;

    public Scheduler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 30 * 60 * 1000) // every 1 minutes
    public void sendAlerts() {
        System.out.println(" ---------------------- >>>> Starting The Scheduler <<<<<<<<<<<< ----------------------------");

//        // Convert LocalDateTime to java.util.Date for taskDate (which is of type Date)
//        LocalDateTime twoHoursAgoLDT = LocalDateTime.now().minusHours(2);
//        Date twoHoursAgo = Date.from(twoHoursAgoLDT.atZone(ZoneId.systemDefault()).toInstant());
//
//        // Keep LocalDateTime for lastAlertSent (which is of type LocalDateTime)
//        LocalDateTime alertCutoff = LocalDateTime.now().minusHours(2);
//
//        List<Task> tasksToAlert = repository.findUncompletedTasksOlderThan2Hours(twoHoursAgo, alertCutoff);

        // Convert LocalDateTime to java.util.Date for taskDate
        LocalDateTime twoHoursAgoLDT = LocalDateTime.now().minusHours(2);
        Date twoHoursAgo = Date.from(twoHoursAgoLDT.atZone(ZoneId.systemDefault()).toInstant());

// For current date boundary
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        Date startOfDayDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());

// Keep LocalDateTime for lastAlertSent
        LocalDateTime alertCutoff = LocalDateTime.now().minusHours(2);

        System.out.println(":::::::::::::::: twoHoursAgo :::::::::::::" +twoHoursAgo);
        System.out.println(":::::::::::::::: startOfDayDate :::::::::::::" +startOfDayDate);
        System.out.println(":::::::::::::::: alertCutoff :::::::::::::" +alertCutoff);

        List<Task> tasksToAlert = repository.findUncompletedTasksOlderThan2HoursForToday(
                twoHoursAgo,
                startOfDayDate,
                alertCutoff
        );

        System.out.println(" ---------------------- >>>> Retrieving The Data <<<<<<<<<<<< ----------------------------" + tasksToAlert);


//        String destination = "/topic/alerts/staff-" + 4;
//        messagingTemplate.convertAndSend(destination, "⚠ You have a pending task: " + "Test two");

        for (Task task : tasksToAlert) {
            System.out.println(" ---------------------- >>>> Setting Up For Sending Alert <<<<<<<<<<<< ----------------------------");
            String destination = "/topic/alerts/staff-" + task.getUserId();
            messagingTemplate.convertAndSend(destination, "⚠ You have a pending task: " + task.getDescription());

            System.out.println(" ---------------------- >>>> The Alert Has Been Sent <<<<<<<<<<<< ----------------------------");

            task.setLastAlertSent(LocalDateTime.now());
            repository.save(task);
        }
    }}
