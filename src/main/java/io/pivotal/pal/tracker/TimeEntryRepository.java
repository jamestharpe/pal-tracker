package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TimeEntryRepository {
    TimeEntry create(TimeEntry timeEntry) throws SQLException;
    TimeEntry find(long timeEntryId) throws SQLException;
    List<TimeEntry> list() throws SQLException;
    TimeEntry update(long id, TimeEntry timeEntry) throws SQLException;
    void delete(long timeEntryId) throws SQLException;
}
