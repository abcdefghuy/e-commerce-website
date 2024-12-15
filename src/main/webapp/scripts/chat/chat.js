console.log("SCRIPT IS ACTIVED");

////////////////////////// Select DOM node //////////////////////////
const form = document.querySelector(".typing-area"),
    inputField = form.querySelector(".input-field"),
    sendBtn = form.querySelector("button"),
    chatBox = document.querySelector(".chat-box");

let out_id = document.getElementById("outgoing_id").value;
let in_id = document.getElementById("incoming_id").value;
////////////////////////// End select DOM node //////////////////////////



////////////////////////// Submit data to server //////////////////////////
function submitForm() {

    var form1 = document.getElementById("message_box");
    var formData = new FormData(form1);

    let msg = document.getElementById("message").value;

    //Ajax
    var xhr = new XMLHttpRequest();
    var url = "insertChat?outgoing_id=" + out_id + "&incoming_id=" + in_id + "&message=" + msg;

    xhr.open("POST", url, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            inputField.value = "";
            scrollToBottom();
            scrollToBottom();
        }
    };

    xhr.send();
}
////////////////////////// End submit data to server //////////////////////////



////////////////////////// Get data from server //////////////////////////
let isFirstRequest = true;

function sendGetRequest() {

    // URL servlet page
    var servletURL = "getChat?outgoing_id=" + out_id + "&incoming_id=" + in_id;

    // fetch API
    fetch(servletURL, {
        method: 'GET',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(data => {
            chatBox.innerHTML = data;

            if (isFirstRequest) {
                scrollToBottom();
                isFirstRequest = false;
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
////////////////////////// End get data from server //////////////////////////


const intervalId = setInterval(sendGetRequest, 700);

function scrollToBottom(){
    chatBox.scrollTop = chatBox.scrollHeight;
    console.log("scrollToBottom called");
}

form.onsubmit = (e) =>{
    e.preventDefault();
}
inputField.addEventListener('keydown', function(event) {
    if (event.key === 'Enter' || event.keyCode === 13) {
        event.preventDefault();

        submitForm();

        console.log('Enter key pressed');
    }
});
chatBox.onmouseenter = () =>{
    chatBox.classList.add("active");
}
chatBox.onmouseleave = () =>{
    chatBox.classList.remove("active");
}