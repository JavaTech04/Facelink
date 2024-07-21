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