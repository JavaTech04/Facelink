const inputSearch = document.querySelector('#input-search');
const suggestions = document.querySelector('#suggestions');
let debounceTimeout;

const addListItem = (text, imageUrl, href) => {
    const ul = document.querySelector('#suggestions .list-group');
    const li = document.createElement('li');
    li.className = 'list-group-item';
    const img = document.createElement('img');
    img.className = 'avt-search';
    img.src = imageUrl;
    const boldText = document.createElement('b');
    boldText.textContent = text;
    li.appendChild(img);
    li.appendChild(boldText);
    li.addEventListener('click', function () {
        window.location.href = href;
    });
    ul.appendChild(li);
}

function fetchResults(query) {
    fetch(`http://facelink-webapp.ap-southeast-2.elasticbeanstalk.com/api/search?query=${query}`)
        .then(response => response.json())
        .then(data => {
            displayResults(data);
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}

function displayResults(data) {
    const ul = document.querySelector('#suggestions .list-group');
    ul.innerHTML = ''; // Clear previous results

    data.forEach(item => {
        var avt = item.avatar ? item.avatar :'https://i0.wp.com/www.vidyadhirajamvk.org/wp-content/uploads/2022/08/venugopal.png?fit=436%2C534&ssl=1';
        addListItem(item.fullName, avt, `/profile/${item.id}`);
    });

    suggestions.style.display = ''; // Show suggestions
}

inputSearch.addEventListener('input', () => {
    clearTimeout(debounceTimeout);
    if (inputSearch.value.length < 1) {
        suggestions.style.display = 'none'
    } else {
        debounceTimeout = setTimeout(() => {
            fetchResults(inputSearch.value);
        }, 300);
    }
})