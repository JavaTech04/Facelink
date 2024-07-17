document.getElementById('uploadContainer').addEventListener('click', function () {
    document.getElementById('fileInput').click();
});
document.getElementById('fileInput').addEventListener('change', function (event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const displayImage = document.getElementById('displayImage');
            const displayVideo = document.getElementById('displayVideo');
            const deleteImage = document.getElementById('deleteImage');
            const deleteVideo = document.getElementById('deleteVideo');

            if (file.type.startsWith('image/')) {
                displayImage.src = e.target.result;
                displayImage.style.display = 'block';
                deleteImage.style.display = 'inline-block';
                displayVideo.style.display = 'none';
                deleteVideo.style.display = 'none';
            } else if (file.type.startsWith('video/')) {
                displayVideo.src = e.target.result;
                displayVideo.style.display = 'block';
                deleteVideo.style.display = 'inline-block';
                displayImage.style.display = 'none';
                deleteImage.style.display = 'none';
            }
        };
        reader.readAsDataURL(file);
    }
});
document.getElementById('deleteImage').addEventListener('click', function () {
    const displayImage = document.getElementById('displayImage');
    displayImage.src = '';
    displayImage.style.display = 'none';
    document.getElementById('fileInput').value = '';
    document.getElementById('deleteImage').style.display = 'none';
});

document.getElementById('deleteVideo').addEventListener('click', function () {
    const displayVideo = document.getElementById('displayVideo');
    displayVideo.src = '';
    displayVideo.style.display = 'none';
    document.getElementById('fileInput').value = '';
    document.getElementById('deleteVideo').style.display = 'none';
});
const uploadMedia = async (file) => {
    try {
        const formData = new FormData();
        formData.append("file", file);
        formData.append("upload_preset", "qh49wlsn");
        formData.append("cloud_name", "dswqplrdx");

        let uploadUrl = "https://api.cloudinary.com/v1_1/dswqplrdx/";
        if (file.type.startsWith('image/')) {
            uploadUrl += "image/upload";
        } else if (file.type.startsWith('video/')) {
            uploadUrl += "video/upload";
        }

        const response = await axios.post(uploadUrl, formData);
        return response.data.secure_url;
    } catch (error) {
        console.error("Error uploading media:", error);
        throw error;
    }
};

document.getElementById('btn-post').addEventListener('click', async () => {
    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];
    if (file) {
        try {
            const mediaUrl = await uploadMedia(file);
            console.log('Uploaded media URL:', mediaUrl);
            if (file.type.startsWith('image/')) {
                document.getElementById('imageInput').value = mediaUrl;
            } else if (file.type.startsWith('video/')) {
                document.getElementById('videoInput').value = mediaUrl;
            }
            document.getElementById('post-new').submit();
        } catch (error) {
            alert("Error uploading media");
            console.error('Error uploading media:', error);
        }
    } else {
        document.getElementById('post-new').submit();
    }
});