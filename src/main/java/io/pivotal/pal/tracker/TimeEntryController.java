package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates REST-based CRUD ops with injectable dependencies
 */
@RestController
public class TimeEntryController {
    private final TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(
            TimeEntryRepository timeEntryRepository,
            MeterRegistry meterRegistry) {

        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    /**
     * Get object from body using @RequestBody
     */
    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) throws SQLException {
        TimeEntry created = this.timeEntryRepository.create(timeEntryToCreate);
        ResponseEntity<TimeEntry> result = new ResponseEntity<TimeEntry>(created, HttpStatus.CREATED);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return result;
    }

    /**
     * Get scalar from url path using @PathVariable
     */
    @GetMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) throws SQLException {
        TimeEntry entry = this.timeEntryRepository.find(timeEntryId);
        if(entry != null){
            actionCounter.increment();
            return new ResponseEntity(entry, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() throws SQLException {
        actionCounter.increment();
        return new ResponseEntity<>(this.timeEntryRepository.list(), HttpStatus.OK);
    }

    @PutMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntry) throws SQLException {
        TimeEntry update = this.timeEntryRepository.update(timeEntryId, timeEntry);
        if(update != null) {
            actionCounter.increment();
            return new ResponseEntity<TimeEntry>(update, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/time-entries/{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) throws SQLException {
        this.timeEntryRepository.delete(timeEntryId);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
