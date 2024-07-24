//Form
const submitInfo = document.querySelector('#submit-info')
const submitChangeLogin = document.querySelector('#submit-change-login')

//Button
const containerChangePassword = document.querySelector('#container-password');
const btnShowChangePassword = document.querySelector('#btn-show-change-password');
const btnDeleteAccount = document.querySelector('#btn-delete-account');
const btnChangeInfo = document.querySelector('#btn-change-info')

//Input
const finalName = document.querySelector('#final-name')
const firstName = document.querySelector('#firstName');
const lastName = document.querySelector('#lastName');
const idInfo = document.querySelector('#id-info');
const pw = document.querySelector('#password');

const statusPw = document.querySelector('#status-pw');

let allowChangePassword = true;

const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);


stompClient.connect({}, () => {
})

function handleSubmitInfo() {
    const accountFullName = {
        id: idInfo.value,
        firstName: firstName.value,
        lastName: lastName.value
    };
    stompClient.send("/app/user.update-name", {}, JSON.stringify(accountFullName));
}


function updateFinalName() {
    finalName.textContent = `${lastName.value} ${firstName.value}`;
}

pw.addEventListener('input', () => {
    if (pw.value.length >= 6 && pw.value.length < 50) {
        statusPw.style.color = 'green'
        statusPw.textContent = 'Password is valid!'
        allowChangePassword = true;
    } else if (pw.value.length === 0) {
        statusPw.textContent = ''
        allowChangePassword = true;
    } else {
        statusPw.style.color = 'red';
        statusPw.textContent = 'Invalid password!';
        allowChangePassword = false;
    }
})
firstName.addEventListener('input', updateFinalName);
lastName.addEventListener('input', updateFinalName);
btnShowChangePassword.addEventListener('click', () => {
    if (containerChangePassword.style.display === 'none') {
        containerChangePassword.style.display = '';
        btnShowChangePassword.innerHTML = 'Hidden change password'
    } else {
        containerChangePassword.style.display = 'none';
        btnShowChangePassword.innerHTML = 'Show change password'
    }
}, true)

btnChangeInfo.addEventListener('click', () => {
    const confirm = window.confirm("Please log in again!")
    if (confirm && allowChangePassword) {
        submitChangeLogin.submit();
    }
})

submitInfo.addEventListener('submit', handleSubmitInfo, true);