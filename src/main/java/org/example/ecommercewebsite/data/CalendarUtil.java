package org.example.ecommercewebsite.data;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public class CalendarUtil {
    public static String getHtmlCalendar(Map<LocalDate, Integer> staffCount_amShift, Map<LocalDate, Integer> staffCount_pmShift, int month, int year) {

        StringBuilder htmlCalendar = new StringBuilder();

        htmlCalendar.append("<table class='table table-bordered'>");
        htmlCalendar.append("<thead class='thead-dark'><tr>");
        htmlCalendar.append("<th>Mon</th><th>Tue</th><th>Wed</th><th>Thu</th><th>Fri</th><th>Sat</th><th>Sun</th>");
        htmlCalendar.append("</tr></thead>");
        htmlCalendar.append("<tbody><tr>");

        // Lấy số ngày trong tháng và ngày đầu tiên của tháng
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstDayOfMonth = yearMonth.atDay(1);

        // Xác định ngày đầu tiên của tháng là thứ mấy trong tuần (bắt đầu từ thứ Hai)
        int dayOfWeekValue = firstDayOfMonth.getDayOfWeek().getValue();

        YearMonth currentMonth = YearMonth.now();
        boolean isPreMonth = yearMonth.isBefore(currentMonth);

        // Thêm ô trống cho các ngày trước ngày đầu tiên của tháng
        for (int i = 1; i < dayOfWeekValue; i++) {
            htmlCalendar.append("<td></td>");
        }

        // Thêm các ngày trong tháng với số lượng nhân viên
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = LocalDate.of(year, month, day);
            int staff_amShift = staffCount_amShift.getOrDefault(currentDate, 0);
            int staff_pmShift = staffCount_pmShift.getOrDefault(currentDate, 0);

            boolean isOverAm = staff_amShift >= 5;
            boolean isOverPm = staff_pmShift >= 5;

            htmlCalendar.append("<td class='table-info'>");
            htmlCalendar.append("<strong>").append(day).append(" </strong>"); // Hiển thị ngày
            htmlCalendar.append("<form action='' method='post' class='d-inline'>")
                    .append("<input type='hidden' name='date' value='").append(currentDate).append("' />")
                    .append("<button type='submit' name='action' value='listStaff' class='btn btn-link p-0'>Ca làm</button>")
                    .append("</form>");

            htmlCalendar.append("<br><input type='checkbox' class='form-check-input mb-1' name='shift-selection-am' value='")
                    .append(currentDate)
                    .append("'")
                    .append(isPreMonth || isOverAm ? " disabled" : "")
                    .append("><label class='form-check-label'>Sáng: ")
                    .append(staff_amShift)
                    .append(" NV</label>");

            htmlCalendar.append("<br><input type='checkbox' class='form-check-input mb-1' name='shift-selection-pm' value='")
                    .append(currentDate)
                    .append("'")
                    .append(isPreMonth || isOverPm ? " disabled" : "")
                    .append("><label class='form-check-label'>Chiều: ")
                    .append(staff_pmShift)
                    .append(" NV</label>");

            htmlCalendar.append("</td>");

            // Khi hết tuần, xuống hàng mới
            if ((day + dayOfWeekValue - 1) % 7 == 0) {
                htmlCalendar.append("</tr><tr>");
            }
        }

        htmlCalendar.append("</tr></tbody></table>");
        return htmlCalendar.toString();
    }
}
