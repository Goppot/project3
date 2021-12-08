class Favorite{
    constructor(item, shop) {
        this.id = null
        this.itemDto = item
        this.shopDto = shop
    }

    id
    itemDto
    shopDto

}

let favorite = document.querySelector('#favoriteList');
let userTags = document.querySelector('#userTag');

function drawModalBody(tmpCart) {
    favorite.innerHTML = '';
    if (tmpCart != null) {
        if(tmpCart.itemDto instanceof Array){
            for (let i = 0; i < tmpCart.itemDto.length; i++){
                favorite.innerHTML += `
            <tr>
                <td>${tmpCart.itemDto[i].name}</td>
                <td>&nbsp;</td>
                
               
                <td>${tmpCart.itemDto[i].price}</td>
                <td>&nbsp;</td>
               
                <td>
                <button type="button" class="btn btn-danger" onclick="deleteFavoriteItem(${tmpCart.itemDto[i].id})">Удалить позицию</button>
                </td>
            </tr>
        `;
            }
        } else {
            favorite.innerHTML += `
            <tr>
                <td>${tmpCart.itemDto.name}</td>
                <td>&nbsp;</td>
                
               
                <td>${tmpCart.itemDto.price}</td>
                <td>&nbsp;</td>
               
                <td>
                <button type="button" class="btn btn-danger" onclick="deleteFavoriteItem(${tmpCart.itemDto.id})">Удалить позицию</button>
                </td>
            </tr>
        `;
        }



    }
    favorite.innerHTML += `
        <tr>
            <td colspan="11">&nbsp;</td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          
        </tr>`;
}


async function deleteFavoriteItem(id){
   console.log("ffgfgfgf" + id);
    let url = new URL("http://localhost:8888/favorite/items/delete/"+id);
    console.log("after" + id);
    const response = await fetch(url, {

        headers: { "Content-Type": "application/json; charset=utf-8" },
        method: 'DELETE'
    })
    drawShoppingCart();
}

function drawShoppingCart() {
        fetch("/favorite/getFavoriteByUser")
            .then(res => res.json())
            .then(data => {
                drawModalBody(data)
            })

}

$('#staticBackdropFavorite').on('show.bs.modal', function () {
    drawShoppingCart()
})

$('#staticBackdropFavorite').on('hidden.bs.modal', function () {
    if (localStorage.getItem('isAnonymous') === 'false') {
        localStorage.removeItem('cart')
    }
})

if (userTags === null) {
    localStorage.setItem('isAnonymous', 'true')
} else {
    localStorage.setItem('isAnonymous', 'false')
}

if (localStorage.getItem('isAnonymous') === 'false') {
    loadUserShoppingCartFromServer()
}