const buttonSignUp = document.getElementById("signUp");
const buttonSignIn = document.getElementById("signIn");
const container = document.getElementById("container");

if (buttonSignUp) {
    buttonSignUp.addEventListener('click', () => {
        container.classList.add("right-panel-active");
    });
}

if (buttonSignIn) {
    buttonSignIn.addEventListener('click', () => {
        container.classList.remove("right-panel-active");
    });
}

const messages = document.querySelectorAll(".dn");
if (messages.length > 0) {
    messages.forEach(message => {
        if (message.innerHTML !== "Đăng kí tài khoản thành công" && message.innerHTML !== "Tài khoản hoặc mật khẩu không được trống"  && message.innerHTML !== "Vui lòng nhập đúng email" && message.innerHTML !== "Sai tài khoản hoặc mật khẩu" && message.innerHTML !== "")   {
            message.classList.add("hidden");
            container.classList.add("right-panel-active");
        } else {
            const messageDK = document.querySelectorAll(".dk");
            if (messageDK.length > 0) {
                messageDK.forEach(item => {
                    item.classList.add("hidden");
                });
            }
        }
    });
}

// Set thời gian cho các thông báo
setTimeout(() => {
    const informMessages = document.querySelectorAll(".inform"); // Sửa "message" thành "inform"
    if (informMessages.length > 0) {
        informMessages.forEach(item => {
            item.classList.add("hidden");
        });
    }
}, 3000); // 3 giây (3000ms)

console.log(document.querySelectorAll(".inform"));

// kiểm tra thời gian sắp hết sesion
// setTimeout(function() {
//     alert("Phiên của bạn sắp hết hạn. Vui lòng thao tác để tiếp tục.");
// }, 25 * 60 * 1000); // 25 phút
