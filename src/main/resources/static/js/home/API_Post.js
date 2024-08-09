document.querySelector('#video-link-button').addEventListener('click', () => {
    const box = document.querySelector('#container-video-link');
    box.style.display = box.style.display === '' ? 'none' : '';
})

function getYouTubeEmbedUrl(url){
    const youtubeRegex = /(?:youtube\.com\/(?:[^\/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([^"&?\/\s]{11})/;
    const match = url.match(youtubeRegex);
    if (match && match[1]) {
        return `https://www.youtube.com/embed/${match[1]}`;
    } else {
        return url;
    }
}
function isValidUrl(url) {
    try {
        new URL(url);
        return true;
    } catch (_) {
        return false;
    }
}
document.querySelector('#link-video-submit').addEventListener('click', async () => {
    const data = {
        content: document.querySelector('#link-video-content').value.trim(),
        link: getYouTubeEmbedUrl(document.querySelector('#link-video-link').value.trim())
    }
    if (!isValidUrl(data.link)) {
        alert('Invalid url!');
        return;
    }
    const response = await fetch('http://facelink-webapp.ap-southeast-2.elasticbeanstalk.com/api/post-link',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
    if(response.status === 200){
        const confirm = window.confirm("Do you want to reload the page?")
        if(confirm){
            location.reload();
        }
    }else{
        alert("Error")
    }
})