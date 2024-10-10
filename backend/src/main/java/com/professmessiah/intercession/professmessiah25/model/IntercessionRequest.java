package com.professmessiah.intercession.professmessiah25.model;

import java.util.List;

public record IntercessionRequest(String salutation, String name, List<TrackerItem> trackerItems) {}

