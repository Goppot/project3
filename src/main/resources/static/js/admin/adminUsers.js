const usersAddModal = $('#userAddFormModal');
const userEditModal = $('#userEditModal')
const usersTable = $('#usersTable');

function clearAddUserForm() {
    usersAddModal.find('#userEmailAdd').val('');
    usersAddModal.find('#userNameAdd').val('');
    usersAddModal.find('#userPasswordAdd').val('');
    usersAddModal.find('#phoneNumberAdd').val('');
    usersAddModal.find('#userFirstNameAdd').val('');
    usersAddModal.find('#userLastNameAdd').val('');
    usersAddModal.find('#userAgeAdd').val('');
    usersAddModal.find('#userAddressAdd').val('');
    usersAddModal.find('#userGenderAdd').val('');
    usersAddModal.find('#userBirthdayAdd').val('');
    usersAddModal.find('#userRolesAdd').val('');
    usersAddModal.find('#userImageAdd').val('');
    usersAddModal.find('#userCouponAdd').val('');
    usersAddModal.find('#userCartAdd').val('');
    usersAddModal.find('#userOrdersAdd').val('');
    usersAddModal.find('#userReviewsAdd').val('');
    usersAddModal.find('#userShopAdd').val('');
    usersAddModal.find('#userFavoriteAdd').val('');
    usersAddModal.find('#userDiscountAdd').val('');
}

function showAllUsers() {
    fetch("/admin/api/users").then(
        res => {
            res.json().then(
                data => {
                    usersTable.empty();
                    if (data.length > 0) {
                        data.forEach((u) => {
                            addUserRow(u);
                        })
                    }
                }
            );
        }
    );
}

function addUserRow(user) {
    usersTable
        .append($('<tr>').attr('id', 'userRowId[' + user.id + ']')
            .append($('<td>').attr('id', 'userData[' + user.id + '][id]').text(user.id))
            .append($('<td>').attr('id', 'userData[' + user.id + '][email]').text(user.email))
            .append($('<td>').attr('id', 'userData[' + user.id + '][username]').text(user.username))
            .append($('<td>').attr('id', 'userData[' + user.id + '][password]').text(user.password))
            .append($('<td>').attr('id', 'userData[' + user.id + '][activationCode]').text(user.activationCode))
            .append($('<td>').attr('id', 'userData[' + user.id + '][phone]').text(user.phone))
            .append($('<td>').attr('id', 'userData[' + user.id + '][firstName]').text(user.firstName))
            .append($('<td>').attr('id', 'userData[' + user.id + '][lastName]').text(user.lastName))
            .append($('<td>').attr('id', 'userData[' + user.id + '][age]').text(user.age))
            .append($('<td>').attr('id', 'userData[' + user.id + '][address]').text(user.address))
            .append($('<td>').attr('id', 'userData[' + user.id + '][roles]').text(user.roles.map(role => role.name)))
            .append($('<td>').attr('id', 'userData[' + user.id + '][gender]').text(user.gender))
            .append($('<td>').attr('id', 'userData[' + user.id + '][birthday]').text(user.birthday))
            .append($('<td>').attr('id', 'userData[' + user.id + '][images]').text(user.images))
            .append($('<td>').attr('id', 'userData[' + user.id + '][coupons]').text(user.coupons))
            .append($('<td>').attr('id', 'userData[' + user.id + '][cart]').text(user.cart))
            .append($('<td>').attr('id', 'userData[' + user.id + '][orders]').text(user.orders))
            .append($('<td>').attr('id', 'userData[' + user.id + '][reviews]').text(user.reviews))
            .append($('<td>').attr('id', 'userData[' + user.id + '][shops]').text(user.shops))
            .append($('<td>').attr('id', 'userData[' + user.id + '][favorite]').text(user.favorite))
            .append($('<td>').attr('id', 'userData[' + user.id + '][discounts]').text(user.discounts))
            .append($('<td>').append($('<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#userEditModal">')
                .click(() => {
                    loadAndShowModalEditUserForm(user.id);
                }).text('Изменить')))
            .append($('<td>').append($('<button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">')
                .click(() => {
                    loadAndShowModalDeleteUserForm(user.id);
                }).text('Удалить')))
        );
}

