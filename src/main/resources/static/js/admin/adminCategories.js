const categoryAddModal = $('#categoryAddForm');
const categoryEditModal = $('#categoryEditForm');
const categoriesTable = $('#categoriesTable');

function clearAddCategoryForm() {
    categoryAddModal.find('#categoryNameAdd').val('');
}

function showAllCategories() {
    fetch("/admin/api/categories").then(
        res => {
            res.json().then(
                data => {
                    categoriesTable.empty();
                    console.log(data)
                    if (data.length > 0) {
                        data.forEach((c) => {
                            addCategoriesRow(c);
                        })
                    }
                }
            );
        }
    );
}

function addCategoriesRow(category) {
    categoriesTable
        .append($('<tr>').attr('id', 'categoryRowId[' + category.id + ']')
            .append($('<td>').attr('id', 'categoryData[' + category.id + '][id]').text(category.id))
            .append($('<td>').attr('id', 'categoryData[' + category.id + '][name]').text(category.name))
            .append($('<td>').append($('<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#categoryEditForm">')
                .click(() => {
                    loadAndShowModalEditCategoryForm(category.id);
                }).text('Изменить')))
            .append($('<td>').append($('<button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">')
                .click(() => {
                    loadAndShowModalDeleteCategoryForm(category.id);
                }).text('Удалить')))
        );
}

function loadAndShowModalEditCategoryForm(id) {
    fetch('/admin/api/categories/' + id, {method: 'GET'})
        .then(function (response) {
            response.json().then(function (category) {
                categoryEditModal.find('#categoryIdEdit').val(id);
                categoryEditModal.find('#categoryNameEdit').val(category.name);
                categoryEditModal.find(':submit').click(() => {
                    let category = {
                        'id': parseInt(categoryEditModal.find('#categoryIdEdit').val()),
                        'name': categoryEditModal.find('#categoryNameEdit').val()
                    };
                    let request = new Request('/admin/api/categories/', {
                        method: 'PUT',
                        headers: {'content-type': 'application/json'},
                        body: JSON.stringify(category)
                    });
                    fetch(request)
                        .then(function () {
                            showAllCategories();
                            categoryEditModal.modal('hide');
                        });
                });
            })
        })
}

function loadAndShowModalDeleteCategoryForm(id) {
    deleteModal.find('.modal-title').text('Удаление категории');
    deleteModal.find('#deleteId').text('Точно удалить категорию (' + id + ')? ');
    deleteModal.find('.submit').text('Удалить').removeClass('btn-primary').addClass('btn-danger')
        .removeAttr('onClick')
        .attr('onClick', 'deleteCategory(' + id + ');');
}

categoryAddModal.find(':submit').click(() => {
    let category = {
        'name': categoryAddModal.find('#categoryNameAdd').val()
    }
    let request = new Request('/admin/api/categories/', {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify(category)
    })
    fetch(request).then(function () {
        showAllCategories();
        clearAddCategoryForm();
        categoryAddModal.modal('hide');
    });
});

$(document).ready(function () {
        showAllCategories();
    }
);