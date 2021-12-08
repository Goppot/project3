/**
 * Поиск магазинов по скидке
 */

$(document).ready(
    function findDiscountStores() {
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }

        let temp = '';
        fetch('api/discounts', {headers}).then(response => {
            return response.json().then(data => {
                data.forEach(
                    discount => {
                        temp += `
        <div className="card" style="width: 15rem;">
        <img src="data:image/png;base64,${discount.shop.logo.picture}" height="150" width="150">
            <div className="card-body">
                <h5 id="nameShop1" className="card-title">${discount.shop.name}</h5>
                <h6>Если Вы купите на <h6>${discount.minOrder}</h6> рублей, то получите единоразово скидку на товар в размере
                <h6>${discount.fixedDiscount}</h6>.</h6>
                <p className="card-text"></p>
                <a href="/shop/${shop.id}" class="btn btn-outline-warning">Страница магазина</a>
            </div>
        </div>`
                    })
                document.querySelector('#cardsPopularShops').innerHTML = temp;
            });

        })
    });