function loadAndShowModalEditUserForm(id) {
    fetch('/admin/api/users/' + id, {method: 'GET'})
        .then(function (response) {
            response.json().then(function (user) {
                userEditModal.find('#userIdEdit').val(id),
                    userEditModal.find('#userEmailEdit').val(user.email),
                    userEditModal.find('#userNameEdit').val(user.username),
                    userEditModal.find('#userPasswordEdit').val(user.password),
                    userEditModal.find('#userActiveCodeAdd').val(user.activationCode),
                    userEditModal.find('#phoneNumberEdit').val(user.phone),
                    userEditModal.find('#userFirstNameEdit').val(user.firstName),
                    userEditModal.find('#userLastNameEdit').val(user.lastName),
                    userEditModal.find('#userAgeEdit').val(user.age),
                    userEditModal.find('#userAddressEdit').val(user.address),
                    userEditModal.find('#userGenderEdit').val(user.gender),
                    userEditModal.find('#userBirthdayEdit').val(user.birthday),
                    userEditModal.find('#userRolesEdit').val(user.roles.map(role => role.name));
                userEditModal.find('#userImageEdit').val(user.images.id),
                    userEditModal.find('#userCouponEdit').val(user.coupons.map(coupon => coupon.id)),
                    userEditModal.find('#userCartEdit').val(user.cart.map(cart => cart.id)),
                    userEditModal.find('#userOrdersEdit').val(user.orders.map(order => order.id)),
                    userEditModal.find('#userReviewsEdit').val(user.reviews.map(review => review.id)),
                    userEditModal.find('#userShopEdit').val(user.shops.map(shop => shop.id)),
                    userEditModal.find('#userFavoriteEdit').val(user.favorite),
                    userEditModal.find('#userDiscountEdit').val(user.discounts)
                userEditModal.find(':submit').click(() => {
                    let user = {
                        'id': parseInt(userEditModal.find('#userIdEdit').val()),
                        'email': userEditModal.find('#userEmailEdit').val(),
                        'username': userEditModal.find('#userNameEdit').val(),
                        'password': userEditModal.find('#userPasswordEdit').val(),
                        'activationCode': userEditModal.find('#userActiveCodeEdit').val(),
                        'phone': userEditModal.find('#phoneNumberEdit').val(),
                        'firstName': userEditModal.find('#userFirstNameEdit').val(),
                        'lastName': userEditModal.find('#userLastNameEdit').val(),
                        'age': userEditModal.find('#userAgeEdit').val(),
                        'address': userEditModal.find('#userAddressEdit').val(),
                        'gender': userEditModal.find('#userGenderEdit').val(),
                        'birthday': userEditModal.find('#userBirthdayEdit').val(),
                        'roles': userEditModal.find('#userRolesEdit').val(),
                        'images': userEditModal.find('#userImageEdit').val(),
                        'coupons': userEditModal.find('#userCouponEdit').val(),
                        'cart': userEditModal.find('#userCartEdit').val(),
                        'orders': userEditModal.find('#userOrdersEdit').val(),
                        'reviews': userEditModal.find('#userReviewsEdit').val(),
                        'shops': userEditModal.find('#userShopEdit').val(),
                        'favorite': userEditModal.find('#userFavoriteEdit').val(),
                        'discounts': userEditModal.find('#userDiscountEdit').val()
                    };
                    let request = new Request('/admin/api/users/', {
                        method: 'PUT',
                        headers: {'content-type': 'application/json'},
                        body: JSON.stringify(user)
                    });
                    fetch(request)
                        .then(function () {
                            showAllUsers();
                            userEditModal.modal('hide');
                        });
                });
            })
        })
}

function loadAndShowModalDeleteUserForm(id) {
    deleteModal.find('.modal-title').text('Удаление пользователя');
    deleteModal.find('#deleteId').text('Точно удалить пользователя (' + id + ')? ');
    deleteModal.find('.submit').text('Удалить').removeClass('btn-primary').addClass('btn-danger')
        .removeAttr('onClick')
        .attr('onClick', 'deleteUser(' + id + ');');
}

usersAddModal.find(':submit').click(() => {
    let user = {
        'email': usersAddModal.find('#userEmailAdd').val(),
        'username': usersAddModal.find('#userNameAdd').val(),
        'password': usersAddModal.find('#userPasswordAdd').val(),
        'activationCode': usersAddModal.find('#userActiveCodeAdd').val(),
        'phone': usersAddModal.find('#phoneNumberAdd').val(),
        'firstName': usersAddModal.find('#userFirstNameAdd').val(),
        'lastName': usersAddModal.find('#userLastNameAdd').val(),
        'age': usersAddModal.find('#userAgeAdd').val(),
        'address': usersAddModal.find('#userAddressAdd').val(),
        'gender': usersAddModal.find('#userGenderAdd').val(),
        'birthday': usersAddModal.find('#userBirthdayAdd').val(),
        'roles': usersAddModal.find('#userRolesAdd').val(),
        'images': usersAddModal.find('#userImageAdd').val(),
        'coupons': usersAddModal.find('#userCouponAdd').val(),
        'cart': usersAddModal.find('#userCartAdd').val(),
        'orders': usersAddModal.find('#userOrdersAdd').val(),
        'reviews': usersAddModal.find('#userReviewsAdd').val(),
        'shops': usersAddModal.find('#userShopAdd').val(),
        'favorite': usersAddModal.find('#userFavoriteAdd').val(),
        'discounts': usersAddModal.find('#userDiscountAdd').val()
    };
    let request = new Request('/admin/api/users/', {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify(user)
    });
    fetch(request).then(function (response) {
        showAllUsers();
        clearAddUserForm();
        usersAddModal.modal('hide');
    });
});

$(document).ready(function () {
        showAllUsers();
    }
);