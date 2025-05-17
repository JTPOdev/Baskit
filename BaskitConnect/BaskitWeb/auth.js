document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("access_token");

    if (!token) {
        console.warn("No token found, redirecting to login.");
        window.location.href = "login.html"; // Redirect if token is missing
    }
});