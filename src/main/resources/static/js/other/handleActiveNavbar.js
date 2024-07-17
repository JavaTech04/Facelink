document.addEventListener('DOMContentLoaded', function () {
    const currentLocation = window.location.pathname;
    const menuItems = document.querySelectorAll('.nav-link');

    menuItems.forEach(item => {
        if (item.getAttribute('href') === currentLocation) {
            item.classList.add('active-custom');
        }
    });
});
