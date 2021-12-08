const shopsAddModal = $('#shopsAddFormModal');
const shopEditModal = $('#shopEditModal');
const shopsTable = $('#shopsTable');

function clearAddShopForm() {
    shopsAddModal.find('#shopNameAdd').val('');
    shopsAddModal.find('#shopEmailAdd').val('');
    shopsAddModal.find('#phoneNumberAdd').val('');
    shopsAddModal.find('#descriptionAdd').val('');
    shopsAddModal.find('#locationAdd').val('');
    shopsAddModal.find('#logoAdd').val('');
    shopsAddModal.find('#ratingAdd').val('');
    shopsAddModal.find('#ownerNameAdd').val('');
}

function showAllShops() {
    fetch("/admin/api/shops").then(
        res => {
            res.json().then(
                data => {
                    shopsTable.empty();
                    if (data.length > 0) {
                        data.forEach((s) => {
                            addShopRow(s);
                        })
                    }
                }
            );
        }
    );
}

function addShopRow(shop) {
    shopsTable
        .append($('<tr>').attr('id', 'shopRowId[' + shop.id + ']')
            .append($('<td>').attr('id', 'shopData[' + shop.id + '][id]').text(shop.id))
            .append($('<td>').attr('id', 'shopData[' + shop.id + '][name]').text(shop.name))
            .append($('<td>').attr('id', 'shopData[' + shop.id + '][email]').text(shop.email))
            .append($('<td>').attr('id', 'shopData[' + shop.id + '][phone]').text(shop.phone))
            .append($('<td>').attr('id', 'shopData[' + shop.id + '][description]').text(shop.description))
            .append($('<td>').attr('id', 'shopData[' + shop.id + '][location]').text(shop.location.name))
            .append($('<td>').attr('id', 'shopData[' + shop.id + '][logo]').text(shop.logo))
            .append($('<td>').attr('id', 'shopData[' + shop.id + '][rating]').text(shop.rating))
            .append($('<td>').attr('id', 'shopData[' + shop.id + '][user]').text(shop.user))
            .append($('<td>').append($('<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#userEditModal">')
                .click(() => {
                    loadAndShowModalAllShopsForm(shop.id);
                }).text('Открыть список')))
            .append($('<td>').append($('<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#shopEditModal">')
                .click(() => {
                    loadAndShowModalEditShopForm(shop.id);
                }).text('Изменить')))
            .append($('<td>').append($('<button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">')
                .click(() => {
                    loadAndShowModalDeleteShopForm(shop.id);
                }).text('Удалить')))
        );
}

function loadAndShowModalEditShopForm(id) {
    fetch('/admin/api/shops/' + id, {method: 'GET'})
        .then(function (response) {
            response.json().then(function (shop) {
                shopEditModal.find('#shopIdEdit').val(id);
                shopEditModal.find('#shopNameEdit').val(shop.name);
                shopEditModal.find('#shopEmailEdit').val(shop.email);
                shopEditModal.find('#shopPhoneEdit').val(shop.phone);
                shopEditModal.find('#shopDescriptionEdit').val(shop.description);
                shopEditModal.find('#shopAddressEdit').val(shop.location.name);
                // shopEditModal.find('#shopLogoEdit').val(shop.logo);
                shopEditModal.find('#shopRatingEdit').val(shop.rating);
                shopEditModal.find('#shopOwnerEdit').val(shop.user);
                shopEditModal.find(':submit').click(() => {
                    let shop = {
                        'id': parseInt(shopEditModal.find('#userIdEdit').val()),
                        'name': shopEditModal.find('#shopNameEdit').val(),
                        'email': shopEditModal.find('#shopEmailEdit').val(),
                        'phone': shopEditModal.find('#shopPhoneEdit').val(),
                        //  'logo': shopEditModal.find('#shopLogoEdit').val(),
                        'rating': shopEditModal.find('#shopRatingEdit').val(),
                        'description': shopEditModal.find('#shopDescriptionEdit').val()
                    };
                    let request = new Request('/admin/api/shops/', {
                        method: 'PUT',
                        headers: {'content-type': 'application/json'},
                        body: JSON.stringify(shop)
                    });
                    fetch(request)
                        .then(function () {
                            showAllShops();
                            shopEditModal.modal('hide');
                        });
                });
            })
        })
}

function loadAndShowModalDeleteShopForm(id) {
    deleteModal.find('.modal-title').text('Удаление магазина');
    deleteModal.find('#deleteId').text('Точно удалить магазин (' + id + ')? ');
    deleteModal.find('.submit').text('Удалить').removeClass('btn-primary').addClass('btn-danger')
        .removeAttr('onClick')
        .attr('onClick', 'deleteShop(' + id + ');');
}

shopsAddModal.find(':submit').click(() => {
    let txt;
    txt = {"id": 1, "name": "Russia", "hibernateLazyInitializer": {}}
    let shop = {
        'name': shopsAddModal.find('#shopNameAdd').val(),
        'email': shopsAddModal.find('#shopEmailAdd').val(),
        'phone': shopsAddModal.find('#phoneNumberAdd').val(),
        'description': shopsAddModal.find('#descriptionAdd').val(),
         'logo': shopsAddModal.find('#logoAdd').val(),
        'rating': shopsAddModal.find('#ratingAdd').val(),
        'location': txt
    };
    let request = new Request('/admin/api/shops/', {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify(shop)
    });
    fetch(request)
        .then(function (response) {
            showAllShops();
            clearAddShopForm();
            shopsAddModal.modal('hide');
        });
});

$(document).ready(function () {
        showAllShops();
    }
);