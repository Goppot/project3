const countryAddModal = $('#countryAddFormModal');
const countryEditModal = $('#countryEditFormModal');
const countriesTable = $('#countryTable');

function clearAddCountryForm() {
    countryAddModal.find('#countryNameAdd').val('');
}

function showAllCountry() {
    fetch("/admin/api/countries").then(
        res => {
            res.json().then(
                data => {
                    countriesTable.empty();
                    if (data.length > 0) {
                        data.forEach((a) => {
                            addCountryRow(a);
                        })
                    }
                }
            );
        }
    );
}

function addCountryRow(address) {
    countriesTable
        .append($('<tr>').attr('id', 'countryRowId[' + address.id + ']')
            .append($('<td>').attr('id', 'countryData[' + address.id + '][id]').text(address.id))
            .append($('<td>').attr('id', 'countryData[' + address.id + '][name]').text(address.name))
            .append($('<td>').append($('<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#countryEditFormModal">')
                .click(() => {
                    loadAndShowModalEditCountryForm(address.id);
                }).text('Изменить')))
            .append($('<td>').append($('<button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">')
                .click(() => {
                    loadAndShowModalDeleteCountryForm(address.id);
                }).text('Удалить')))
        );
}

function loadAndShowModalEditCountryForm(id) {
    fetch('/admin/api/countries/' + id, {method: 'GET'})
        .then(function (response) {
            response.json().then(function (country) {
                countryEditModal.find('#countryIdEdit').val(id);
                countryEditModal.find('#countryNameEdit').val(country.name);
                countryEditModal.find(':submit').click(() => {
                    let country = {
                        'id': parseInt(countryEditModal.find('#countryIdEdit').val()),
                        'name': countryEditModal.find('#countryNameEdit').val()
                    };
                    let request = new Request('/admin/api/countries/', {
                        method: 'PUT',
                        headers: {'content-type': 'application/json'},
                        body: JSON.stringify(country)
                    });
                    fetch(request)
                        .then(function () {
                            showAllCountry();
                            countryEditModal.modal('hide');
                        });
                });
            })
        })
}

function loadAndShowModalDeleteCountryForm(id) {
    deleteModal.find('.modal-title').text('Удаление страны');
    deleteModal.find('#deleteId').text('Точно удалить страну (' + id + ')? ');
    deleteModal.find('.submit').text('Удалить').removeClass('btn-primary').addClass('btn-danger')
        .removeAttr('onClick')
        .attr('onClick', 'deleteCountry(' + id + ');');
}

countryAddModal.find(':button').click(() => {
    let country = {
        'name': countryAddModal.find('#countryNameAdd').val()
    }
    let request = new Request('/admin/api/countries/', {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify(country)
    })
    fetch(request).then(function () {
        showAllCountry();
        clearAddCountryForm();
        countryAddModal.modal('hide');
    });
});

$(document).ready(function () {
        showAllCountry();
    }
);