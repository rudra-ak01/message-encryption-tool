const API_URL = "http://localhost:8080/api/crypto";

function encrypt() {
    const input = document.getElementById("inputText").value;

    if(!input.trim()){
        showToast("Please enter a message!!", "error");
        return;
    }

    fetch(API_URL + "/encrypt", {
        method: "POST",
        headers: {
            "Content-Type": "text/plain"
        },
        body: input
    })
    .then(response => response.text())
    .then(data => {
        document.getElementById("outputText").value = data;
        showToast("Message encrypted successfully!", "success");
    })
    .catch(() => {
        showToast("Encryption failed!", "error");
    });
}

function decrypt() {
    const input = document.getElementById("inputText").value;

     if(!input.trim()){
        showToast("Please enter a message!!", "error");
        return;
     }

    fetch(API_URL + "/decrypt", {
        method: "POST",
        headers: {
            "Content-Type": "text/plain"
        },
        body: input
    })
    .then(response => response.text())
    .then(data => {
        document.getElementById("outputText").value = data;
        showToast("Message decrypted successfully!", "success");
    })
    .catch(() => {
        showToast("decryption failed!", "error");
    });
}
function showToast(message, type = "success") {
    const container = document.getElementById("toastContainer");

    const toast = document.createElement("div");
    toast.classList.add("toast", type);
    toast.textContent = message;

    container.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 3500);
}
