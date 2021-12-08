//получить value cookie
function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}
// Отправить данные
function send_data(url, data, method) {
    const response = fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: data,
    })
}
//отправить запрос содержащий id товаров и получить ответ List<Item>
function basket_data(url, data, method){
    const response = fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: data,
    }).then(res => res.json())
        .then(basket_item => {
            console.log(basket_item)
            let count_price = 0;
            let basket = ``;
            basket_item.forEach((i) => {
                let unit = getCookie(i.id + "basket")
                count_price += Number(i.price * unit)
                basket += `<div class="basket-div">
                           <div class="basket-img-div">
                           <img src="data:image/png;base64,${i.images[0].picture}" class="img-thumbnail">
                           <br/>
                           <button type="button" class="btn btn-danger basket-minus-button" id="${i.id}">-</button>
                           <span class="basket-span-unit-${i.id}">${unit}</span>
                           <button type="button" class="btn btn-success basket-plus-button" id="${i.id}">+</button>
                           </div>
                           <div class="basket-text-div">
                           <h6>Название: ${i.name}</h6>
                           <h6>Цена: ${i.price}</h6>
                           <p>Описание: ${i.description}</p>
                           </div>
                           </div>`
            })
            let all_price = `<h3>Итого:</h3>
                             <h3>${count_price}</h3>`;
            document.querySelector(".home-shop-right-body").innerHTML = basket;
            document.querySelector(".home-shop-left-body").innerHTML = all_price;

        })
}
//определение id товара из cookie name
function basket_cookie_name(){
    let cookie_split = document.cookie.split("; ")
    console.log(cookie_split)
    let basket = [];
    let cookie_name = [];
    let cookie_id = [];
    let count = 0;
    for (let i = 0; i < cookie_split.length; i++) {
        if (cookie_split[i].indexOf("basket") > -1){
            basket[count] = cookie_split[i];
            count++;
        }
    }
    console.log(basket);
    for (let i = 0; i < basket.length; i++) {
        let name_split = basket[i].split("basket=")
        cookie_name[i] = name_split[0];
        cookie_id[i] = {
            id : cookie_name[i]
        }
    }
    console.log(cookie_name)
    console.log(cookie_id)

    if (cookie_id.length > 0){
        let data = {
            cartItems : cookie_id
        }
        basket_data("http://localhost:8888/api/cart-item",JSON.stringify(data),"POST");
    } else {
        document.querySelector(".home-shop-right-body").innerHTML = `<h3>Корзина пуста</h3>`
        document.querySelector(".home-shop-left-body").innerHTML = `<h3>Итого:</h3>
                                                                                <h3>0</h3>`
    }

}
//прибавить количество товара
function basket_plus_click(){
    const plus = (id) => {
        let cookie_value = getCookie(id + "basket")
        if (cookie_value !== undefined) {
            let value = Number(cookie_value) + 1;
            document.cookie = id + "basket" + "=" + value + "; path=/";
        } else {
            document.cookie = id + "basket" + "=" + 1 + "; path=/";
        }
    }
    $(document).on("click", ".basket-plus-div", function (e) {
        let id = e.target.id
        let user_tag = document.getElementById("userTag");
        if (user_tag === null) {
            plus(id);
        } else {
            let data = {}
            send_data("http://localhost:8888/api/cart-item/add/item/" + id, data, "POST");
        }
    })
    $(document).on("click", ".basket-plus-button", function (e) {
        let id = e.target.id
        plus(id);
        basket_cookie_name()
    })
}
basket_plus_click();

//отнять количество товара
function basket_minus_click(){
    $(document).on("click", ".basket-minus-button", function (e) {
        let id = e.target.id
        let cookie_value = getCookie(id + "basket")
        if (Number(cookie_value) !== 0) {
            if (cookie_value !== undefined) {
                let value = Number(cookie_value) - 1;
                document.cookie = id + "basket" + "=" + value + "; path=/";
            }
        }
        let text = `${getCookie(id + "basket")}`
        document.querySelector(".basket-span-unit-" + id + "").innerHTML = text
        if(Number(getCookie(id + "basket")) === 0) {
            document.cookie = id + "basket" + "=" + 0 + "; path=/; max-age = 0";
        }
        if (document.cookie.length === 0){
            document.querySelector(".home-shop-right-body").innerHTML = `<h3>Корзина пуста</h3>`
            document.querySelector(".home-shop-left-body").innerHTML = `<h3>Итого:</h3>
                                                                                <h3>0</h3>`
        } else {
        basket_cookie_name();
        }
    })
}
basket_minus_click();

function click_basket(){
    $(document).on("click", ".basket-btn", function (){
        basket_cookie_name();
    })
}
click_basket();

function basket_btn(){
    let user_tag = document.getElementById("userTag");
    if (user_tag !== null){
        let text = `<a href="/user-cart-page">
                    <button type="button" class="btn btn-outline-primary basket-btn1">Корзина</button>
                </a>`;
        document.querySelector(".div-header-right-one").innerHTML = text
    }

}
basket_btn();
