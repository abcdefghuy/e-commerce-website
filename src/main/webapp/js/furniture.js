const furDes = document.querySelectorAll(".fur_des");
furDes.forEach((item)=>{
    let itemDes = item.innerHTML;
    let arr = itemDes.split("-");
    let updatedContent = "";
    console.log(arr);
    arr.forEach((part) => {
        updatedContent += "- " + part.trim() + "<br>"; // Thêm từng phần tử và xuống dòng
    });

    item.innerHTML = updatedContent; // Gán lại nội dung mới cho phần tử
});


// dinh dang price

const priceValues = document.querySelectorAll(".priceValue")
if (priceValues){
    priceValues.forEach((item)=>{
        const formattedValue = parseInt(item.innerHTML).toLocaleString();
        item.innerHTML = "Giá: " + formattedValue + " VND";
    })
}