const userService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    registerNewUser: async (user) => await fetch('/registration', {
        method: 'POST',
        headers: userService.head,
        body: JSON.stringify(user)
    }),
}
$(document).ready(function () {

    $('#registrationButtonModal').on('click', function () {
        $('#staticBackdrop').slideUp()
        $('.registrationForm').slideDown()
    })
    $('.btn-close').on('click', function () {
        $('.registrationForm').slideUp()
        $('#staticBackdrop').slideDown()
    })
    $('.registrationButton').on('click', async function (event) {
        let imageInput = $('.avatarImage')[0].files[0]
        let image
        if (imageInput !== undefined) {
            image = imageToBinary(imageInput)
        }
        let user = {
            email: $('.registrationForm .email').val(),
            username: $('.registrationForm .username').val(),
            password: $('.registrationForm .password').val(),
            phone: $('.registrationForm .phone').val(),
            firstName: $('.registrationForm .firstName').val(),
            lastName: $('.registrationForm .lastName').val(),
            gender: $('.registrationForm input[name="gender"]:checked').val(),
            age: $('.registrationForm .age').val(),
            birthday: $('.registrationForm .birthday').val(),
            images: {
                picture: image,
                isMain: true
            }
        }
        console.log(user)
        userVerification(user)
    })


})

