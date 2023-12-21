const userPassword = document.querySelector('.password');
const confirmPassword = document.querySelector('.confirmPassword');
const name = document.querySelector('.name');
const phone = document.querySelector('.phone');
const message = document.getElementById('message');

function checkPwMatch() {
    if (userPassword.value !== confirmPassword.value) {
        message.textContent = "입력한 정보가 일치하지 않습니다.";
    } else {
        message.textContent = "입력한 정보가 일치합니다.";
    }
}

confirmPassword.addEventListener('input', checkPwMatch);

document.getElementById('registrationForm').addEventListener('submit', function(event) {
    let idValue = document.querySelector('.id').value;
    let pwValue = userPassword.value;
    let confirmPwValue = confirmPassword.value;
    let nameValue = name.value;
    let phoneValue = phone.value;

    if (!idValue || !pwValue || !confirmPwValue || !nameValue || !phoneValue) {
        event.preventDefault(); // 기본 제출 동작을 막습니다.
        alert('필수 값이 누락되었습니다. 확인 후 다시 시도해주세요.')
    }
    if (userPassword.value !== confirmPassword.value) {
        event.preventDefault();
        alert('입력한 정보가 일치하지 않습니다.')
    }
});

