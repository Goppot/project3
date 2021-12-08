const addressAddModal = $('#addressAddFormModal');
const addressEditModal = $('#addressEditFormModal');
const addressesTable = $('#addressTable');

function clearAddAddressForm() {
    addressAddModal.find('#addressIndexAdd').val('');
    addressAddModal.find('#addressCountryAdd').val('');
    addressAddModal.find('#addressCityAdd').val('');
    addressAddModal.find('#addressStreetAdd').val('');
    addressAddModal.find('#addressHouseAdd').val('');
}

function showAllAddresses() {
    fetch("/admin/api/addresses").then(
        res => {
            res.json().then(
                data => {
                    addressesTable.empty();
                    if (data.length > 0) {
                        data.forEach((a) => {
                            addAddressesRow(a);
                        })
                    }
                }
            );
        }
    );
}

function addAddressesRow(address) {
    addressesTable
        .append($('<tr>').attr('id', 'addressRowId[' + address.id + ']')
            .append($('<td>').attr('id', 'addressData[' + address.id + '][id]').text(address.id))
            .append($('<td>').attr('id', 'addressData[' + address.id + '][cityIndex]').text(address.cityIndex))
            .append($('<td>').attr('id', 'addressData[' + address.id + '][country]').text(address.country.name))
            .append($('<td>').attr('id', 'addressData[' + address.id + '][city]').text(address.city.name))
            .append($('<td>').attr('id', 'addressData[' + address.id + '][street]').text(address.street))
            .append($('<td>').attr('id', 'addressData[' + address.id + '][house]').text(address.house))
            .append($('<td>').append($('<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addressEditFormModal">')
                .click(() => {
                    loadAndShowModalEditAddressForm(address.id);
                }).text('Изменить')))
            .append($('<td>').append($('<button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">')
                .click(() => {
                    loadAndShowModalDeleteAddressForm(address.id);
                }).text('Удалить')))
        );
}

function loadAndShowModalDeleteItemForm(id) {
    deleteModal.find('.modal-title').text('Удаление продукта');
    deleteModal.find('#deleteId').text('Точно удалить продукт (' + id + ')? ');
    deleteModal.find('.submit').text('Удалить').removeClass('btn-primary').addClass('btn-danger')
        .removeAttr('onClick')
        .attr('onClick', 'deleteItem(' + id + ');');
}

function loadAndShowModalEditAddressForm(id) {
    fetch('/admin/api/addresses/' + id, {method: 'GET'})
        .then(function (response) {
            response.json().then(function (address) {
                addressEditModal.find('#addressIdEdit').val(id);
                addressEditModal.find('#addressIndexEdit').val(address.cityIndex);
                addressEditModal.find('#addressCountryEdit').val(address.country.name);
                addressEditModal.find('#addressCityEdit').val(address.city);
                addressEditModal.find('#addressStreetEdit').val(address.street);
                addressEditModal.find('#addressHouseEdit').val(address.house);
                addressEditModal.find(':submit').click(() => {
                    let address = {
                        'id': parseInt(addressEditModal.find('#addressIdEdit').val()),
                        'cityIndex': addressEditModal.find('#addressIndexEdit').val(),
                        'country': addressEditModal.find('#addressCountryEdit').val(),
                        'city': addressEditModal.find('#addressCityEdit').val(),
                        'street': addressEditModal.find('#addressStreetEdit').val(),
                        'house': addressEditModal.find('#addressHouseEdit').val()
                    }
                    let request = new Request('/admin/api/addresses/', {
                        method: 'PUT',
                        headers: {'content-type': 'application/json'},
                        body: JSON.stringify(address)
                    });
                    fetch(request)
                        .then(function () {
                            showAllAddresses();
                            addressEditModal.modal('hide');
                        });
                });
            })
        })
}

function loadAndShowModalDeleteAddressForm(id) {
    deleteModal.find('.modal-title').text('Удаление адреса');
    deleteModal.find('#deleteId').text('Точно удалить адрес (' + id + ')? ');
    deleteModal.find('.submit').text('Удалить').removeClass('btn-primary').addClass('btn-danger')
        .removeAttr('onClick')
        .attr('onClick', 'deleteAddress(' + id + ');');
}

addressAddModal.find(':button').click(() => {
    let address = {
        'cityIndex': addressAddModal.find('#addressIndexAdd').val(),
        'country': addressAddModal.find('#addressCountryAdd').val(),
        'city': addressAddModal.find('#addressCityAdd').val(),
        'street': addressAddModal.find('#addressStreetAdd').val(),
        'house': addressAddModal.find('#addressHouseAdd').val()
    }
    let request = new Request('/admin/api/addresses/', {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify(address)
    })
    fetch(request).then(function () {
        showAllAddresses();
        clearAddAddressForm();
        addressAddModal.modal('hide');
    });
});

$(document).ready(function () {
        showAllAddresses();
    }
);