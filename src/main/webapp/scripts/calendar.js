/**
 * 
 */
const monthNames = [
            "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
            "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
        ];
        const dayNames = ["T2", "T3", "T4", "T5", "T6", "T7", "CN"]; // Tên các ngày trong tuần bắt đầu từ thứ Hai
    
        let currentMonth = new Date().getMonth();
        let currentYear = new Date().getFullYear();
        const today = new Date().getDate(); // Ngày hiện tại
        let selectedEmployee = null;
        const maxShifts = 15;
        const employeeShifts = {}; // Để lưu ca làm việc cho từng nhân viên
    
        function renderCalendar(month, year) {
            const calendarTable = document.getElementById("calendarTable");
            const monthTitle = document.getElementById("monthTitle");
            monthTitle.textContent = monthNames[month] + " " + year;
            calendarTable.innerHTML = ""; // Xóa nội dung trước khi vẽ lại
    
            // Thêm tên các ngày trong tuần
            let daysHeader = "<tr>";
            for (const day of dayNames) {
                daysHeader += `<th>${day}</th>`;
            }
            daysHeader += "</tr>";
            calendarTable.innerHTML += daysHeader;
    
            const firstDay = new Date(year, month).getDay();
            const daysInMonth = new Date(year, month + 1, 0).getDate();
    
            // Chỉnh sửa để đầu tuần là thứ Hai
            const adjustedFirstDay = (firstDay === 0) ? 6 : firstDay - 1;
    
            let row = "<tr>";
    
            // Thêm ô trống cho những ngày trước tháng
            for (let i = 0; i < adjustedFirstDay; i++) {
                row += "<td></td>";
            }
    
            // Thêm các ngày trong tháng
            for (let day = 1; day <= daysInMonth; day++) {
                const highlightClass = (day === today && month === new Date().getMonth() && year === currentYear) ? 'highlight' : '';
                const selectedShifts = employeeShifts[selectedEmployee] || [];
                const shiftClass = selectedShifts.some(shift => shift.day === day) ? 'selected' : '';
    
                row += `<td class="calendar-cell ${highlightClass} ${shiftClass}" data-day="${day}">
                            ${day}<br>
                            <input type="checkbox" class="morning-shift" ${shiftClass ? 'checked' : ''}> 7 - 11h<br>
                            <input type="checkbox" class="afternoon-shift" ${shiftClass ? 'checked' : ''}> 13 - 17h<br>
                            <p>Số lượng: </p>
                        </td>`;
                if ((day + adjustedFirstDay) % 7 === 0) {
                    row += "</tr><tr>"; // Chuyển sang hàng tiếp theo
                }
            }
            row += "</tr>";
            calendarTable.innerHTML += row;
        }
    
        document.getElementById("prevMonth").addEventListener("click", function() {
            if (currentMonth === 0) {
                currentMonth = 11;
                currentYear--;
            } else {
                currentMonth--;
            }
            renderCalendar(currentMonth, currentYear);
        });
    
        document.getElementById("nextMonth").addEventListener("click", function() {
            if (currentMonth === 11) {
                currentMonth = 0;
                currentYear++;
            } else {
                currentMonth++;
            }
            renderCalendar(currentMonth, currentYear);
        });
    
        document.getElementById("employeeSelect").addEventListener("change", function() {
            selectedEmployee = this.value;
            renderCalendar(currentMonth, currentYear);
            updateSelectedShifts();
        });
    
        document.getElementById("confirmButton").addEventListener("click", function() {
            if (!selectedEmployee) {
                alert("Vui lòng chọn một nhân viên.");
                return;
            }
            const shifts = [];
            const days = document.querySelectorAll('#calendarTable td[data-day]');
            days.forEach(dayCell => {
                const day = dayCell.getAttribute('data-day');
                const morningShift = dayCell.querySelector('.morning-shift').checked;
                const afternoonShift = dayCell.querySelector('.afternoon-shift').checked;
                if (morningShift) {
                    shifts.push({ day: day, shift: 'Ca Sáng', date: new Date(currentYear, currentMonth, day).toLocaleDateString('vi-VN') });
                }
                if (afternoonShift) {
                    shifts.push({ day: day, shift: 'Ca Chiều', date: new Date(currentYear, currentMonth, day).toLocaleDateString('vi-VN') });
                }
            });
            if (shifts.length > maxShifts) {
                alert(`Bạn chỉ có thể chọn tối đa ${maxShifts} buổi.`);
                return;
            }
            employeeShifts[selectedEmployee] = shifts; // Lưu ca làm cho nhân viên
            updateSelectedShifts();
            renderCalendar(currentMonth, currentYear); // Vẽ lại lịch với ca đã chọn
        });
    
        function updateSelectedShifts() {
            const shiftsContainer = document.getElementById("selectedShifts");
            shiftsContainer.innerHTML = "";
            if (selectedEmployee) {
                const shifts = employeeShifts[selectedEmployee] || [];
                if (shifts.length > 0) {
                    shiftsContainer.innerHTML = `<strong>Ca đã chọn cho nhân viên ${selectedEmployee}:</strong><br>`;
                    shifts.forEach(shift => {
                        shiftsContainer.innerHTML += `Ngày ${shift.date} (${shift.shift})<br>`;
                    });
                } else {
                    shiftsContainer.innerHTML = `<strong>Ca đã chọn cho nhân viên ${selectedEmployee}:</strong> Không có`;
                }
            }
        }
    
        // Vẽ lịch tháng hiện tại
        renderCalendar(currentMonth, currentYear);