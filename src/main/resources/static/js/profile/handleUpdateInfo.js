const editDetailsSubmit = () => {
    document.getElementById('editDetails').submit()
}

document.getElementById('btn-edit').addEventListener('click', () => {
    const divShow = document.getElementById('div-show');
    const inputShow = document.getElementById('input-show');
    const btnEdit = document.getElementById('btn-edit');
    const divControl = document.getElementById('div-control');

    divShow.style.display = 'none';
    inputShow.style.display = '';
    btnEdit.style.display = 'none';
    divControl.style.display = 'flex'
})

document.getElementById('btn-cancel').addEventListener('click', () => {
    const divShow = document.getElementById('div-show');
    const inputShow = document.getElementById('input-show');
    const btnEdit = document.getElementById('btn-edit');
    const divControl = document.getElementById('div-control');

    divShow.style.display = 'block';
    inputShow.style.display = 'none';
    btnEdit.style.display = '';
    divControl.style.display = 'none'
})

document.getElementById('btn-save').addEventListener('click', () => {
    document.getElementById('updateBio').submit()
})
