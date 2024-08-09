const forgotBtn = document.querySelector('#forgot-btn');
const forgotContainer = document.querySelector('#forgot-container');
const forgotSend = document.querySelector('#forgot-send');
const forgotEmail = document.querySelector('#forgot-email');
const containerBox = document.querySelector('#container-box')
const spinnerBox = document.querySelector('#spinner-box')

forgotBtn.addEventListener('click', () => {
    forgotContainer.style.display = forgotContainer.style.display === 'none' ? '' : 'none';
})

forgotSend.addEventListener('click', async () => {
    fetch(`http://facelink-webapp.ap-southeast-2.elasticbeanstalk.com/accuracy/isExists?email=${forgotEmail.value.trim()}`)
        .then(res => res.json())
        .then(data => {
            if (data.isLocked) {
                alert("Email is locked")
            } else if (data.isEnabled) {
                alert("Email is enabled")
            } else {
                commitUpdate(data)
            }
        }).catch(_ => {
        alert("Email invalid!")
    })
})

const commitUpdate = async (data) => {
    containerBox.style.display = 'none'
    spinnerBox.style.display = ''
    const resPost = await fetch(`http://facelink-webapp.ap-southeast-2.elasticbeanstalk.com/accuracy/response-password/${data.email}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    if (resPost.status === 200) {
        alert("Please enter password in email")
    } else {
        alert("Please try again tomorrow!")
    }
    location.reload();
}