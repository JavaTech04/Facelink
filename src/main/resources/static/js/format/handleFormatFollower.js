document.addEventListener('DOMContentLoaded', function () {
    // const followerCountSpan = document.getElementById('followerCount');
    // const followingCountSpan = document.getElementById('followingCount');
    // let followerCount = parseInt(followerCountSpan.innerText);
    // let followingCount = parseInt(followerCountSpan.innerText);
    // followerCountSpan.innerText = formatFollowers(followerCount);
    // followingCountSpan.innerText = formatFollowers(followingCount);

    const followerCountSpan = document.getElementById('followerCount');
    if (followerCountSpan) {
        let followerCount = parseInt(followerCountSpan.innerText, 10);
        followerCountSpan.innerText = formatFollowers(followerCount);
    } else {
        console.warn('Element with ID "followerCount" not found.');
    }

    const followingCountSpan = document.getElementById('followingCount');
    if (followingCountSpan) {
        let followingCount = parseInt(followingCountSpan.innerText, 10);
        followingCountSpan.innerText = formatFollowers(followingCount);
    } else {
        console.warn('Element with ID "followingCount" not found.');
    }
});
function formatFollowers(count) {
    if (count >= 1000000) {
        return (count / 1000000).toFixed(1) + 'M';
    } else if (count >= 1000) {
        return (count / 1000).toFixed(1) + 'k';
    } else {
        return count;
    }
}