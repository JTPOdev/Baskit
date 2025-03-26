document.addEventListener("DOMContentLoaded", () => {
    const navLinks = document.querySelectorAll("ul li a");
    const currentPage = window.location.pathname.split("/").pop(); 

    navLinks.forEach(link => {
        if (link.getAttribute("href") === currentPage) {
            link.classList.add("active");
        } else {
            link.classList.remove("active");
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const heading = document.querySelector(".header-left h1");

    heading.classList.add("active"); 

    heading.addEventListener("click", function () {
        this.classList.add("active"); 
    });
});


document.addEventListener("DOMContentLoaded", function () {
    const nav = document.querySelector("nav");

    setTimeout(() => {
        nav.classList.add("show");
    }, 100);
});

document.addEventListener("DOMContentLoaded", async function () {
    const formContainer = document.getElementById("updateAccountForm");
    const usernameField = document.getElementById("newUsername");
    const passwordField = document.getElementById("newPassword");
    const confirmPasswordField = document.getElementById("confirmPassword");
    const toggleNewPassword = document.getElementById("toggleNewPassword");
    const toggleConfirmPassword = document.getElementById("toggleConfirmPassword");
    const updateBtn = document.getElementById("updateBtn");
    const adminLink = document.querySelector("ul li a[href='admin.html']");
    const logoutButton = document.getElementById("logoutButton");



    function togglePasswordVisibility(passwordField, toggleButton) {
        const isPasswordVisible = passwordField.type === "password";
        passwordField.type = isPasswordVisible ? "text" : "password";
        toggleButton.textContent = isPasswordVisible ? "HIDE" : "SHOW";
    }

    toggleNewPassword.addEventListener("click", function () {
        togglePasswordVisibility(passwordField, toggleNewPassword);
    });
    
    toggleConfirmPassword.addEventListener("click", function () {
        togglePasswordVisibility(confirmPasswordField, toggleConfirmPassword);
    });

    async function fetchAdminProfile() {
        try {
            const token = localStorage.getItem("access_token");
            const response = await fetch("http://192.168.100.111:8000/admin/profile", {
                method: "GET",
                headers: { "Authorization": `Bearer ${token}` }
            });
    
            const data = await response.json();
    
            if (response.ok) {
                const usernameDisplay = document.getElementById("usernameDisplay");
                const passwordDisplay = document.getElementById("passwordDisplay");
                
                if (usernameDisplay && passwordDisplay) {
                    usernameDisplay.textContent = data.username;
                    passwordDisplay.textContent = "********";
                } else {
                    console.error("Profile display elements are missing in HTML.");
                }
    
                isUpdated = data.has_updated === "Updated";
    
                if (isUpdated) {
                    formContainer.style.display = "none";
                    toggleDarken(false);
                } else {
                    formContainer.style.display = "flex";
                    toggleDarken(true);
                }
            } else {
                alert(data.message || "Failed to fetch profile.");
            }
        } catch (error) {
            console.error("Error fetching profile:", error);
        }
    }
    
    document.addEventListener("DOMContentLoaded", fetchAdminProfile);

    if (window.location.pathname.includes("admin.html")) {
        fetchAdminProfile();
    }

    adminLink.addEventListener("click", function (event) {
        event.preventDefault();
        fetchAdminProfile();
    });

    updateBtn.addEventListener("click", async function (event) {
        event.preventDefault();
    
        if (isUpdated) {
            alert("Your account is already updated and cannot be changed again.");
            return;
        }
    
        const newUsername = usernameField.value.trim();
        const newPassword = passwordField.value.trim();
        const confirmPassword = confirmPasswordField.value.trim();
    
        if (!newUsername || !newPassword) {
            alert("Please enter a new username and password.");
            return;
        }
    
        if (newPassword !== confirmPassword) {
            alert("Passwords do not match!");
            return;
        }
    
        try {
            const token = localStorage.getItem("access_token");
            const response = await fetch("http://192.168.100.111:8000/admin/changeCredentials", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify({
                    new_username: newUsername,
                    new_password: newPassword
                })
            });
    
            const data = await response.json();
    
            if (response.ok) {
                alert("Credentials updated successfully!");
                isUpdated = true;
                formContainer.style.display = "none";
                toggleDarken(false);
            } else {
                alert(data.message || "Failed to update credentials.");
            }
        } catch (error) {
            console.error("Error updating credentials:", error);
        }
    });

    const navLinks = document.querySelectorAll("ul li a");
    const currentPage = window.location.pathname.split("/").pop();
    navLinks.forEach(link => {
        if (link.getAttribute("href") === currentPage) {
            link.classList.add("active");
        } else {
            link.classList.remove("active");
        }
    });

    const heading = document.querySelector(".header-left h1");
    heading.classList.add("active");
    heading.addEventListener("click", function () {
        this.classList.add("active");
    });

    const nav = document.querySelector("nav");
    setTimeout(() => {
        nav.classList.add("show");
    }, 100);

    document.addEventListener("DOMContentLoaded", function () {
        const navLinks = document.querySelectorAll("ul li a");
        const navIndicator = document.querySelector(".nav-indicator");
    
        function moveIndicator(link) {
            if (!navIndicator) return;
            navIndicator.style.top = `${link.offsetTop}px`;
            navIndicator.style.left = `${link.offsetLeft}px`;
            navIndicator.style.width = `${link.offsetWidth}px`;
        }
    
        function setActiveLink() {
            const currentPage = window.location.pathname.split("/").pop();
            navLinks.forEach(link => {
                if (link.getAttribute("href") === currentPage) {
                    link.classList.add("active");
                    moveIndicator(link);
                } else {
                    link.classList.remove("active");
                }
            });
        }
    
        navLinks.forEach(link => {
            link.addEventListener("click", function (event) {
                if (!this.href.includes("#")) event.preventDefault();
                navLinks.forEach(l => l.classList.remove("active"));
                this.classList.add("active");
                moveIndicator(this);
                window.location.href = this.href;
            });
        });
        setActiveLink();
    });

    const header = document.querySelector("header");
    function toggleDarken(state) {
        if (state) {
            nav.classList.add("darken");
            header.classList.add("darken");
        } else {
            nav.classList.remove("darken");
            header.classList.remove("darken");
        }
    }

    if (window.location.pathname.includes("admin.html")) {
        fetchAdminProfile();
    }

    adminLink.addEventListener("click", function (event) {
        event.preventDefault();
        formContainer.style.display = "flex";
        toggleDarken(true); 
    });

    window.addEventListener("click", function (event) {
        if (event.target === formContainer) {
            if (!usernameField.value.trim() && !passwordField.value.trim()) {
                alert("Please update your username or password before closing.");
                return;
            }
            if (passwordField.value.trim() !== confirmPasswordField.value.trim()) {
                alert("Passwords do not match. Please correct them before closing.");
                return;
            }
            formContainer.style.display = "none";
            toggleDarken(false);
        }
    });
});