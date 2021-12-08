// получаем модалки
const userReviewAddForm = $('#userReviewAddForm');
const reviewsModal = document.querySelector(".review-listing")


// добавляем отзыв о товаре от пользователя
async function userItemReview() {
    $(userReviewAddForm.find(':submit')).on('click', async () => {
        let imageInput = userReviewAddForm.find('#logoAdd')[0].files[0]
        let image
        if (imageInput !== undefined) {
            image = imageToBinary(imageInput)
        }
        let review = {
            dignity: userReviewAddForm.find('#reviewDignityAdd').val(),
            flaw: userReviewAddForm.find('#reviewFlawAdd').val(),
            text: userReviewAddForm.find('#reviewTextAdd').val(),
            rating: userReviewAddForm.find('#reviewRatingAdd').val(),
            itemName: itemName,
            logo: [{
                url: userReviewAddForm.find('#logoAdd').val(),
                picture: image,
                isMain: true
            }]
        }
        const response = await fetch("/api/review/item", {
            method: 'POST',
            body: JSON.stringify(review),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            },
        })
        if (response.ok) {
            userReviewAddForm.modal("hide")
            shop_or_item();
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.errors}
                            <button type="button" class="close" data-bs-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            userReviewAddForm.prepend(alert);
        }
    })

}

// добавляем отзыв о магазине от пользователя
function userShopReview() {
    $(userReviewAddForm.find(':submit')).on('click', async () => {
        let imageInput = userReviewAddForm.find('#logoAdd')[0].files[0]
        let image
        if (imageInput !== undefined) {
            image = imageToBinary(imageInput)
        }
        let review = {
            dignity: userReviewAddForm.find('#reviewDignityAdd').val(),
            flaw: userReviewAddForm.find('#reviewFlawAdd').val(),
            text: userReviewAddForm.find('#reviewTextAdd').val(),
            rating: userReviewAddForm.find('#reviewRatingAdd').val(),
            shopName: shopName,
            logo: [{
                url: userReviewAddForm.find('#logoAdd').val(),
                picture: image,
                isMain: true
            }]
        }

        const response = await fetch("/api/review/shop", {
            method: 'POST',
            body: JSON.stringify(review),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            },
        })
        if (response.ok) {
            userReviewAddForm.modal("hide")
            shop_or_item();
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.errors}
                            <button type="button" class="close" data-bs-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            userReviewAddForm.prepend(alert);
        }
    });

}

async function getItemReviews() {
    await fetch("/api/review/items")
        .then(res => res.json())
        .then(reviewItem => {
            let result = getReview(reviewItem, "itemName");
            if (result !== "") {
                reviewsModal.innerHTML = result;
            } else {
                reviewsModal.innerHTML = `<div>
                        <p>Отзывы отсутствуют</p>
                    </div>`;
            }
        });
}

async function getShopReviews() {
    await fetch("/api/review/shops")
        .then(res => res.json())
        .then(reviewShop => {
            let result = getReview(reviewShop, "shopName");
            if (result !== "") {
                reviewsModal.innerHTML = result;
            } else {
                reviewsModal.innerHTML = `<div>
                        <p>Отзывы отсутствуют</p>
                    </div>`;
            }
        });
}


function getReview(review, name) {
    let result = ""
    let temp = []
    let imageReview = []
    for (let i = 0; i < review.length; i++) {
        temp[i] = `
                            <tr>
                                <td>${review[i].id}</td>
                                <td>${review[i].userFirstName + " " + review[i].userLastName}</td>
                                <td>${review[i].date.substr(0,10)}</td>
                                <td>${review[i].text}</td>
                                <td>${review[i].dignity}</td>
                                <td>${review[i].flaw}</td>
                                <td>${review[i].rating}</td>
                      `;
        if (review[i].logo.length > 0) {
            console.log("yes")
            imageReview[i] = `<td><img src="data:image/png;base64,${review[i].logo[0].picture}" width="200" height="222" class="img-thumbnail"></td>
                            </tr>`;
        } else {
            console.log("addEmpty")
            imageReview[i] = `<td>Отсутсвует</td>
                            </tr>`;
        }
        if ((name === "shopName" && review[i].shopName === shopName) ||
            (name === "itemName" && review[i].itemName === itemName)) {
            result += temp[i] + imageReview[i];
        }
    }
    return result;
}

// convert image to byte array
function imageToBinary(image) {
    let reader = new FileReader();
    reader.readAsDataURL(image);
    reader.onloadend = function () {
        let data = (reader.result).split(',')[1];
        let binaryBlob = atob(data);
        localStorage.setItem("image", binaryBlob)
    }
    let imageBase64 = localStorage.getItem("image")
    return base64ToBinary(imageBase64)
}

function base64ToBinary(imageBase64) {
    let bytes = [];
    for (let i = 0; i < imageBase64.length; i++) {
        bytes.push(
            imageBase64.charCodeAt(i)
        );
    }
    return bytes
}


