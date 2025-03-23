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
    const adminLink = document.querySelector("ul li a[href='admin.html']");
    const formContainer = document.getElementById("updateAccountForm");
    const updateBtn = document.getElementById("updateBtn");
    const cancelBtn = document.getElementById("cancelBtn");

    let formIsSubmitted = false;

    adminLink.addEventListener("click", function (event) {
        event.preventDefault();
        formContainer.style.display = "flex";
    });

    updateBtn.addEventListener("click", function (event) {
        event.preventDefault();

        const newUsername = document.getElementById("newUsername").value;
        const newPassword = document.getElementById("newPassword").value;
        const confirmPassword = document.getElementById("confirmPassword").value;

        if (newPassword !== confirmPassword) {
            alert("Passwords do not match!");
            return;
        }

        alert("Account updated successfully!");

        formIsSubmitted = true;

        formContainer.style.display = "none";

        if (formIsSubmitted) {
            window.location.href = "admin.html";
        }
    });

    cancelBtn.addEventListener("click", function (event) {
        event.preventDefault();
        formContainer.style.display = "none";
    });

    window.addEventListener("click", function (event) {
        if (event.target === formContainer) {
            formContainer.style.display = "none";
        }
    });
});

document.getElementById("logoutButton").addEventListener("click", function () {
    if (confirm("Are you sure you want to log out?")) {
        sessionStorage.clear();
        localStorage.clear();
        window.location.href = "login.html";
    }
});
