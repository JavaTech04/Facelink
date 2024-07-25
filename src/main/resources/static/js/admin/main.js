const lockAccount = (id) => {
    window.location.href = `/admin/lockAccount/${id}?admin-page=true`;
}
const unlockAccount = (id) => {
    window.location.href = `/admin/unlockAccount/${id}?admin-page=true`;
}

const verifiedAccount = (id) => {
    window.location.href = `/admin/verifiedAccount/${id}`;
}
const unVerifiedAccount = (id) => {
    window.location.href = `/admin/unVerifiedAccount/${id}`;
}

const setAdmin = (id) => {
    window.location.href = `/admin/setAdmin/${id}`;
}
const removeAdmin = (id) => {
    window.location.href = `/admin/removeAdmin/${id}`;
}

const deleteAccount = (id) => {
    swal({
        title: "Are you sure?",
        text: "Your account will be permanently deleted!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
            if (willDelete) {
                window.location.href = `/admin/delete/${id}`;
            }
        });
}