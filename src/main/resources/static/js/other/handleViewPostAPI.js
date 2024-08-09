
const commentSubmit = async () => {
    const postId = document.getElementById('p-id').textContent;
    const content = document.querySelector('#comment-content');
    const data = {
        message: content.value.trim(),
        postId: postId
    }
    if (data.message.length === 0) {
        return false;
    }
    const response = await fetch("http://facelink-webapp.ap-southeast-2.elasticbeanstalk.com/api/send-comment",
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
    if (response.status === 200) {
        content.value = "";
        location.reload();
    }
}

const handleReations = async (type) => {
    const postId = document.getElementById('p-id').textContent;
    const url = `http://facelink-webapp.ap-southeast-2.elasticbeanstalk.com/api/like?idPost=${postId}&type=${type}`
    const response = await fetch(url, {method: 'POST'})
    if (response.status === 200) {
        location.reload();
    } else {
        alert("Error!")
    }
}