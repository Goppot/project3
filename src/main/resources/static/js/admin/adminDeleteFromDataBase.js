const deleteModal = $('#deleteModal');

function deleteShop(id) {
    fetch('/admin/api/shops/' + id, {method: 'DELETE'})
        .then(function () {
            deleteModal.modal('hide');
            shopsTable.find('#shopRowId\\[' + id + '\\]').remove();
        });
}

function deleteUser(id) {
    fetch('/admin/api/users/' + id, {method: 'DELETE'})
        .then(function () {
            deleteModal.modal('hide');
            usersTable.find('#userRowId\\[' + id + '\\]').remove();
        });
}

function deleteItem(id) {
    fetch('/admin/api/items/' + id, {method: 'DELETE'})
        .then(function () {
            deleteModal.modal('hide');
            itemsTable.find('#itemRowId\\[' + id + '\\]').remove();
        });
}

function deleteCountry(id) {
    fetch('/admin/api/countries/' + id, {method: 'DELETE'})
        .then(function () {
            deleteModal.modal('hide');
            countriesTable.find('#countryRowId\\[' + id + '\\]').remove();
        });
}

function deleteCity(id) {
    fetch('/admin/api/cities/' + id, {method: 'DELETE'})
        .then(function () {
            deleteModal.modal('hide');
            citiesTable.find('#cityRowId\\[' + id + '\\]').remove();
        });
}

function deleteAddress(id) {
    fetch('/admin/api/addresses/' + id, {method: 'DELETE'})
        .then(function () {
            deleteModal.modal('hide');
            addressesTable.find('#addressRowId\\[' + id + '\\]').remove();
        });
}

function deleteCategory(id) {
    fetch('/admin/api/categories/' + id, {method: 'DELETE'})
        .then(function () {
            deleteModal.modal('hide');
            categoriesTable.find('#categoryRowId\\[' + id + '\\]').remove();
        });
}

$(document).ready(function () {} );