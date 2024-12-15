function previewAvatar(event) {
    const reader = new FileReader();
    reader.onload = function() {
        const avatarImage = document.getElementById('avatar-image');
        avatarImage.src = reader.result;
    }
    reader.readAsDataURL(event.target.files[0]);
}