$(async function () {
    await getUnmoderatedReviews();
    await getUnmoderatedReviewsCountAndSetBadge();
})

const reviewUrl = "http://" + window.location.hostname + ":8888/moderator/api/reviews";
const reviewFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    getUnmoderatedReviews: async () => await fetch(`${reviewUrl}/getUnmoderatedReviews`),
    getOneUnmoderatedReview: async (id) => await fetch(`${reviewUrl}/getOneUnmoderatedReview/${id}`),
    updateReview: async (item) => await fetch(`${reviewUrl}/editReview`, {
        method: 'PUT',
        headers: reviewFetchService.head,
        body: JSON.stringify(item)
    }),
    getUnmoderatedReviewsCounter: async () => await fetch(`${reviewUrl}/getUnmoderatedReviewsCount`)
}

async function getUnmoderatedReviews() {
    let reviewCards = $('#reviewsCards')
    await reviewFetchService.getUnmoderatedReviews()
        .then(res => res.json())
        .then(reviews => {
            console.log(reviews);
                if (reviews.length === 0) {
                    reviewCards.append(`<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="emptyReviewList">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Отзывов для модерации нет!</h5>                        
                            </p>
                  
                        </div>
                    </div>
                </div>
                </div>
                </div>
                <br>`)
                } else {
                    reviews.forEach(review => {
                        let cardFilling =
                            `<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="reviewCard${review.id}">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Отзыв к: </b>${review.itemName} <b>из</b> ${review.shopName}</h5>
                       
                           <p class="card-text" id="cardText">Текст отзыва: <b>${review.text}</b> <br>
                           <b>Отзыв оставил: ${review.userFirstName} ${review.userLastName}<br>
                            <b>Рейтинг отзыва: </b>${review.rating}<br>
                            </p>
                        
                           <button data-reviewid="${review.id}" data-action="accept" class="btn btn-success">Одобрить</a>
                           <button data-reviewid="${review.id}" data-action="decline" class="btn btn-danger">Отклонить</button>
                       
                        </div> 
                      </div>
                </div>
                </div>
                </div>
                <br>`
                        reviewCards.append(cardFilling);
                    })
                    getUnmoderatedReviewsCountAndSetBadge();
                }
            }
        )
    $(`#reviewsCards`).find('button').on('click', (event) => {
        let targetButton = $(event.target);
        let buttonItemId = targetButton.attr('data-reviewid');
        let buttonAction = targetButton.attr('data-action');
        if (buttonAction === "accept") {
            acceptReview(buttonItemId);
        } else if (buttonAction === "decline") {
            declineReview(buttonItemId);
        }
    })
}

async function acceptReview(id) {
    await reviewFetchService.getOneUnmoderatedReview(id)
        .then(res => res.json())
        .then(async review => {
            review.moderated = true;
            review.moderateAccept = true;
            console.log(review)
            const response = await reviewFetchService.updateReview(review);
            if (response.ok) {
                let reviewsCards = $('#reviewsCards')
                reviewsCards.find(`#reviewCard${review.id}`).remove()
                await reviewFetchService.getUnmoderatedReviews()
                    .then(res => res.json())
                    .then(items => {
                        if (items.length === 0) {
                            reviewsCards.append(`<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="emptyReviewList">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Отзывов для модерации нет!</h5>                        
                            </p>                  
                        </div>
                    </div>
                </div>
                </div>
                </div>
                <br>`)
                        }
                    })
                await getUnmoderatedReviewsCountAndSetBadge();
            } else {
                console.log("Произошла ошибка")
            }
        })
}

async function declineReview(id) {
    let form;
    let cardField;
    if (!(document.getElementById(`declineReview${id}`))) {
        await reviewFetchService.getOneUnmoderatedReview(id)
            .then(res => res.json())
            .then(review => {
                    if (!(document.getElementById(`declineReview${review.id}`))) {
                        form = `<form id="declineReview${review.id}">
                    <input type="text" class="form-control" id="rejectedReasonToReview${review.id}" name="rejectedReasonToReview${review.id}" placeholder="Введите причину отказа" style="width: 200px; height: 150px;" 
                    min="5">
                    <button type="button" class="btn btn-primary" id="rejectReviewReason${review.id}">Отправить</button>\
                    </form>`
                        cardField = $("#reviewsCards").find(`#reviewCard${review.id}`);
                        cardField.append(form);
                        console.log("Created form")
                    }
                    $(`#declineReview${review.id}`).find('button').on('click', () => {
                        review.moderated = true;
                        review.moderateAccept = false;
                        review.moderatedRejectReason = document.querySelector(`#rejectedReasonToReview${review.id}`).value;
                        sendReviewAndDeleteCard(review);
                    })
                }
            )
    }
}

async function sendReviewAndDeleteCard(review) {
    const response = await reviewFetchService.updateReview(review);
    if (response.ok) {
        let reviewsCards = $('#reviewsCards')
        console.log(reviewsCards);
        reviewsCards.find(`#reviewCard${review.id}`).remove()
        await reviewFetchService.getUnmoderatedReviews()
            .then(res => res.json())
            .then(reviews => {
                if (reviews.length === 0) {
                    reviewsCards.append(`<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="emptyReviewList">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Отзывов для модерации нет!</h5>                        
                            </p>                
                        </div>
                    </div>
                </div>
                </div>
                </div>
                <br>`)
                }
                getUnmoderatedReviewsCountAndSetBadge();
            })
    } else {
        console.log("Произошла ошибка")
    }
}

async function getUnmoderatedReviewsCountAndSetBadge() {
    let precounter = await reviewFetchService.getUnmoderatedReviewsCounter();
    let counter = precounter.json();
    counter.then(counter => {
        $("#v-pills-tab #v-pills-reviews-tab").html(`Отзывы <span
                        class="badge badge-secondary">${counter}</span>
                    <span class="sr-only">unmoderated reviews</span></a>`)
    })
}