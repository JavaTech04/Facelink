function scrollCarousel(direction) {
    const carousel = document.getElementById('carouselInner');
    const scrollAmount = 160; // Adjust the scroll amount as needed (150px + 10px margin)
    if (direction === -1) {
        carousel.scrollBy({ left: -scrollAmount, behavior: 'smooth' });
    } else if (direction === 1) {
        carousel.scrollBy({ left: scrollAmount, behavior: 'smooth' });
    }
}