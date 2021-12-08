class CartItem {
    constructor(item, shop, quantity) {
        this.id = null
        this.itemDto = item
        this.shopDto = shop
        this.quantity = quantity
    }

    id
    itemDto
    shopDto
    quantity
}

let cartItems = document.querySelector('#cartItemsList');
let userTag = document.querySelector('#userTag');

function sendRequestForAddNewCartItem(body) {
    if (localStorage.getItem('isAnonymous') === 'false') {
        fetch("/shoppingCart/addNewCartItem", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        })
            .then(res => {
                console.dir(res)
                drawShoppingCart()
            })
    }
}

function sendRequestForUpdateQuantityCartItem(body) {
    if (localStorage.getItem('isAnonymous') === 'false') {
        fetch("/shoppingCart/updateQuantity", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        })
            .then(res => {
                console.dir(res)
                drawShoppingCart()
            })
    }
}

function sendRequestForDeletePositionFromCart(body) {
    if (localStorage.getItem('isAnonymous') === 'false') {
        fetch("/shoppingCart/deletePositionFromCart", {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        })
            .then(res => {
                console.dir(res)
                drawShoppingCart()
            })
    }
}

function loadUserShoppingCartFromServer() {
    if (localStorage.getItem('isAnonymous') === 'false') {
        fetch("/shoppingCart/getUserShoppingCart")
            .then(res => res.json())
            .then(data => {
                if (data.length === 0) {
                    loadLocalShoppingCartToServer()
                } else {
                    localStorage.removeItem('cart')
                }
            })
    }
}

function loadLocalShoppingCartToServer() {
    if (localStorage.getItem('isAnonymous') === 'false' && localStorage.getItem('cart') !== null) {
        fetch("/shoppingCart/loadLocalShoppingCartToServer", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: localStorage.getItem('cart')
        })
            .then(res => {
                localStorage.removeItem('cart')
            })
    }
}

function drawModalBody(tmpCart) {
    cartItems.innerHTML = '';
    let sum = 0
    if (tmpCart != null) {
        for (let i = 0; i < tmpCart.length; i++) {
            sum += tmpCart[i].itemDto.price * tmpCart[i].quantity
            cartItems.innerHTML += `
            <tr>
                <td>${tmpCart[i].itemDto.name}</td>
                <td>&nbsp;</td>
                <td>
                    <button type="button" class="btn btn-danger" onclick="deleteOneFromShoppingCartPosition(${tmpCart[i].itemDto.id})">-</button>
                </td>
                <td>${tmpCart[i].quantity}</td>
                <td>
                    <button type="button" class="btn btn-success" onclick="addOneToShoppingCartPosition(${tmpCart[i].itemDto.id})">+</button>
                </td>
                <td>&nbsp;</td>
                <td>${tmpCart[i].itemDto.price}</td>
                <td>&nbsp;</td>
                <td>${tmpCart[i].itemDto.price * tmpCart[i].quantity}</td>
                <td>&nbsp;</td>
                <td>
                <button type="button" class="btn btn-danger" onclick="deletePositionFromCart(${tmpCart[i].itemDto.id})">Удалить позицию</button>
                </td>
            </tr>
        `;
        }
    }
    cartItems.innerHTML += `
        <tr>
            <td colspan="11">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="3" align="left">Общая сумма:</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td colspan="3" align="right">${sum}</td>
        </tr>`;
}

function addToShoppingCart(id, quantity) {
    fetch("shop/item/" + id)
        .then(res => res.json())
        .then(item => {
            fetch("/shop_api/" + item.shopName)
                .then(res => res.json())
                .then(shop => {
                    let cartItem = new CartItem(item, shop, quantity)
                    if (localStorage.getItem('isAnonymous') === 'true') {
                        if (localStorage.getItem('cart') === null) {
                            let tmpCart = []
                            tmpCart.push(cartItem)
                            localStorage.setItem('cart', JSON.stringify(tmpCart))
                        } else {
                            let shoppingCart = JSON.parse(localStorage.getItem('cart'));
                            for (let i = 0; i < shoppingCart.length; i++) {
                                if (parseInt(cartItem.itemDto.id) === parseInt(shoppingCart[i].itemDto.id)) {
                                    shoppingCart[i].quantity = shoppingCart[i].quantity + quantity
                                    localStorage.setItem('cart', JSON.stringify(shoppingCart))
                                    cartItem.quantity = shoppingCart[i].quantity
                                    return
                                }
                            }
                            shoppingCart.push(cartItem)
                            localStorage.setItem('cart', JSON.stringify(shoppingCart))
                        }
                    } else {
                            fetch("/shoppingCart/getUserShoppingCart")
                                .then(res => res.json())
                                .then(data => {
                                    if (data.length !== 0) {
                                        data.forEach(
                                            tmpCartItem => {
                                                if (tmpCartItem.itemDto.id === cartItem.itemDto.id) {
                                                    cartItem.quantity += tmpCartItem.quantity
                                                    sendRequestForUpdateQuantityCartItem(cartItem)
                                                    return
                                                }
                                            }
                                        )
                                        sendRequestForAddNewCartItem(cartItem)
                                        return
                                    }
                                })
                    }
                })
        })
}

