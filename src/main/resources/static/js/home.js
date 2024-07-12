document.getElementById('uploadContainer').addEventListener('click', function() {
    document.getElementById('fileInput').click();
});

document.getElementById('fileInput').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const displayImage = document.getElementById('displayImage');
            const displayVideo = document.getElementById('displayVideo');
            displayImage.src = e.target.result;
            displayImage.style.display = 'block';
            document.getElementById('deleteImage').style.display = 'inline-block';
        };
        reader.readAsDataURL(file);
    }
});

document.getElementById('deleteImage').addEventListener('click', function() {
    const displayImage = document.getElementById('displayImage');
    displayImage.src = '';
    displayImage.style.display = 'none';
    const fileInput = document.getElementById('fileInput');
    fileInput.value = '';
    document.getElementById('deleteImage').style.display = 'none';
});

const uploadImageHome = async (image) => {
    try {
        const formData = new FormData();
        formData.append("file", image);
        formData.append("upload_preset", "qh49wlsn");
        formData.append("cloud_name", "dswqplrdx");

        const response = await axios.post("https://api.cloudinary.com/v1_1/dswqplrdx/image/upload", formData);
        // const response = await axios.post("https://api.cloudinary.com/v1_1/dswqplrdx/video/upload", formData);

        return response.data.secure_url;
    } catch (error) {
        console.error("Error uploading image:", error);
        throw error;
    }
};

document.getElementById('btn-post').addEventListener('click', async () => {
    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];
    if (file) {
        try {
            const imageUrl = await uploadImageHome(file);
            console.log('Uploaded image URL:', imageUrl);
            document.getElementById('imageInput').value = imageUrl;
    document.getElementById('post-new').submit();
        } catch (error) {
            alert("Error")
            console.error('Error uploading image:', error);
        }
    }
})