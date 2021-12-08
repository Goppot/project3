const userService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    getUserShops: async (id) => await fetch('/getUserShops/' + id, {
        method: 'GET',
        headers: userService.head
    }),
    getUserSalesItems: async (id) => await fetch('/getUserSalesItems/' + id, {
        method: 'GET',
        headers: userService.head
    })
}

async function startUserPage(){
    await fillUserItemTable();
    await fillUsersShops();
    await addImageToUser();
    await userNewShop();
}
startUserPage()


async function fillUserItemTable() {
    let id = $('.user-id').val()
    let response = await userService.getUserSalesItems(id)
    let items
    if (response.ok) {
        items = await response.json().then(result => result)
        console.log(items)
        userOrdersTableFill(items)
    }
}

async function userOrdersTableFill(items) {
    let itemsTable = $('#items-table')
    let rows = ""
    for (let i = 0; items[i] !== undefined; i++) {
        rows +=
            `
            <tr>
                <th><img src="data:image/png;base64,${items[i].images[0].picture}" height="60" width="60"></th>
                <th>${items[i].name}</th>
                <th>${items[i].categories[0]?.name}</th>
                <th>${items[i].price}</th>
            </tr>
        `
    }
    itemsTable.append(rows)
}

async function fillUsersShops() {
    let newShopCard =
        `<div class="card shadow-sm " style="width: 15rem;height: 21rem">
                <img src="https://sc02.alicdn.com/kf/HTB1qiaROCzqK1RjSZPxq6A4tVXaB/223383126/HTB1qiaROCzqK1RjSZPxq6A4tVXaB.jpg_.webp" class="card-img-top" height="200" >
                <div class="card-body">
                   <div class="card-title"><h4>Новый магазин</h4></div>
                    <div class="card-text" style="margin-bottom: 10px">Зарегестрируйте новый магазин тут!</div>   
                    <button type="button" id="btnUserShopAddForm" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#userShopAddForm">Регистрация</button>
                </div>
        </div>`

    let id = $('.user-id').val()
    let response = await userService.getUserShops(id)
    let shops
    let cards
    if (response.ok) {
        shops = await response.json().then(res => res)
        cards = await makeShopsCards(shops)
    } else {
        cards = []
    }
    console.log(cards)
    cards.push(newShopCard)
    let grid = ''
    for (let i = 0; i < cards.length; i += 3) {
        grid += await makeGrid(cards[i], cards[i + 1], cards[i + 2])
    }
    await $('#user-shops').append(grid)
}

async function makeShopsCards(shops) {
    console.log(shops)
    let cards = []
    for (let i = 0; shops[i] !== undefined; i++) {
        let shop = shops[i]
        let massage =
            `<div class="card shadow-sm " style="width: 15rem;height: 21rem">
                <img src="data:image/png;base64,${shop.logo[0].picture}" class="card-img-top" height="200">
                <div class="card-body" >
                    <div class="card-title"><h4>${shop.name}</h4></div>
                    <div class="card-text" style="margin-bottom: 5px">Рейтинг магазина:${shop.rating}/10</div>                        
                    <form action="/shop/${shop.id}">
                        <button type="submit" class="btn btn-primary">Страница магазина</button>
                      </form>
                </div>
            </div>`;

        cards.push(massage)

        console.log(cards)
    }
    return cards
}

async function makeGrid(fitsCard, secondCard, thirdCard) {
    let grid =
        `
    <div class="container-fluid">
        <div class="row flex-row flex-nowrap">
            <div class="col-5">
                ${fitsCard.toString()}
            </div><div class="col-5">
                ${secondCard == null ? '' : secondCard.toString()}
            </div>
            <div class="col-5">
                ${thirdCard == null ? '' : thirdCard.toString()}
            </div>
        </div>
    </div>`
    return grid

}


async function addImageToUser() {
    let array = Object.values($('.avatar-byte-array').val().split(',')).map(Number)

    let image = ''
    if (array.length < 5) {
        image = 'http://s3.amazonaws.com/37assets/svn/765-default-avatar.png'
    } else {
        image = `data:image/png;base64,${await arrayBufferToBase64(array)}`
    }

    $('#avatar-image').attr('src', image);
}

//convert byte array to base64
async function arrayBufferToBase64(buffer) {
    let binary = '';
    let bytes = new Uint8Array(buffer);
    let len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);

}

async function userNewShop() {
    const shopsAddModal = $('#userShopAddForm');
    $(shopsAddModal.find(":submit")).on('click', async () => {
        let imageInput = shopsAddModal.find('#logoAdd')[0].files[0]
        console.log(imageInput)
        let image
        if (imageInput !== undefined) {
            image = imageToBinary(imageInput)
        }
        console.log(image)
        let shop = {
            'name': shopsAddModal.find('#shopNameAdd').val(),
            'email': shopsAddModal.find('#shopEmailAdd').val(),
            'phone': shopsAddModal.find('#phoneNumberAdd').val(),
            'description': shopsAddModal.find('#descriptionAdd').val(),
            'location': {"id":1, "name": "Russia" + shopsAddModal.find("#locationAdd").val(), "hibernateLazyInitializer": {}},
            'logo': [{
                url: shopsAddModal.find('#logoAdd').val(),
                picture: image,
                isMain: true}],
            'rating': 0
        }
        console.log(shop)

        const response = await fetch("shop_api/", {
            method: 'POST',
            body: JSON.stringify(shop),
            headers: userService.head
        });
        console.log(response)
        if(response.ok) {
            shopsAddModal.modal("hide");
            location.reload()
        } else {
            let body = await response.json()
            console.log(body)
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.errors}
                            <button type="button" class="close" data-bs-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            shopsAddModal.prepend(alert);
        }
    })
}

// convert image to byte array
function imageToBinary(image) {
    let reader = new FileReader();
    reader.readAsDataURL(image);
    reader.onloadend = function () {
        let data = (reader.result).split(',')[1];
        let binaryBlob = atob(data);
        localStorage.setItem("image", binaryBlob)
    }
    let imageBase64 = localStorage.getItem("image")
    return base64ToBinary(imageBase64)
}

function base64ToBinary(imageBase64) {
    let bytes = [];
    for (let i = 0; i < imageBase64.length; i++) {
        bytes.push(
            imageBase64.charCodeAt(i)
        );
    }
    return bytes
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
//получить value cookie
function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}
//добавить куки корзины в БД юзера, и удалить их
function addCookieCartItem() {
    let cookie_split = document.cookie.split("; ")
    let basket = [];
    let cookie_data = [];
    let cookie_name = [];
    let cookie_value = [];
    let count = 0;
    for (let i = 0; i < cookie_split.length; i++) {
        if (cookie_split[i].indexOf("basket") > -1) {
            basket[count] = cookie_split[i];
            count++;
        }
    }
    for (let i = 0; i < basket.length; i++) {
        let name_split = basket[i].split("basket=")
        cookie_name[i] = name_split[0];
        cookie_value[i] = getCookie(cookie_name[i] + "basket")
        cookie_data[i] = {
            id : cookie_name[i],
            quantity : cookie_value[i]
        }
        document.cookie = cookie_name[i] + "basket" + "=" + 0 + "; path=/; max-age = 0"
    }
    console.log(cookie_name)
    console.log(cookie_data)
    if (cookie_data.length > 0) {
        let data = {
            cartItems: cookie_data
        }
        send_data("http://localhost:8888/api/cart-item/user", JSON.stringify(data), "POST");
    }
}
addCookieCartItem();
