const buttonPaginations = document.querySelectorAll("[data-pagination]");
if (buttonPaginations){
    let url = new URL (window.location.href);
    console.log(url);
    buttonPaginations.forEach((button)=>{
        button.addEventListener("click",()=>{
            const page = button.getAttribute("data-pagination");
            console.log(page);
            url.searchParams.set("page",page);
            window.location.href = url.href;
        })
    })
}


// price slider
const priceSlider = document.querySelector("#priceSlider");
if (priceSlider) {


    priceSlider.addEventListener("input", function () {
        const formattedValue = parseInt(priceSlider.value).toLocaleString();
        document.querySelector("#priceValue").textContent = `${formattedValue} VND`;
    });

    priceSlider.addEventListener("change", function () {
        const formSearch = document.querySelector("#formSearch");
        const priceInput = formSearch.querySelector("input[name=price]");
        if (priceInput) {
            priceInput.value = priceSlider.value;
            formSearch.submit();
        }
    });

}


// dinh dang price

const priceValues = document.querySelectorAll(".priceValue")
if (priceValues){
    priceValues.forEach((item)=>{
        const formattedValue = parseInt(item.innerHTML).toLocaleString();
        item.innerHTML = formattedValue + " VND";
    })
}

//color
const colorSelect = document.querySelector("#colorSelect");
if (colorSelect){
    console.log(colorSelect);
    colorSelect.addEventListener("change",function (){
        const formSearch = document.querySelector("#formSearch");
        const colorInput = formSearch.querySelector("input[name=Color]");
        colorInput.value = colorSelect.value;
        formSearch.submit();
    });
}

//NSX
const nsxRadios = document.querySelectorAll('input[name="nsx"]');
if (nsxRadios){
    nsxRadios.forEach((item)=>{
        item.addEventListener("change",function (){
            if (item.checked){
                const formSearch = document.querySelector("#formSearch");
                const nsxInput = formSearch.querySelector("input[name=NSX]");
                nsxInput.value = item.value;
                console.log(nsxInput);
                console.log(formSearch);
                formSearch.submit();
            }
        })
    })
}

// button submit
const buttonSubmitSearch = document.querySelectorAll(".buttonSubmitSearch");
if (buttonSubmitSearch){
    buttonSubmitSearch.forEach((item)=>{
        item.addEventListener("click",function (event){
            event.preventDefault(); // Ngăn hành động mặc định của nút submit
            const formSearch = document.querySelector("#formSearch");
            const colorInput = formSearch.querySelector("input[name=Color]");
            const priceInput = formSearch.querySelector("input[name=price]");
            const nsxInput = formSearch.querySelector("input[name=NSX]");
            colorInput.value = "";
            priceInput.value = "70000000";
            nsxInput.value = "";
            formSearch.submit();
        })
    })
}


