import { BASE_URL } from "./config.js";

document.addEventListener("DOMContentLoaded", () => {
    setupNavigation();
    setupHeader();
    setupNavIndicator();
    setupUserManagement();
    setupRegistration();
});

function setupNavigation() {
    const navLinks = document.querySelectorAll("ul li a");
    const currentPage = window.location.pathname.split("/").pop();

    navLinks.forEach(link => {
        link.classList.toggle("active", link.getAttribute("href") === currentPage);
    });
}

function setupHeader() {
    const heading = document.querySelector(".header-left h1");
    heading.classList.add("active");

    heading.addEventListener("click", () => {
        heading.classList.add("active");
    });

    const nav = document.querySelector("nav");
    setTimeout(() => {
        nav.classList.add("show");
    }, 100);
}

function setupNavIndicator() {
    const navLinks = document.querySelectorAll(".nav-link");
    const indicator = document.querySelector(".nav-indicator");

    function moveIndicator(link) {
        indicator.style.top = `${link.offsetTop}px`;
        indicator.style.left = `${link.offsetLeft}px`;
        indicator.style.width = `${link.offsetWidth}px`;
    }

    navLinks.forEach(link => {
        link.addEventListener("click", (event) => {
            event.preventDefault();
            navLinks.forEach(l => l.classList.remove("active"));
            link.classList.add("active");
            moveIndicator(link);
        });
    });

    const activeLink = document.querySelector(".nav-link.active");
    if (activeLink) moveIndicator(activeLink);
}
function setupRegistration() {
    const registrationForm = document.getElementById("registrationForm");
    const registerMessage = document.getElementById("registerMessage");

    registrationForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        
        const getInputValue = (id) => {
            const element = document.getElementById(id);
            return element ? element.value.trim() : "";
        };
    
        const password = getInputValue("password");
        const confirmPassword = getInputValue("confirmPassword");
    
        if (password !== confirmPassword) {
            registerMessage.style.color = "red";
            registerMessage.textContent = "Passwords do not match.";
            return;
        }
    
        const birthMonth = getInputValue("birth_month") || null;
        const birthDay = getInputValue("birth_day") || null;
        const birthYear = getInputValue("birth_year") || null;
        
        if (!birthMonth || !birthDay || !birthYear) {
            registerMessage.style.color = "red";
            registerMessage.textContent = "Please provide a valid birth date.";
            return;
        }
        
        if (!birthYear || !birthMonth || !birthDay) {
            registerMessage.style.color = "red";
            registerMessage.textContent = "Please provide a valid birth date.";
            return;
        }

        const userData = {
            firstname: getInputValue("firstname"),
            lastname: getInputValue("lastname"),
            username: getInputValue("username"),
            email: getInputValue("email"),
            mobile_number: getInputValue("mobile_number"),
            password: password,
            confirm_password: confirmPassword,
            birth_year: birthYear,
            birth_month: birthMonth,
            birth_day: birthDay,
            is_mobile: false
        };

        // Log the registration data
        console.log("Submitting registration with data:", userData);

        try {
            const response = await fetch(`${BASE_URL}/user/register`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(userData)
            });

            const responseText = await response.text();
            console.log("Server Response:", responseText);

            if (!response.ok) {
                console.error(`Failed request with status: ${response.status}`);
                registerMessage.style.color = "red";
                registerMessage.textContent = "Registration failed. Please try again.";
                return;
            }

            try {
                const result = JSON.parse(responseText);
                if (response.ok) {
                    registerMessage.style.color = "green";
                    registerMessage.textContent = result.message;
                    registrationForm.reset();
                } else {
                    registerMessage.style.color = "red";
                    registerMessage.textContent = result.message || "Registration failed.";
                    
                    if (result.errors) {
                        let errorText = Object.values(result.errors).join("\n");
                        alert(errorText);
                    }
                }
            } catch (jsonError) {
                console.error("Failed to parse JSON:", jsonError);
                registerMessage.style.color = "red";
                registerMessage.textContent = "An error occurred. Please try again.";
            }
        } catch (error) {
            console.error("Registration error:", error);
            registerMessage.style.color = "red";
            registerMessage.textContent = "An error occurred. Please try again.";
        }
    });
}



