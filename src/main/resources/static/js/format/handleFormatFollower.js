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
