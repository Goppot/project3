function userOrders() {
    let id = $('.user-id').val();
    console.log(id)
    let temp = "";
    fetch(`/getOrders/${id}`)
        .then(res => res.json())
        .then(orders => {
            orders.forEach((o) => {
                temp += `
             <tr>
                <td>${o.id}</td>
                <td>${o.date}</td>
                <td>${o.status}</td>
                <td>${o.total}</td>
             <tr>`;
                document.querySelector("#userOrders").innerHTML = temp;
            })
        })
}

userOrders();
