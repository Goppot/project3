// Отправить данные
function send_data(url, data, method) {
    const response = fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: data,
    })
        .then(getUserCartItems)
}

function getUserCartItems(){
    fetch("http://localhost:8888/api/cart-item/user")
        .then(res => res.json())
        .then(cart_items => {
            let cart = ``;
            let count_price = 0;
            cart_items.forEach((i) => {
                let unit = i.quantity
                count_price += Number(i.item.price * unit)
                cart += `<div class="basket-div">
                           <div class="basket-img-div">
                           <img src="data:image/png;base64,${i.item.images[0].picture}" class="img-thumbnail">
                           <br/>
                           <button type="button" class="btn btn-danger basket-minus-button" id="${i.id}">-</button>
                           <span class="basket-span-unit-${i.id}">${unit}</span>
                           <button type="button" class="btn btn-success basket-plus-button" id="${i.id}">+</button>
                           </div>
                           <div class="basket-text-div">
                           <h6>Название: ${i.item.name}</h6>
                           <h6>Цена: ${i.item.price}</h6>
                           <p>Описание: ${i.item.description}</p>
                           <button type="button" class="btn btn-primary">Купить</button>
                           </div>
                           </div>`
            })
            let all_price = `<h3>Итого:</h3>
                             <h3>${count_price}</h3>`;
            document.querySelector(".home-shop-right-body").innerHTML = cart;
            document.querySelector(".home-shop-left-body").innerHTML = all_price;
        })
}
getUserCartItems();

//прибавить количество товара
function basket_plus_click(){
    $(document).on("click", ".basket-plus-button", function (e) {
        let id = e.target.id
        let data = {}
        send_data("http://localhost:8888/api/cart-item/plus/" + id, data, "PUT");
    });
}
basket_plus_click();

//отнять количество товара
function basket_minus_click(){
    $(document).on("click", ".basket-minus-button", function (e) {
        let id = e.target.id;
        let data = {};
        send_data("http://localhost:8888/api/cart-item/minus/" + id, data, "PUT");
    });
}
basket_minus_click();
