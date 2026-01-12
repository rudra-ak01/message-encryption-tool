const API = "http://localhost:8080/api/crypto";

async function encryptMessage() {
    const message = document.getElementById("inputBox").value;

    const res = await fetch(`${API}/encrypt`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ message })
    });

    const cipher = await res.text();
    document.getElementById("outputBox").value = cipher;
}

async function decryptMessage() {
    const cipher = document.getElementById("outputBox").value;

    const res = await fetch(`${API}/decrypt`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ message: cipher })
    });

    const plain = await res.text();
    document.getElementById("inputBox").value = plain;
}
