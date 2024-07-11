document.addEventListener('DOMContentLoaded', function () {
    const followerCountSpan = document.getElementById('followerCount');
    let followerCount = parseInt(followerCountSpan.innerText);

    function formatFollowers(count) {
        if (count >= 1000000) {
            return (count / 1000000).toFixed(1) + 'M';
        } else if (count >= 1000) {
            return (count / 1000).toFixed(1) + 'k';
        } else {
            return count;
        }
    }

    followerCountSpan.innerText = formatFollowers(followerCount);
});
document.addEventListener('DOMContentLoaded', function () {
    const followerCountSpan = document.getElementById('followingCount');
    let followingCount = parseInt(followerCountSpan.innerText);

    function formatFollowers(count) {
        if (count >= 1000000) {
            return (count / 1000000).toFixed(1) + 'M';
        } else if (count >= 1000) {
            return (count / 1000).toFixed(1) + 'k';
        } else {
            return count;
        }
    }

    followerCountSpan.innerText = formatFollowers(followingCount);
});

const editDetailsSubmit = () => {
    document.getElementById('editDetails').submit()
}

document.getElementById('btn-edit').addEventListener('click', () => {
    const divShow = document.getElementById('div-show');
    const inputShow = document.getElementById('input-show');
    const btnEdit = document.getElementById('btn-edit');
    const divControl = document.getElementById('div-control');

    divShow.style.display = 'none';
    inputShow.style.display = '';
    btnEdit.style.display = 'none';
    divControl.style.display = 'flex'
})

document.getElementById('btn-cancel').addEventListener('click', () => {
    const divShow = document.getElementById('div-show');
    const inputShow = document.getElementById('input-show');
    const btnEdit = document.getElementById('btn-edit');
    const divControl = document.getElementById('div-control');

    divShow.style.display = 'block';
    inputShow.style.display = 'none';
    btnEdit.style.display = '';
    divControl.style.display = 'none'
})

document.getElementById('btn-save').addEventListener('click', () => {
    document.getElementById('updateBio').submit()
})