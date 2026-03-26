import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;


public class CalendarGUI extends JFrame {
    private final HumanScheduleManager manager = new HumanScheduleManager();

    private final JPanel calendarPanel;
    private final JLabel monthLabel;
    private LocalDate currentDate;

    public CalendarGUI() {
        setTitle("Swing Calendar");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        currentDate = LocalDate.now();

        // 상단 패널 (월 변경)
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        monthLabel = new JLabel("", SwingConstants.CENTER);

        // updateCalendar 호출로 해당 월의 일정 재구성
        prevButton.addActionListener(e -> {
            currentDate = currentDate.minusMonths(1);
            updateCalendar();
        });

        // updateCalendar 호출로 해당 월의 일정 재구성
        nextButton.addActionListener(e -> {
            currentDate = currentDate.plusMonths(1);
            updateCalendar();
        });

        topPanel.add(prevButton, BorderLayout.WEST);
        topPanel.add(monthLabel, BorderLayout.CENTER);
        topPanel.add(nextButton, BorderLayout.EAST);

        // 달력 패널
        calendarPanel = new JPanel(new GridLayout(0, 7, 3, 3));

        add(topPanel, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);

        updateCalendar();
    }

    private void updateCalendar() {
        // updateCalendar 호출 시 매번 일정 전체를 새로 그림
        calendarPanel.removeAll();

        // 월 레이블 업데이트
        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        monthLabel.setText(currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + currentDate.getYear());

        // 요일 출력
        for (DayOfWeek dow : DayOfWeek.values()) {
            calendarPanel.add(new JLabel(dow.getDisplayName(TextStyle.SHORT, Locale.ENGLISH), SwingConstants.CENTER));
        }

        // 시작 요일 및 총 일 수
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int startDay = firstDayOfMonth.getDayOfWeek().getValue(); // 1 (Mon) ~ 7 (Sun)
        int daysInMonth = yearMonth.lengthOfMonth();

        // 앞 빈칸
        for (int i = 1; i < startDay; i++) {
            calendarPanel.add(new JLabel(""));
        }

        // 날짜 출력
        for (int day = 1; day <= daysInMonth; day++) {
            JPanel dayPanel = new JPanel(new BorderLayout());
            JButton dayButton = new JButton(String.valueOf(day));


            dayButton.addActionListener(e -> {
                int clickedDay = Integer.parseInt(dayButton.getText());
                LocalDateTime selectedDate = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), clickedDay).atStartOfDay();

                JTextField titleField = new JTextField();
                JTextField descField = new JTextField();
                JTextField startField = new JTextField("10:00");
                JTextField endField = new JTextField("12:00");

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Title:"));
                panel.add(titleField);
                panel.add(new JLabel("Description:"));
                panel.add(descField);
                panel.add(new JLabel("Start Time (HH:mm):"));
                panel.add(startField);
                panel.add(new JLabel("End Time (HH:mm):"));
                panel.add(endField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Add Event", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String title = titleField.getText();
                        String desc = descField.getText();
                        LocalDateTime start = selectedDate.withHour(Integer.parseInt(startField.getText().split(":")[0])).withMinute(Integer.parseInt(startField.getText().split(":")[1]));
                        LocalDateTime end = selectedDate.withHour(Integer.parseInt(endField.getText().split(":")[0])).withMinute(Integer.parseInt(endField.getText().split(":")[1]));

                        manager.addEvent(title, desc, start, end);
                        updateCalendar(); // 새로 그리기
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "시간 형식이 올바르지 않습니다. 예: 10:00", "입력 오류", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });



            JPanel eventsContainer = new JPanel();
            eventsContainer.setLayout(new BoxLayout(eventsContainer, BoxLayout.Y_AXIS));

            LocalDate thisDate = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), day);
            for (ScheduleEvent event : manager.getEventsByDate(thisDate.atStartOfDay())) {
                JPanel eventPanel = new JPanel(new BorderLayout());


                String formatted = "<html>[" +
                        event.getStartTime().toLocalTime() + " - " +
                        event.getEndTime().toLocalTime() + "]<br>" +
                        event.getTitle() + "</html>";
                JLabel eventLabel = new JLabel(formatted);
                eventLabel.setPreferredSize(new Dimension(160, 32));


                JButton deleteButton = new JButton("X");

                deleteButton.setMargin(new Insets(2, 5, 2, 5)); // X 버튼 작게
                deleteButton.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(this, "이 일정을 삭제하시겠습니까?", "삭제 확인", JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == JOptionPane.OK_OPTION) {
                        manager.deleteEvent(event);
                        updateCalendar();
                    }
                });

                eventPanel.add(eventLabel, BorderLayout.CENTER);
                eventPanel.add(deleteButton, BorderLayout.EAST);
                eventsContainer.add(eventPanel);
            }


            dayPanel.add(dayButton, BorderLayout.NORTH);
            dayPanel.add(eventsContainer, BorderLayout.CENTER);
            calendarPanel.add(dayPanel);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    public static void main(String[] args) {
        JFrame obj = new CalendarGUI();
        obj.setVisible(true);
    }
}