package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Demonstrates making "version 2" of a set of API end-points (no actual differences from TimeEntryController)
 */
@RestController
public class TimeEntryControllerV2 {
    private final TimeEntryRepository timeEntryRepository;

    public TimeEntryControllerV2(TimeEntryRepository timeEntryRepository) {

        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/v2/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) throws SQLException {
        TimeEntry created = this.timeEntryRepository.create(timeEntryToCreate);
        ResponseEntity<TimeEntry> result = new ResponseEntity<TimeEntry>(created, HttpStatus.CREATED);
        return result;
    }

    @GetMapping("/v2/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) throws SQLException {
        TimeEntry entry = this.timeEntryRepository.find(timeEntryId);
        if(entry != null){
            return new ResponseEntity(entry, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/v2/time-entries")
    public ResponseEntity<List<TimeEntry>> list() throws SQLException {
        List<TimeEntry> list = this.timeEntryRepository.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/v2/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntry) throws SQLException {
        TimeEntry update = this.timeEntryRepository.update(timeEntryId, timeEntry);
        if(update != null) {
            return new ResponseEntity<TimeEntry>(update, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/v2/time-entries/{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) throws SQLException {
        this.timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