function addOneToShoppingCartPosition(id) {
    if (localStorage.getItem('isAnonymous') === 'true') {
        let shoppingCart = JSON.parse(localStorage.getItem('cart'));
        for (let i = 0; i < shoppingCart.length; i++) {
            if (parseInt(shoppingCart[i].itemDto.id) === id) {
                shoppingCart[i].quantity++
            }
        }
        localStorage.setItem('cart', JSON.stringify(shoppingCart))
        drawShoppingCart()
    } else {
        fetch("/shoppingCart/getUserShoppingCart")
            .then(res => res.json())
            .then(data => {
                if (data.length !== 0) {
                    data.forEach(
                        tmpCartItem => {
                            if (tmpCartItem.itemDto.id === id) {
                                tmpCartItem.quantity++
                                sendRequestForUpdateQuantityCartItem(tmpCartItem)
                            }
                        }
                    )
                }
            })
    }
}

function deleteOneFromShoppingCartPosition(id) {
    if (localStorage.getItem('isAnonymous') === 'true') {
        let shoppingCart = JSON.parse(localStorage.getItem('cart'));
        for (let i = 0; i < shoppingCart.length; i++) {
            if (parseInt(shoppingCart[i].itemDto.id) === id && parseInt(shoppingCart[i].quantity) >= 2) {
                shoppingCart[i].quantity--
            }
        }
        localStorage.setItem('cart', JSON.stringify(shoppingCart))
        drawShoppingCart()
    } else {
        fetch("/shoppingCart/getUserShoppingCart")
            .then(res => res.json())
            .then(data => {
                if (data.length !== 0) {
                    data.forEach(
                        tmpCartItem => {
                            if (tmpCartItem.itemDto.id === id && tmpCartItem.quantity >= 2) {
                                tmpCartItem.quantity--
                                sendRequestForUpdateQuantityCartItem(tmpCartItem)
                            }
                        }
                    )
                }
            })
    }
}

function deletePositionFromCart(id) {
    if (localStorage.getItem('isAnonymous') === 'true') {
        let shoppingCart = JSON.parse(localStorage.getItem('cart'));
        let tmpCart = [];
        for (let i = 0; i < shoppingCart.length; i++) {
            if (parseInt(shoppingCart[i].itemDto.id) !== id) {
                tmpCart.push(shoppingCart[i])
            }
        }
        localStorage.setItem('cart', JSON.stringify(tmpCart))
        drawShoppingCart()
    } else {
        fetch("/shoppingCart/getUserShoppingCart")
            .then(res => res.json())
            .then(data => {
                if (data.length !== 0) {
                    data.forEach(
                        tmpCartItem => {
                            if (tmpCartItem.itemDto.id === id) {
                                sendRequestForDeletePositionFromCart(tmpCartItem)
                            }
                        }
                    )
                }
            })
    }
}

function drawShoppingCart() {
    if (localStorage.getItem('isAnonymous') === 'false') {
        fetch("/shoppingCart/getUserShoppingCart")
            .then(res => res.json())
            .then(data => {
                drawModalBody(data)
            })
    } else {
        drawModalBody(JSON.parse(localStorage.getItem('cart')))
    }
}

$('#staticBackdropBasket').on('show.bs.modal', function () {
    drawShoppingCart()
})

$('#staticBackdropBasket').on('hidden.bs.modal', function () {
    if (localStorage.getItem('isAnonymous') === 'false') {
        localStorage.removeItem('cart')
    }
})

if (userTag === null) {
    localStorage.setItem('isAnonymous', 'true')
} else {
    localStorage.setItem('isAnonymous', 'false')
}

if (localStorage.getItem('isAnonymous') === 'false') {
    loadUserShoppingCartFromServer()
}