const itemAddModal = $('#itemAddForm');
const itemEditModal = $('#itemEditForm');
const itemsTable = $('#itemsTable');

function clearAddItemForm() {
    itemAddModal.find('#itemNameAdd').val('');
    itemAddModal.find('#itemPriceAdd').val('');
    itemAddModal.find('#itemCategoryAdd').val('');
    itemAddModal.find('#itemCountAdd').val('');
    itemAddModal.find('#itemRatingAdd').val('');
    itemEditModal.find('#itemImagesAdd').val('');
    itemEditModal.find('#itemReviewsAdd').val('');
    itemAddModal.find('#itemDescriptionAdd').val('');
    itemAddModal.find('#itemDiscountAdd').val('');
}

function showAllItems() {
    fetch("/admin/api/items").then(
        res => {
            res.json().then(
                data => {
                    itemsTable.empty();
                    console.log(data);
                    if (data.length > 0) {
                        data.forEach((i) => {
                            addItemRow(i);
                        })
                    }
                }
            );
        }
    );
}

function addItemRow(item) {
    itemsTable
        .append($('<tr>').attr('id', 'itemRowId[' + item.id + ']')
            .append($('<td>').attr('id', 'itemData[' + item.id + '][id]').text(item.id))
            .append($('<td>').attr('id', 'itemData[' + item.id + '][name]').text(item.name))
            .append($('<td>').attr('id', 'itemData[' + item.id + '][price]').text(item.price))
            .append($('<td>').attr('id', 'itemData[' + item.id + '][categories]').text(item.categories.map(c => c.name)))
            .append($('<td>').attr('id', 'itemData[' + item.id + '][images]').text(item.images.map(c => c.id)))
            .append($('<td>').attr('id', 'itemData[' + item.id + '][reviews]').text(item.reviews.map(c => c.id)))
            .append($('<td>').attr('id', 'itemData[' + item.id + '][count]').text(item.count))
            .append($('<td>').attr('id', 'itemData[' + item.id + '][rating]').text(item.rating))
            .append($('<td>').attr('id', 'itemData[' + item.id + '][description]').text(item.description))
            .append($('<td>').attr('id', 'itemData[' + item.id + '][discount]').text(item.discount))
            .append($('<td>').append($('<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#itemEditForm">')
                .click(() => {
                    loadAndShowModalEditItemForm(item.id);
                }).text('Изменить')))
            .append($('<td>').append($('<button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">')
                .click(() => {
                    loadAndShowModalDeleteItemForm(item.id);
                }).text('Удалить')))
        );
}

function loadAndShowModalEditItemForm(id) {
    fetch('/admin/api/items/' + id, {method: 'GET'})
        .then(function (response) {
            response.json().then(function (item) {
                itemEditModal.find('#itemIdEdit').val(id);
                itemEditModal.find('#itemNameEdit').val(item.name);
                itemEditModal.find('#itemPriceEdit').val(item.price);
                itemEditModal.find('#itemCategoryEdit').val(item.categories.map(c => c.name));
                itemEditModal.find('#itemImagesEdit').val(item.images.id);
                itemEditModal.find('#itemReviewsEdit').val(item.reviews.id);
                itemEditModal.find('#itemCountEdit').val(item.count);
                itemEditModal.find('#itemRatingEdit').val(item.rating);
                itemAddModal.find('#itemDescriptionEdit').val(item.description);
                itemAddModal.find('#itemDiscountEdit').val(item.discount);
                itemEditModal.find(':submit').click(() => {
                    let item = {
                        'id': parseInt(itemEditModal.find('#itemIdEdit').val()),
                        'name': itemEditModal.find('#itemNameEdit').val(),
                        'price': itemEditModal.find('#itemPriceEdit').val(),
                        'categories': itemEditModal.find('#itemCategoryEdit').val(),
                        'count': itemEditModal.find('#itemCountEdit').val(),
                        'rating': itemEditModal.find('#itemRatingEdit').val()
                    };
                    let request = new Request('/admin/api/items/', {
                        method: 'PUT',
                        headers: {'content-type': 'application/json'},
                        body: JSON.stringify(item)
                    });
                    fetch(request)
                        .then(function () {
                            showAllItems();
                            itemEditModal.modal('hide');
                        });
                });
            })
        })
}

itemAddModal.find(':submit').click(() => {
    let item = {
        'name': itemAddModal.find('#itemNameAdd').val(),
        'price': itemAddModal.find('#itemPriceAdd').val(),
        'categories': itemEditModal.find('#itemCategoryAdd').val(),
        'images': itemEditModal.find('#itemImagesAdd').val(),
        'reviews': itemEditModal.find('#itemReviewsAdd').val(),
        'count': itemAddModal.find('#itemCountAdd').val(),
        'rating': itemAddModal.find('#itemRatingAdd').val(),
        'description': itemAddModal.find('#itemDescriptionAdd').val(),
        'discount': itemAddModal.find('#itemDiscountAdd').val()
    };
    let request = new Request('/admin/api/items/', {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify(item)
    });
    console.log(JSON.stringify(item))
    fetch(request).then(function () {
        showAllItems();
        clearAddItemForm();
        itemAddModal.modal('hide');
    });
});

$(document).ready(function () {
        showAllItems();
    }
);