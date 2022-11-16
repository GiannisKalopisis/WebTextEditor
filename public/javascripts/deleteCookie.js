// ONLY beforeunload, to work refresh properly

// window.onbeforeunload = function(event) {
//     const response =  fetch('/deleteCookie', {
//         method: 'POST',
//         headers: {
//             'Accept': '',
//             'Content-Type': 'text/javascript'
//         },
//         body: JSON.stringify({title : "@title"})
//     });
//     console.log(response);
//     return false;
// }

// window.addEventListener("beforeunload", function (event) {
//     const response =  fetch('/deleteCookie', {
//         method: 'POST',
//         headers: {
//             'Accept': '',
//             'Content-Type': 'text/javascript'
//         },
//         body: JSON.stringify({title : "@title"})
//     });
//     console.log(response)
// });