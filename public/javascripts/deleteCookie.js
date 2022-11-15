// ONLY beforeunload, to work refresh properly

window.addEventListener('beforeunload', function () {
    const response =  fetch('/deleteCookie', {
        method: 'POST',
        headers: {
            'Accept': '',
            'Content-Type': ''
        },
        body: JSON.stringify({title : "@title"})
    });
    console.log(response)
});