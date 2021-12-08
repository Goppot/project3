function userItems() {
    let id = $('.user-id').val();
    console.log(id)
    let temp = "";
    fetch(`/getUserSalesItems/${id}`)
        .then(res => res.json())
        .then(items => {
            items.forEach((i) => {
                temp += `
             <tr>
                <td>${i.id}</td>
                <td>${i.name}</td>
                <td>${i.count}</td>
                <td>${i.price}</td>
             <tr>`;
                document.querySelector("#userItems").innerHTML = temp;
            })
        })
}

userItems();
