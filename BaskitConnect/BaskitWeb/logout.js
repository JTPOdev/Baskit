import { BASE_URL } from "./config.js"; 

logoutButton.addEventListener("click", async function () {
    if (confirm("Are you sure you want to log out?")) {
        try {
            const token = localStorage.getItem("access_token");
            const response = await fetch(`http://${BASE_URL}/admin/logout`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                }
            });

            const data = await response.json();
            if (response.ok) {
                alert("Successfully logged out.");
            } else {
                alert(data.message || "Failed to log out.");
            }
        } catch (error) {
            console.error("Error logging out:", error);
        }

        localStorage.clear();
        sessionStorage.clear();
        window.location.href = "login.html";
    }
});
