const API_URL = "http://localhost:8080/api/crypto";

function encrypt() {
    const input = document.getElementById("inputText").value;

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
    })
    .catch(error => {
        alert("Encryption failed!");
        console.error(error);
    });
}

function decrypt() {
    const input = document.getElementById("inputText").value;

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
    })
    .catch(error => {
        alert("Decryption failed!");
        console.error(error);
    });
}
