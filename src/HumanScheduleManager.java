import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class HumanScheduleManager implements ScheduleManager {

    // 날짜별로 일정들을 저장할 자료구조
    private final Map<LocalDate, List<ScheduleEvent>> scheduleMap;

    // 생성자
    public HumanScheduleManager() {
        scheduleMap = new HashMap<>();
    }

    // 일정 추가 (인터페이스에 명시된 메서드)
    @Override
    public void addEvent(String title, String description, LocalDateTime start, LocalDateTime end) {
        ScheduleEvent event = new ScheduleEvent(title, description, start, end);
        LocalDate date = start.toLocalDate();

        if (!scheduleMap.containsKey(date)) {
            scheduleMap.put(date, new ArrayList<>());
        }

        scheduleMap.get(date).add(event);
    }

    // 날짜별 일정 조회 (인터페이스에 명시된 메서드)
    @Override
    public List<ScheduleEvent> getEventsByDate(LocalDateTime date) {
        LocalDate localDate = date.toLocalDate();

        if (scheduleMap.containsKey(localDate)) {
            return scheduleMap.get(localDate);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteEvent(ScheduleEvent event) {
        LocalDate localDate = event.getStartTime().toLocalDate();

        if (scheduleMap.containsKey(localDate)) {
            List<ScheduleEvent> eventList = scheduleMap.get(localDate);
            eventList.remove(event);
            if (eventList.isEmpty()) {
                scheduleMap.remove(localDate);
            }
        }
    }



}

