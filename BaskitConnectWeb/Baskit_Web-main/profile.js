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

document.addEventListener("DOMContentLoaded", function () {
    const navLinks = document.querySelectorAll(".nav-link");
    const indicator = document.querySelector(".nav-indicator");

    function moveIndicator(link) {
        const linkRect = link.getBoundingClientRect();
        const navRect = link.closest("ul").getBoundingClientRect();

        indicator.style.top = `${link.offsetTop}px`;
        indicator.style.left = `${link.offsetLeft}px`;
        indicator.style.width = `${link.offsetWidth}px`;
    }

    navLinks.forEach(link => {
        link.addEventListener("click", function (event) {
            event.preventDefault();
            navLinks.forEach(l => l.classList.remove("active"));
            this.classList.add("active");
            moveIndicator(this);
        });
    });

    const activeLink = document.querySelector(".nav-link.active");
    if (activeLink) {
        moveIndicator(activeLink);
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const nav = document.querySelector("nav");
    const header = document.querySelector("header");
    const formContainer = document.getElementById("updateAccountForm");
    const updateBtn = document.getElementById("updateBtn");
    const adminLink = document.querySelector("ul li a[href='profile.html']");
    

    function toggleDarken(state) {
        if (state) {
            nav.classList.add("darken");
            header.classList.add("darken");
        } else {
            nav.classList.remove("darken");
            header.classList.remove("darken");
        }
    }

    if (window.location.pathname.includes("profile.html")) {
        formContainer.style.display = "flex";
        toggleDarken(true);
    }


    adminLink.addEventListener("click", function (event) {
        event.preventDefault();
        formContainer.style.display = "flex";
        toggleDarken(true); 
    });

    updateBtn.addEventListener("click", function (event) {
        event.preventDefault();
    
        const newUsername = document.getElementById("newUsername").value.trim();
        const newPassword = document.getElementById("newPassword").value.trim();
        const confirmPassword = document.getElementById("confirmPassword").value.trim();
    
        if (!newUsername && !newPassword) {
            alert("Please enter a new username or password.");
            return;
        }
    
        if (newPassword !== confirmPassword) {
            alert("Passwords do not match!");
            return;
        }
    
        alert("Account updated successfully!");
    
        formContainer.style.display = "none";
        toggleDarken(false);
    });
    


    window.addEventListener("click", function (event) {
        if (event.target === formContainer) {
            const newUsername = document.getElementById("newUsername").value.trim();
            const newPassword = document.getElementById("newPassword").value.trim();
            const confirmPassword = document.getElementById("confirmPassword").value.trim();

            if (!newUsername && !newPassword) {
                alert("Please update your username or password before closing.");
                return;
            }

            if (newPassword !== confirmPassword) {
                alert("Passwords do not match. Please correct them before closing.");
                return;
            }

            formContainer.style.display = "none";
            toggleDarken(false);
        }
    });

});

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".toggle-password").forEach(icon => {
        icon.addEventListener("click", function () {
            const targetId = this.getAttribute("data-target");
            const passwordField = document.getElementById(targetId);
            
            if (passwordField.type === "password") {
                passwordField.type = "text";
                this.classList.replace("fa-eye", "fa-eye-slash");
            } else {
                passwordField.type = "password";
                this.classList.replace("fa-eye-slash", "fa-eye");
            }
        });
    });
});



document.getElementById("logoutButton").addEventListener("click", function () {
    if (confirm("Are you sure you want to log out?")) {
        sessionStorage.clear();
        localStorage.clear();
        window.location.href = "login.html";
    }
});
