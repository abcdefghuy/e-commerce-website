<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.example.ecommercewebsite.utils.ImageUtil" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Our Chat</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/chatModule.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"/>
</head>
<body>

<div class="wrapper">
  <section class="chat-area">

    <!-- user header -->
    <header class="${outgoingUser.status == 'InActive' ? 'inactive' : ''}">


      <c:if test="${'staff'.equals(current_role)}">
        <a href="${pageContext.request.contextPath}/Staff/loadCustomerList" class="back-icon">
          <i class="fas fa-arrow-left"></i>
        </a>
      </c:if>
      <c:if test="${!'staff'.equals(current_role)}">
        <a href="${pageContext.request.contextPath}/Staff/loadStaffChatList" class="back-icon">
          <i class="fas fa-arrow-left"></i>
        </a>
      </c:if>



      <c:if test="${outgoingUser.avatar != null}">
        <img alt="User" src="data:image/jpeg;base64, ${ImageUtil.DisplayImage(outgoingUser.avatar)}">
      </c:if>
      <c:if test="${outgoingUser.avatar == null}">
        <img alt="User" src="https://via.placeholder.com/150">
      </c:if>

      <div class="details">
        <span>
          ${outgoingUser.name}
        </span>
        <p>${status}</p>
      </div>
    </header>

    <div class="chat-box ${outgoingUser.status == 'InActive' ? 'inactive' : ''}">
      <!-------------------- Message -------------------->
      <!-------------------- End Message -------------------->
    </div>

    <!-- type area -->
    <form action="#" id="message_box" class="typing-area" autocomplete="off">
      <input type="hidden" name="incoming_id" id="incoming_id" value="${incoming_id}">
      <input type="hidden" name="outgoing_id" id="outgoing_id" value="${outgoing_id}">

      <input
              type="text"
              name="message"
              id="message"
              class="input-field"
              placeholder="
              ${outgoingUser.status == 'InActive' ?
              'Người dùng không hoạt động' :
              'Nhập tin nhắn tại đây...'}"
      ${outgoingUser.status == 'InActive' ? 'disabled' : ''}
      >
      <button
              type="button"
              onclick="submitForm()"
      ${outgoingUser.status == 'InActive' ? 'disabled' : ''}
      >
        <i class="fab fa-telegram-plane"></i>
      </button>

    </form>

  </section>
</div>

<%--<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/chat/chat.js"></script>--%>

<script>
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

</script>



</body>
</html>
