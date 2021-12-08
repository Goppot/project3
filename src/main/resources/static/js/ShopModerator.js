$(async function () {
    await getUnmoderatedShops();
    await getUnmoderatedShopsCountAndSetBadge();
})

const shopUrl = "http://" + window.location.hostname + ":8888/moderator/api/shops";
const shopFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    getUnmoderatedShops: async () => await fetch(`${shopUrl}/getUnmoderatedShops`),
    getOneUnmoderatedShop: async (id) => await fetch(`${shopUrl}/getOneUnmoderatedShop/${id}`),
    updateShop: async (shop) => await fetch(`${shopUrl}/editShop`, {
        method: 'PUT',
        headers: shopFetchService.head,
        body: JSON.stringify(shop)
    }),
    getUnmoderatedShopsCounter: async () => await fetch(`${shopUrl}/getUnmoderatedShopsCount`)
}

async function getUnmoderatedShops() {
    let shopCards = $('#shopsCards')
    await shopFetchService.getUnmoderatedShops()
        .then(res => res.json())
        .then(shops => {
                if (shops.length === 0) {
                    shopCards.append(`<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="emptyShopList">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Магазинов для модерации нет!</h5>                        
                            </p>
                        </div>
                    </div>
                </div>
                </div>
                </div>
                <br>`)
                } else {
                    shops.forEach(shop => {
                        console.log(shop)
                        let cardFilling =
                            `<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="shopCard${shop.id}">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Наименование магазина: </b>${shop.name}</h5>
                           <p class="card-text" id="cardText"><b>Описание магазина: </b>${shop.description}
                            <br>
                           <b>Email: </b> ${shop.email}
                           <br>
                           <b>Номер телефона: </b> ${shop.phone}
                           </p>
                        
                           <button data-shopid="${shop.id}" data-action="accept" class="btn btn-success">Одобрить</a>
                           <button data-shopid="${shop.id}" data-action="decline" class="btn btn-danger">Отклонить</button>
                          
                        </div>
                        <img class="col-sm-6 text-right" src="data:image/jpg;base64, ${shop.logo.picture}"/>
                    </div>
                </div>
                </div>
                </div>
                <br>`
                        shopCards.append(cardFilling);
                    })
                    getUnmoderatedShopsCountAndSetBadge();
                }
            }
        )
    $("#shopsCards").find('button').on('click', (event) => {
        let targetButton = $(event.target);
        let buttonShopId = targetButton.attr('data-shopid');
        let buttonAction = targetButton.attr('data-action');
        if (buttonAction === "accept") {
            acceptShop(buttonShopId);
        } else if (buttonAction === "decline") {
            declineShop(buttonShopId);
        }
    })
}

async function acceptShop(id) {
    await shopFetchService.getOneUnmoderatedShop(id)
        .then(res => res.json())
        .then(async shop => {
            console.log(shop);
            shop.moderated = true;
            shop.moderateAccept = true;
            const response = await shopFetchService.updateShop(shop);
            if (response.ok) {
                let shopCards = $('#shopsCards')
                shopCards.find(`#shopCard${shop.id}`).remove()
                await shopFetchService.getUnmoderatedShops()
                    .then(res => res.json())
                    .then(shops => {
                        if (shops.length === 0) {
                            shopCards.append(`<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="emptyShopList">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Магазинов для модерации нет!</h5>                        
                            </p>
                  
                        </div>
                    </div>
                </div>
                </div>
                </div>
                <br>`)
                        }
                    })
                await getUnmoderatedShopsCountAndSetBadge();
            } else {
                console.log("Произошла ошибка")
            }
        })
}

async function declineShop(id) {
    let form;
    let cardField;
    if (!(document.getElementById(`declineShop${id}`))) {
        await shopFetchService.getOneUnmoderatedShop(id)
            .then(res => res.json())
            .then(shop => {
                    if (!(document.getElementById(`declineShop${shop.id}`))) {
                        form = `<form id="declineShop${shop.id}">
                    <input type="text" class="form-control" id="rejectedShopReasonTo${shop.id}" name="rejectedReasonTo${shop.id}" placeholder="Введите причину отказа" style="width: 200px; height: 150px;" 
                    min="5">
                    <button type="button" class="btn btn-primary" id="rejectShopReason${shop.id}">Отправить</button>\
                    </form>`
                        cardField = $("#shopsCards").find(`#shopCard${shop.id}`);
                        cardField.append(form);
                        console.log("Createdd form")
                    }
                    $(`#declineShop${shop.id}`).find('button').on('click', () => {
                        shop.moderated = true;
                        shop.moderateAccept = false;
                        shop.moderatedRejectReason = document.querySelector(`#rejectedShopReasonTo${shop.id}`).value;
                        sendShopAndDeleteCard(shop);
                    })
                }
            )
    }
}

async function sendShopAndDeleteCard(shop) {
    const response = await shopFetchService.updateShop(shop);
    if (response.ok) {
        let shopCards = $('#shopsCards')
        shopCards.find(`#shopCard${shop.id}`).remove()
        await shopFetchService.getUnmoderatedShops()
            .then(res => res.json())
            .then(shops => {
                if (shops.length === 0) {
                    shopCards.append(`<div class="row">
                         <div class="col-sm-10">
                            <div class="card" id="emptyShopList">
                                <div class="row card-body">
                                    <div class="col-sm-10">
                          <h5 class="card-title"><b>Магазинов для модерации нет!</h5>                        
                            </p>                  
                        </div>
                    </div>
                </div>
                </div>
                </div>
                <br>`)
                }
                getUnmoderatedShopsCountAndSetBadge();
            })
    } else {
        console.log("Произошла ошибка")
    }
}

async function getUnmoderatedShopsCountAndSetBadge() {
    let precounter = await shopFetchService.getUnmoderatedShopsCounter();
    let counter = precounter.json();
    counter.then(counter => {
        $("#v-pills-tab #v-pills-shops-tab").html(`Магазины <span
                        class="badge badge-secondary">${counter}</span>
                    <span class="sr-only">unmoderated shops</span></a>`)
    })
}