document.getElementById('btn-edit').addEventListener('click', () => {
    document.getElementById('div-content').style.display = 'none'
    document.getElementById('div-content-edit').style.display = ''
})
document.getElementById('btn-cancel').addEventListener('click', () => {
    document.getElementById('div-content').style.display = ''
    document.getElementById('div-content-edit').style.display = 'none'
})

document.getElementById('btn-save').addEventListener('click', () => {
    document.getElementById('submit').submit();
})

const commentSubmit = async () => {
    const postId = document.getElementById('p-id').textContent;
    const content = document.querySelector('#comment-content');
    const data = {
        message: content.value.trim(),
        postId: postId
    }
    if(data.message.length === 0){
        return false;
    }
    const response = await fetch("/api/send-comment",
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

const handleLike = async (type) => {
    const postId = document.getElementById('p-id').textContent;
    var url = `/api/like?idPost=${postId}&type=${type}`
    const response = await fetch(url, {method: 'POST'})
    if (response.status === 200){
        location.reload();
    }else {
        alert("Error!")
    }
}