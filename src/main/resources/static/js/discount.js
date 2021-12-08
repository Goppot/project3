$(document).ready(function () {
    restartAllUser();
});

function createTableRow(u) {
    let date = new Date(u.birthday);
    return `<tr id="user_table_row">
        <td>${u.id}</td>
        <td><img src="data:image/png;base64,${u.images.picture}" width="80" height="80" alt=""></td>
        <td>${u.gender}</td>
        <td>${u.firstName}</td>
        <td>${u.lastName}</td>
        <td>${u.age}</td>
        <td>${u.phone}</td>
        <td>${u.email}</td>
        <td>${date.getDay() + " - " + date.getMonth() + " - " + date.getFullYear()}</td>
        <td>
        <a  href="/api/userlist/${u.id}" class="btn btn-info discBtn" >Добавить скидку</a>
        </td>
    </tr>`;
}

function restartAllUser() {
    let UserTableBody = $("#user_table_body")
    UserTableBody.children().remove();
    fetch("/api/userlist/all")
        .then((response) => {
            response.json().then(
                data => data.forEach(function (item) {
                    let TableRow = createTableRow(item);
                    UserTableBody.append(TableRow);
                }));
        }).catch(error => {
        console.log(error);
    });
}

let userJs = null;
    $(document).on('click', 'a.btn' ,function (event) {
    event.preventDefault()
    if ($(event.target).hasClass('discBtn')) {
        let href = $(event.target).attr("href");
        $(".discount #discountModal").modal();
        userJs = fetch(href, {
            method: 'GET'
        }).then(function (response) {
            response.json().then(function (user) {
                userJs = user;
            });
        });
    }

    if ($(event.target).hasClass('discountButton')) {
        if (!document.querySelector('.first').classList.contains('active')) {
            $('#percentageDiscount').val('');
        } else if (!document.querySelector('.second').classList.contains('active')) {
            $('#fixedDiscount').val('');
        }
        let shopId = $('#chooseShop').val()[0];

        fetch('/api/userlist/shop/' + shopId, {
            method: 'GET'
        }).then(function (response) {
            response.json().then(function (shop) {
                let discount = {
                    user: userJs,
                    shop: shop,
                    minOrder: $('#minOderDiscount').val(),
                    percentage: $('#percentageDiscount').val(),
                    fixedDiscount: $('#fixedDiscount').val()
                }
                discountModalButton(discount)
                // updateUser(discount);
            });
        });
    }
});

function discountModalButton(discount) {
    console.log(discount)
    fetch("/api/userlist/addDiscount", {
        method: "POST",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(discount)
    }).then(function () {
        $('.discount #discountModal').modal('hide');
        restartAllUser();
    });
}