//check user to exists in BD and validation user fields
async function userVerification(user) {
    $('.alert').hide("slide")
    let response = await userService.registerNewUser(user)
    if (response.ok) {
        $('#successfulRegistration').show('slide')
    } else {
        let responseBody = await response.json().then(result => result)
        console.log(responseBody)
        if (responseBody.isExist !== undefined) {
            let massage = responseBody.isExist;
            let alert = $('#isExistAlert')
            alert.empty()
            alert.append(massage)
            alert.show('slide')
        } else {
            responseBody = responseBody.errors
            for (let i in user) {
                let inputAlert = $('#' + i.toString() + 'Alert')
                if (responseBody[i] !== undefined) {
                    let alertMassage = `<small> ${responseBody[i]} </small>`
                    inputAlert.empty()
                    inputAlert.append(alertMassage)
                    inputAlert.show('slide')
                }
            }
        }
    }
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

//form-login
function login_btn_click(){
    $(document).on("click", ".login-btn", function (){
        document.querySelector(".login-content").innerHTML = form_login;
        $(".login-form").show();
    })
}
login_btn_click();

//form-registration
function registration_click(){
    $(document).on("click", ".input-button-registration", function (){
        document.body.style.overflow = "hidden";
        document.querySelector(".login-content").innerHTML = form_registration;
    })
}
registration_click();

function close_btn_click(){
    $(document).on("click", ".btn-close", function (){
        $(".login-form").hide();
        document.body.style.overflow = "visible";
    })
    $(document).on("click", ".login-form", function (e){
        if($(e.target).hasClass("login-form")){
            $(".login-form").hide();
            document.body.style.overflow = "visible";
        }
    })
}
close_btn_click();

//form login
// language=HTML
let form_login = `<div class="modal-dialog">
                    <div class="modal-content">
                        <div style="text-align: right">
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-header">
                            <div class="image-header">
                                  <img src="https://covr.org/wp-content/uploads/Amazon-swoop-145x100.jpg"
                                       alt="https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/172/69ae45600772983179fe0d89b3f3cc1b_crop.jpeg">
                              </div>
                        </div>
          
                        <div class="modal-body">
                            <form method="post" action="/index">
                                <div class="mb-3">
                                    <label for="SignInUsername" class="form-label">Имя пользователя</label>
                                    <input type="text" placeholder="Username" class="form-control"
                                           id="SignInUsername" name="username">
                                    <div id="emailHelp" class="form-text"></div>
                                </div>
                                <div class="mb-3">
                                    <label for="SignInPassword" class="form-label">Пароль</label>
                                    <input type="password" placeholder="Password" class="form-control"
                                           id="SignInPassword" name="password">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="flexCheckDefault"
                                               name="remember-me">
                                        <label class="form-check-label" for="flexCheckDefault">
                                            Запомнить пароль
                                        </label>
                                    </div>
                                    <div class="button-in">
                                        <button type="submit" class="btn btn-warning">Войти</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <a id="registrationButtonModal" class="input-button-registration" href="#">Зарегистрироваться</a>
                        </div>
                    </div>
                  </div>`

//form registration
let form_registration = `<!--<div class="modal registrationForm">-->
<!--    <div class=" mx-auto col-lg-4">-->
        <form class="p-4 p-md-5 border rounded-3 bg-light" id="newUserForm">
            <div style="text-align: right">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="form-floating my-3" id="email">
                <input type="email" class="form-control email" name="email">
                <label for="email">Email address</label>
                <div class="alert alert-danger collapse" role="alert" id="emailAlert">
                </div>
            </div>
            <div class="form-floating mb-3" id="username">
                <input class="form-control username" name="username">
                <label for="username">User Name</label>
                <div class="alert alert-danger collapse" role="alert" id="usernameAlert">
                </div>
            </div>
            <div class="form-floating mb-3">
                <input class="form-control firstName" id="firstName" name="firstName">
                <label for="firstName">First Name</label>
                <div class="alert alert-danger collapse" role="alert" id="firstNameAlert">
                </div>
            </div>
            <div class="form-floating mb-3">
                <input class="form-control lastName" id="lastName" name="lastName">
                <label for="lastName">Last Name</label>
                <div class="alert alert-danger collapse" role="alert" id="lastNameAlert">
                </div>
            </div>
            <div class="form-floating mb-3">
                <input type="password" class="form-control password" id="password" name="password">
                <label for="password">Password</label>
                <div class="alert alert-danger collapse" role="alert" id="passwordAlert">
                </div>
            </div>
            <div class="form-floating mb-2">
                <input type="tel" class="form-control phone" id="phone" value="+7" name="phoneNumber">
                <label for="phone">Phone Number</label>
                <div class="alert alert-danger collapse" role="alert" id="phoneAlert">
                </div>
            </div>
            <h6 class="mb-2 pb-1 gender-label">Gender: </h6>
            <div class="my-3" id="gender-check">
                <div class="form-check">
                    <input id="male" name="gender" type="radio" class="form-check-input" required="" value="MALE"
                           checked>
                    <label class="form-check-label" for="male">Male</label>
                </div>
                <div class="form-check">
                    <input id="female" name="gender" type="radio" class="form-check-input" required=""
                           value="FEMALE">
                    <label class="form-check-label" for="female">Female</label>
                </div>
                <div class="form-check">
                    <input id="UNKNOWN" name="gender" type="radio" class="form-check-input" required=""
                           value="UNKNOWN">
                    <label class="form-check-label" for="UNKNOWN">Other</label>
                </div>
            </div>
            <div class="form-floating mb-2">
                <input type="number" class="form-control age" id="age" name="age">
                <label for="age">Age</label>
                <div class="alert alert-danger collapse" role="alert" id="ageAlert">
                </div>
            </div>

            <div class="form-floating mb-3">
                <input type="date" class="form-control birthday" id="birthday" name="birthday">
                <label for="birthday">Date of birth:</label>
                <div class="alert alert-danger collapse" role="alert" id="birthdayAlert">
                </div>
            </div>
            <div class="mb-3">
                <label for="avatar" class="form-label">User avatar</label>
                <input class="form-control avatarImage" accept="image/*" type="file" id="avatar" name="avatar">
            </div>
            <button class="w-100 btn btn-lg btn-warning registrationButton" type="button" id="registrationButton"
                    onclick="">
                Регистрация
            </button>
            <div class="alert alert-danger collapse" role="alert" id="isExistAlert">
            </div>
            <div class="alert alert-success collapse" role="alert" id="successfulRegistration">
                User have been registered
            </div>

        </form>
<!--    </div>-->
<!--</div>-->`



