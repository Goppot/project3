/**
 * Вставка категорий
 */
function sendRequestForCategories(url, body) {
    const headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }

    let temp = '';
    fetch('allCategories', {headers}).then(response => { // дописать актуальный Url
        return response.json().then(data => {
            data.forEach(
                category => {
                    temp += `<li><a class="dropdown-item" href="#">${category.name}</a></li>`;
                }
            )

            document.querySelector('#categoryItems').innerHTML = temp;
        });

    })
}

/**
 * Вставка магазинов
 */

function sendRequestForShops() {
    const headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }

    let temp = '';
    fetch('popularShops', {headers}).then(response => { // дописать актуальный Url
        return response.json().then(data => {
            data.forEach(
                shop => {
                    temp += `
        <div class="card" style="width: 15rem;">
        <img src="data:image/png;base64,${shop.logo[0].picture}" height="150" width="150">
            <div class="card-body">
                <h5 id="nameShop1" class="card-title">${shop.name}</h5>
                <h6>Описание: <h7>${shop.description}</h7></h6>
                <p class="card-text"></p>
                <a href="/shop/${shop.id}" class="btn btn-outline-warning">Страница магазина</a>
            </div>
        </div>`

                })
            document.querySelector(".shop-container").innerHTML = temp;
        });

    })
}

/**
 * Вставка популярных товаров
 */

function sendRequestForItems(url, body) {
    const headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }

    let temp = '';
    fetch('popularItems', {headers}).then(response => { // дописать актуальный Url
        return response.json().then(data => {
            data.forEach(
                item => {
                    temp += `
            <div class="cards_with_popular_products">
                <div class="card" style="width: 15rem;">
                    <img src="data:image/png;base64,${item.images[0].picture}"
                     class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 id="item1" class="card-title">${item.name}</h5>
                        <h6>Цена: ${item.price}</h6>
                        <h7>Описание товара: ${item.description}</h7>
                        <p class="card-text"></p>
                        <a id="addToCart" class="btn btn-primary" onclick="addToShoppingCart(${item.id}, 1)">В Корзину</a>
                    </div>
                </div>
            </div>
                        `;
                })

            document.querySelector(".item-container").innerHTML = temp;
        });

    })
}

sendRequestForCategories();
sendRequestForItems();
sendRequestForShops();