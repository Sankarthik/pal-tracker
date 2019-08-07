package io.pivotal.pal.tracker;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long, TimeEntry> timeEntries = new HashMap<>();

    private long currentId = 1L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        Long timeEntryId = currentId++;

        TimeEntry createdTimeEntry = new TimeEntry(
            timeEntryId,
            timeEntry.getProjectId(),
            timeEntry.getUserId(),
            timeEntry.getDate(),
            timeEntry.getHours()
        );

        timeEntries.put(timeEntryId, createdTimeEntry);
        return createdTimeEntry;
    }

    public TimeEntry update(long timeEntryId, TimeEntry timeEntry) {
        if(find(timeEntryId) == null) {
            return null;
        }

        TimeEntry updatedEntry = new TimeEntry(
            timeEntryId,
            timeEntry.getProjectId(),
            timeEntry.getUserId(),
            timeEntry.getDate(),
            timeEntry.getHours()
        );

        timeEntries.replace(timeEntryId, updatedEntry);
        return updatedEntry;
    }

    public void delete(long timeEntryId) {
        timeEntries.remove(timeEntryId);
    }

    public TimeEntry find(long timeEntryId) {
        return timeEntries.get(timeEntryId);
    }

    public List<TimeEntry> list() {
        return unmodifiableList(new ArrayList<>(timeEntries.values()));
    }

}
