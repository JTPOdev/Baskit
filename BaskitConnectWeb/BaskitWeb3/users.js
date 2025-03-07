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
    const users = [
        { name: "sun sun sun", username: "sun3", store: "SuperSun", mobile: "09123456789", email: "sun@email.com", type: "owner" },
        { name: "sun sun sun", username: "sun3", store: "SuperSun", mobile: "09123456789", email: "sun@email.com", type: "owner" },
        { name: "beru ru ru", username: "beru", mobile: "09456789012", email: "beru@email.com", type: "tagabili" },
        { name: "beru ru ru", username: "beru", mobile: "09456789012", email: "beru@email.com", type: "tagabili" }
    ];

    const userList = document.getElementById("userList");
    const filterSelect = document.getElementById("filterSelect");
    const headerRow = document.querySelector(".header-row");

    function displayUsers(filter = "") {
        userList.innerHTML = "";
        if (!filter) {
            return;
        }

        let filteredUsers = users.filter(user => user.type === filter);

        if (filter === "owner") {
            headerRow.innerHTML = `
                <div>Name</div>
                <div>Username</div>
                <div>Store Name</div>
                <div>Mobile Number</div>
                <div>Email</div>
            `;
            headerRow.style.gridTemplateColumns = "repeat(5, 1fr)";
        } else {
            headerRow.innerHTML = `
                <div>Name</div>
                <div>Username</div>
                <div>Mobile Number</div>
                <div>Email</div>
            `;
            headerRow.style.gridTemplateColumns = "repeat(4, 1fr)";
        }

        filteredUsers.forEach(user => {
            const userCard = document.createElement("div");
            userCard.classList.add("user-card");

            if (user.type === "owner") {
                userCard.innerHTML = `
                    <div>${user.name}</div>
                    <div>${user.username}</div>
                    <div>${user.store}</div>
                    <div>${user.mobile}</div>
                    <div>${user.email}</div>
                `;
                userCard.style.gridTemplateColumns = "repeat(5, 1fr)";
            } else {
                userCard.innerHTML = `
                    <div>${user.name}</div>
                    <div>${user.username}</div>
                    <div>${user.mobile}</div>
                    <div>${user.email}</div>
                `;
                userCard.style.gridTemplateColumns = "repeat(4, 1fr)";
            }

            userList.appendChild(userCard);
        });
    }

    filterSelect.addEventListener("change", function () {
        displayUsers(this.value);
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const filterSelect = document.getElementById("filterSelect");
    const registerButtonContainer = document.getElementById("registerButtonContainer");

    function updateRegisterButton() {
        registerButtonContainer.innerHTML = "";
        if (filterSelect.value === "tagabili") {
            const registerButton = document.createElement("button");
            registerButton.textContent = "Register";
            registerButton.id = "registerButton";
            registerButton.classList.add("register-button");
            registerButtonContainer.appendChild(registerButton);
        }
    }

    filterSelect.addEventListener("change", updateRegisterButton);
});

document.getElementById("logoutButton").addEventListener("click", function () {
    if (confirm("Are you sure you want to log out?")) {
        sessionStorage.clear();
        localStorage.clear();
        window.location.href = "login.html";
    }
});
