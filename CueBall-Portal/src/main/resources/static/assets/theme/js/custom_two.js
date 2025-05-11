function setMultiSelectValue(r, l) {
    if (r && "null" !== r) {
        if ("string" == typeof r) {
            var e = r.split(",");
            console.log(e), $(l).val(e)
        } else Array.isArray(r) ? (console.log(r), $(l).val(r)) : console.error("Invalid input type. Expected string or array.");
        $(l).trigger("change")
    } else console.error("Input array is null or 'null'.")
}

const userId = getCookie('cbusr');

console.log(userId);

const staffId = userId;
console.log("Subscribing to: /topic/alerts/staff-" + staffId);
const socket = new SockJS('/CueBallPortal/task-alert');
const stompClient = Stomp.over(socket);
stompClient.debug = console.log;
stompClient.connect({}, () => {
    stompClient.subscribe(`/topic/alerts/staff-${staffId}`, (message) => {
        // alert(message.body);
        Swal.fire({
            position: "top-end",
            icon: "warning",
            width: 600,
            background: "rgb(255 232 162)",
            title: message.body,
            showConfirmButton: false,
            timer: 4000,
            showClass: {
                popup: `
      animate__animated
      animate__fadeInUp
      animate__faster
    `
            },
            hideClass: {
                popup: `
      animate__animated
      animate__fadeOutDown
      animate__faster
    `
            }
        });
    });
});


function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return null;
}

function deleteCookie(name) {
    document.cookie = `${name}=; Path=/; Expires=Thu, 01 Jan 1970 00:00:00 GMT`;
}


