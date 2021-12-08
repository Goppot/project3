const cityAddModal = $('#cityAddFormModal');
const cityEditModal = $('#cityEditFormModal');
const citiesTable = $('#cityTable');

function clearAddCityForm() {
    cityAddModal.find('#cityNameAdd').val('');
    cityAddModal.find('#countryNameAdd').val('');
}

function showAllCity() {
    fetch("/admin/api/cities").then(
        res => {
            res.json().then(
                data => {
                    citiesTable.empty();
                    if (data.length > 0) {
                        data.forEach((a) => {
                            addCitiesRow(a);
                        })
                    }
                }
            );
        }
    );
}

function addCitiesRow(address) {
    citiesTable
        .append($('<tr>').attr('id', 'cityRowId[' + address.id + ']')
            .append($('<td>').attr('id', 'cityData[' + address.id + '][id]').text(address.id))
            .append($('<td>').attr('id', 'cityData[' + address.id + '][name]').text(address.name))
            .append($('<td>').attr('id', 'cityData[' + address.id + '][country]').text(address.country.name))
            .append($('<td>').append($('<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#cityEditFormModal">')
                .click(() => {
                    loadAndShowModalEditCityForm(address.id);
                }).text('Изменить')))
            .append($('<td>').append($('<button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">')
                .click(() => {
                    loadAndShowModalDeleteCityForm(address.id);
                }).text('Удалить')))
        );
}

function loadAndShowModalEditCityForm(id) {
    fetch('/admin/api/cities/' + id, {method: 'GET'})
        .then(function (response) {
            response.json().then(function (city) {
                cityEditModal.find('#cityIdEdit').val(id);
                cityEditModal.find('#cityNameEdit').val(city.name);
                cityEditModal.find('#countryNameEdit').val(city.country.name);
                cityEditModal.find(':submit').click(() => {
                    let city = {
                        'id': parseInt(cityEditModal.find('#countryIdEdit').val()),
                        'name': cityEditModal.find('#countryNameEdit').val()
                    };
                    let request = new Request('/admin/api/countries/', {
                        method: 'PUT',
                        headers: {'content-type': 'application/json'},
                        body: JSON.stringify(city)
                    });
                    fetch(request)
                        .then(function () {
                            showAllCity();
                            cityEditModal.modal('hide');
                        });
                });
            })
        })
}

function loadAndShowModalDeleteCityForm(id) {
    deleteModal.find('.modal-title').text('Удаление города');
    deleteModal.find('#deleteId').text('Точно удалить город (' + id + ')? ');
    deleteModal.find('.submit').text('Удалить').removeClass('btn-primary').addClass('btn-danger')
        .removeAttr('onClick')
        .attr('onClick', 'deleteCity(' + id + ');');
}

cityAddModal.find(':button').click(() => {
    let city = {
        'name': cityAddModal.find('#cityNameAdd').val(),
        'country': cityAddModal.find('#countryNameAdd').val()
    }
    let request = new Request('/admin/api/cities/', {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify(city)
    })
    fetch(request).then(function () {
        showAllCity();
        clearAddCityForm();
        cityAddModal.modal('hide');
    });
});

$(document).ready(function () {
        showAllCity();
    }
);