function setupUserManagement() {
    const userList = document.getElementById("userList");
    const filterSelect = document.getElementById("filterSelect");
    const headerRow = document.querySelector(".header-row");
    const openFormButton = document.getElementById("openFormButton");
    const closeFormButton = document.getElementById("closeFormButton");
    const userFormContainer = document.getElementById("userFormContainer");
    const userForm = document.getElementById("userForm");

    async function fetchUsers(filter) {
        userList.innerHTML = "";
        const endpoint = filter === "tagabili" ? "/user/tagabili" : "/store/request/all";

        try {
            const response = await fetch(`${BASE_URL}${endpoint}`);
            const data = await response.json();
            console.log("API Response:", data);
            let users = Array.isArray(data.users) ? data.users : (Array.isArray(data) ? data : []);
            if (filter !== "tagabili") {
                users = users.filter(user => (user.request_status || "").trim().toLowerCase() === "approved");
            }
            if (!users.length) {
                userList.innerHTML = `<p>No users found.</p>`;
                return;
            }
            displayUsers(users, filter);
        } catch (error) {
            console.error("Error fetching users:", error);
            userList.innerHTML = `<p>Error loading users.</p>`;
        }
    }

    function displayUsers(users, filter) {
        userList.innerHTML = "";

        if (filter === "tagabili") {
            headerRow.innerHTML = `
                <div>Name</div>
                <div>Username</div>
                <div>Mobile</div>
                <div>Email</div>
            `;
            headerRow.style.gridTemplateColumns = "repeat(4, 1fr)";

            users.forEach(user => {
                const userCard = document.createElement("div");
                userCard.classList.add("user-card");
                userCard.dataset.userId = user.id || user.username;

                userCard.innerHTML = `
                    <div>${user.firstname || user.name || "Unknown"} ${user.lastname || ""}</div>
                    <div>${user.username || ""}</div>
                    <div>${user.mobile || user.mobile_number || ""}</div>
                    <div>${user.email || ""}</div>
                `;
                userList.appendChild(userCard);
            });
        } else if (filter === "owner") {
            headerRow.innerHTML = `
                <div>Name</div>
                <div>Username</div>
                <div>Store Name</div>
                <div>Mobile</div>
                <div>Email</div>
            `;
            headerRow.style.gridTemplateColumns = "repeat(5, 1fr)";

            users.forEach(user => {
                const userCard = document.createElement("div");
                userCard.classList.add("user-card", "clickable");
                userCard.dataset.userId = user.id || user.username;

                userCard.innerHTML = `
                    <div>${user.name || `${user.firstname || "Unknown"} ${user.lastname || ""}`}</div>
                    <div>${user.username || ""}</div>
                    <div>${user.store || user.store_name || ""}</div>
                    <div>${user.mobile || user.mobile_number || ""}</div>
                    <div>${user.email || ""}</div>
                `;
                userCard.addEventListener("click", () => toggleUserDetails(userCard, user));
                userList.appendChild(userCard);
            });
        }
    }

    function toggleUserDetails(userCard, user) {
        console.log("User object:", user);
        console.log("Valid ID URL:", user.validId || user.valid_id || 'img/placeholder.jpg');
        console.log("Certificate URL:", user.certificate || user.certificate_of_registration || 'img/placeholder.jpg');

        let existingDetailsRow = document.querySelector(".details-row");
        if (existingDetailsRow) {
            if (existingDetailsRow.dataset.userId === userCard.dataset.userId) {
                existingDetailsRow.remove();
                return;
            }
            existingDetailsRow.remove();
        }

        let detailsRow = document.createElement("div");
        detailsRow.classList.add("details-row");
        detailsRow.dataset.userId = userCard.dataset.userId;
        detailsRow.innerHTML = `
            <div class="info-sections">
                <div class="info-container">
                    <div class="info-box">
                        <h2>STORE INFORMATION</h2>
                        <div><strong>Store Name:</strong> ${user.store || user.store_name || ""}</div>
                        <div><strong>Owner Name:</strong> ${user.name || user.owner_name || `${user.firstname || "Unknown"} ${user.lastname || ""}`}</div>
                        <div><strong>Contact Number:</strong> ${user.contact || user.mobile || user.store_phone_number || ""}</div>
                        <div><strong>Address:</strong> ${user.address || user.store_address || ""}</div>
                        <div><strong>Store Origin:</strong> ${user.origin || ""}</div>
                    </div>
                    <div class="info-box">
                        <h2>BUSINESS INFORMATION</h2>
                        <div><strong>Registered Store Name:</strong> ${user.registeredStoreName || user.registered_store_name || ""}</div>
                        <div><strong>Registered Store Address:</strong> ${user.registeredStoreAddress || user.registered_store_address || ""}</div>
                        <div><strong>Store Type:</strong> ${user.storeType || user.store_status || ""}</div>
                        <div class="documents">
                            <div class="document">
                                <p>Valid ID</p>
                                <img src="${user.validId || user.valid_id || 'img/placeholder.jpg'}" alt="Valid ID" class="clickable-image">
                            </div>
                            <div class="document">
                                <p>Certificate</p>
                                <img src="${user.certificate || user.certificate_of_registration || 'img/placeholder.jpg'}" alt="Certificate" class="clickable-image">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
        userCard.insertAdjacentElement("afterend", detailsRow);

        const images = detailsRow.querySelectorAll(".clickable-image");
        images.forEach(img => {
            img.style.cursor = "pointer";
            img.addEventListener("click", () => {
                console.log("Image clicked, src:", img.src);
                openFullScreenImage(img.src);
            });
        });
    }

    function openFullScreenImage(imageSrc) {
        const fullScreenDiv = document.createElement("div");
        fullScreenDiv.classList.add("full-screen-view");
        fullScreenDiv.innerHTML = `<img src="${imageSrc}" alt="Enlarged Image">`;
        document.body.appendChild(fullScreenDiv);
        fullScreenDiv.addEventListener("click", () => fullScreenDiv.remove());
    }

    openFormButton.addEventListener("click", function () {
        userFormContainer.style.display = "block";
    });

    closeFormButton.addEventListener("click", function () {
        userFormContainer.style.display = "none";
    });


    filterSelect.addEventListener("change", function () {
        openFormButton.style.display = (this.value === "tagabili") ? "block" : "none";
        if (this.value) {
            fetchUsers(this.value);
        } else {
            userList.innerHTML = "";
        }
    });

    openFormButton.style.display = "none";

    document.getElementById("logoutButton").addEventListener("click", function () {
        if (confirm("Are you sure you want to log out?")) {
            sessionStorage.clear();
            localStorage.clear();
            window.location.href = "login.html";
        }
    });

    userList.innerHTML = "";
}