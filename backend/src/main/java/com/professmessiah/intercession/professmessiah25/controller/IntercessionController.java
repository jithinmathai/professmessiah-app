package com.professmessiah.intercession.professmessiah25.controller;


import com.professmessiah.intercession.professmessiah25.model.IntercessionRequest;
import com.professmessiah.intercession.professmessiah25.model.TrackerItem;
import com.professmessiah.intercession.professmessiah25.service.IntercessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intercessions")
public class IntercessionController {

    @Autowired
    private IntercessionService intercessionService;

    // GET endpoint to return current counts of tracker items
    @GetMapping("/tracker-items")
    public List<TrackerItem> getTrackerItems() {
        return intercessionService.getTrackerItems();
    }

    // POST endpoint to update tracker items
    @PostMapping("/tracker-items")
    public String updateTrackerItems(@RequestBody IntercessionRequest request) {
        intercessionService.updateTrackerItems(request);
        return "Tracker items updated successfully";
    }
}
