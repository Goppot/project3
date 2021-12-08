async function getRating(ratingVal) {
    const ratings = document.querySelectorAll(".rating")
    // получаем рейтинг если больше 0
    if (ratings.length > 0) {
        console.log("rating > 0")
        initRatings();
    }

    function initRatings() {
        let ratingActive;
        let ratingValue;

        for (let i = 0; i < ratings.length; i++) {
            const rating = ratings[i];
            initRating(rating,ratingVal);
        }

        function initRating(rating,ratingVal) {
            initRatingVars(rating,ratingVal);
            setRatingActiveWidth();
        }

        function initRatingVars(rating, ratingVal) {
            ratingActive = rating.querySelector(".ratingActive")
            ratingValue = ratingVal ;
        }

        function setRatingActiveWidth(index = ratingValue) {
            const ratingActiveWidth = index / 0.05;
            ratingActive.style.width = `${ratingActiveWidth}%`;
        }
    }
}

async function getRatingHtml() {
    document.querySelector("#ratingShow").innerHTML =
        (`
                <style>
                .rating-div {
                    display: inline-block;
                }
                .rating {
                    font-size: 40px;
                    line-height: 0.75;
                    padding-bottom: 10px;
                }
            
                .ratingBody {
                    position: relative;
                    margin: auto;
                }
            
                .ratingBody::before {
                    content: "★★★★★";
                    display: block;
                }
            
                .ratingItems {
                    position:absolute;
                    width: 100%;
                    height: 100%;
                    top: 0;
                    left: 0;
                }
            
                .ratingItem {
                    flex: 0 0 20%;
                    height: 100%;
                    opacity: 0;
                }
            
                .ratingActive {
                    position: absolute;
                    width: 0%;
                    height: 100%;
                    top: 0;
                    left: 0;
                    overflow: hidden;
                }
            
                .ratingActive::before {
                    position: absolute;
                    content: "★★★★★";
                    width: 100%;
                    height: 100%;
                    top: 0;
                    left: 0;
                    color: #fdbf2f;
                }
            </style>
            <div class="rating rating-div">
                <div class="ratingBody">
                    <div class="ratingActive"></div>
                    <div class="ratingItems">
                        <input type="radio" class="ratingItem" value="1" name="rating">
                        <input type="radio" class="ratingItem" value="2" name="rating">
                        <input type="radio" class="ratingItem" value="3" name="rating">
                        <input type="radio" class="ratingItem" value="4" name="rating">
                        <input type="radio" class="ratingItem" value="5" name="rating">
                    </div>
                </div>
            </div>
    `);
}