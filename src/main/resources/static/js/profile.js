document.getElementById('formFile').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('avatarImage').src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
});

const uploadImageProfile = async (image) => {
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

document.getElementById('save-avatar').addEventListener('click', async function() {
    const button = document.getElementById('save-avatar');
    const saveText = document.getElementById('save-text');
    const saveLoader = document.getElementById('save-loader');

    saveText.classList.add('visually-hidden');
    saveLoader.classList.remove('d-none');


    const fileInput = document.getElementById('formFile');
    const file = fileInput.files[0];
    if (file) {
        try {
            const imageUrl = await uploadImageProfile(file);
            console.log('Uploaded image URL:', imageUrl);
            document.getElementById('avatarImage').src = imageUrl;
            document.getElementById('avatarInput').value = imageUrl;
            document.getElementById('submit-avatar').submit()
        } catch (error) {
            console.error('Error uploading image:', error);
        }
    }
});

