import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleManager {
    void addEvent(String title, String description, LocalDateTime start, LocalDateTime end);
    void deleteEvent(ScheduleEvent value);
    List<ScheduleEvent> getEventsByDate(LocalDateTime date);
}
