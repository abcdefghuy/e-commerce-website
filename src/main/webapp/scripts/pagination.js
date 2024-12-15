const rowsPerPage = 5;
let currentPage = 1;

const rows = Array.from(document.querySelectorAll('#employee-table tr'));
const totalPages = Math.ceil(rows.length / rowsPerPage);

function displayRows(page) {
    const start = (page - 1) * rowsPerPage;
    const end = start + rowsPerPage;

    rows.forEach((row, index) => {
        if (index >= start && index < end) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });

    document.getElementById('page-info').textContent = `Page ${page} of ${totalPages} `;
    document.getElementById('prev-page').disabled = page === 1;
    document.getElementById('next-page').disabled = page === totalPages;
}

document.getElementById('prev-page').addEventListener('click', function() {
    if (currentPage > 1) {
        currentPage--;
        displayRows(currentPage);
    }
});

document.getElementById('next-page').addEventListener('click', function() {
    if (currentPage < totalPages) {
        currentPage++;
        displayRows(currentPage);
    }
});

// Hiển thị trang đầu tiên khi tải trang
displayRows(currentPage);