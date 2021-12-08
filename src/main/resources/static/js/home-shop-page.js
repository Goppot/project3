//получить кнопку поиска
let searchHomeShopButton = document.getElementById('searchHomeShopPageButton')
//получить pathname url
const pathname = document.location.pathname;

//получить названия и рейтинг
let shopName = ""
let itemName = ""
let itemRating = ""
let shopRating = ""

//получить все товары этого магазина
async function shop_items() {
    await fetch("http://localhost:8888/shop_api" + pathname)
        .then(res => res.json())
        .then(shop => {
            shopRating = shop.rating
            shopName = shop.name;
            let logo = `<div class="shop-div">
        <img src="data:image/png;base64,${shop.logo[0].picture}" class="img-thumbnail">
        <h3>${shop.name}</h3>
        <h3>${shop.location.name}</h3>
        <div id="ratingShow"></div>
        </div>`;
                let text = `<div><h3>Самые популярные товары</h3></div>`
                let items = ``;
                shop.items.forEach((i) => {
                    items += `<div class="item-div">
        <a href="/item/${i.id}"><img src="data:image/png;base64,${i.images[0].picture}" class="img-thumbnail"></a>
        <h6>${i.name}</h6>
        <h6>${i.price}</h6>
        <p>${i.description}</p>
        <button type="button" class="btn btn-primary basket-plus-div" id="${i.id}">В корзину</button>
        </div>`
                })
                document.querySelector(".home-shop-right-body").innerHTML = text + items;
                document.querySelector(".home-shop-left-body").innerHTML = logo;
            })
}

//получить страницу товара
async function item_present() {
    await fetch("http://localhost:8888/shop" + pathname)
        .then(res => res.json())
        .then(item => {
            itemRating = item.rating
            itemName = item.name
            let item_present = `
                                <div class="item-present">
                                    <h3>name: ${item.name}</h3>
                                    <h3>price: ${item.price}</h3>
                                    <h3>shopName: ${item.shopName}</h3>
                                    <div id="ratingShow"></div>
                                    <button type="button" class="btn btn-primary basket-plus-div" id="${item.id}">В корзину</button>
                                    </div>
                              
                                 `;
            let item_img = ``;
            item.images.forEach((i) => {
                item_img += `<div class="item-div">
                <img src="data:image/png;base64,${i.picture}" class="img-thumbnail" style="width: 300px">
                </div>`
            })
            document.querySelector(".home-shop-right-body").innerHTML = item_img + item_present;
        })
}

//получение предметов по поиску
async function findShopItems() {
    // получаем значение поиска
    const searchInput = document.getElementById('searchHomeShopPage');
    // обрабатываем нажатие кнопки поиска;
    $(searchHomeShopButton).on('click', async function () {
        console.log(searchInput.value)
        await fetch("http://localhost:8888/shop_api" + pathname)
            .then(res => res.json())
            .then(shop => {
                document.querySelector('.home-shop-right-body').innerHTML = search(shop.items, searchInput);
            });
    });
}


//определить с какого контроллера зашли на страницу
async function shop_or_item() {
    if (pathname.indexOf("basket") > -1) {
        basket_cookie_name();
    } else if (pathname.indexOf("shop") > -1) {
        await shop_items();
        await userShopReview()
        await getRatingHtml();
        await getRating(shopRating)
        await getShopReviews();
        await findShopItems
    } else if (pathname.indexOf("item") > -1) {
        await item_present();
        await userItemReview();
        await getRatingHtml();
        await getRating(itemRating)
        await getItemReviews();
    }
}
shop_or_item();
