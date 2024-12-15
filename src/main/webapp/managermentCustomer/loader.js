$(document).ready(function () {
    // Ẩn loader khi trang đã tải xong
    $('#global-loader').fadeOut('slow');
});
window.onload = function () {
    // Ẩn loader khi trang đã tải xong
    var loader = document.getElementById('global-loader');
    if (loader) {
        loader.style.display = 'none';
    }
};
$(document).ready(function () {
    $('#deleteReason').on('change', function () {
        if ($(this).val() === 'Khác') {
            $('#deleteReasonText').show();
        } else {
            $('#deleteReasonText').hide();
        }
    });
});