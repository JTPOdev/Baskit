import { BASE_URL } from "./config.js";

document.addEventListener("DOMContentLoaded", () => {
    setupNavigation();
    setupHeader();
    setupNavIndicator();
    setupUserManagement();
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

function setupUserManagement() {
    const userList = document.getElementById("userList");
    const filterSelect = document.getElementById("filterSelect");
    const headerRow = document.querySelector(".header-row");

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
        headerRow.innerHTML = `
            <div>Name</div>
            <div>Username</div>
            <div>Mobile Number</div>
            <div>Email</div>
        `;
        headerRow.style.gridTemplateColumns = "repeat(4, 1fr)";

        users.forEach(user => {
            console.log("User Data:", user);
            const userCard = document.createElement("div");
            userCard.classList.add("user-card");
            userCard.dataset.userId = user.id;

            userCard.innerHTML = `
                <div>${user.firstname || "Unknown"} ${user.lastname || ""}</div>
                <div>${user.username || "N/A"}</div>
                <div>${user.mobile_number || "N/A"}</div>
                <div>${user.email || user.user_email || user.owner_email || "No Email Provided"}</div>
            `;
            userCard.style.gridTemplateColumns = "repeat(4, 1fr)";

            if (filter !== "tagabili") {
                userCard.classList.add("clickable");
                userCard.addEventListener("click", () => toggleUserDetails(userCard, user));
            }

            userList.appendChild(userCard);
        });
    }

    function toggleUserDetails(userCard, user) {
        let existingDetailsRow = document.querySelector(".details-row");

        if (existingDetailsRow) {
            if (existingDetailsRow.dataset.userId === user.id.toString()) {
                existingDetailsRow.remove();
                return;
            }
            existingDetailsRow.remove();
        }

        let detailsRow = document.createElement("div");
        detailsRow.classList.add("details-row");
        detailsRow.dataset.userId = user.id;
        detailsRow.innerHTML = `
            <div class="info-sections">
                <div class="info-container">
                    <div class="info-box">
                        <h2>STORE INFORMATION</h2>
                        <div><strong>Store Name:</strong> ${user.store_name || "N/A"}</div>
                        <div><strong>Owner:</strong> ${user.owner_name || "N/A"}</div>
                        <div><strong>Contact:</strong> ${user.store_phone_number || "N/A"}</div>
                        <div><strong>Address:</strong> ${user.store_address || "N/A"}</div>
                    </div>
                    <div class="info-box">
                        <h2>BUSINESS INFORMATION</h2>
                        <div><strong>Registered Store Name:</strong> ${user.registered_store_name || "N/A"}</div>
                        <div><strong>Registered Store Address:</strong> ${user.registered_store_address || "N/A"}</div>
                        <div><strong>Store Type:</strong> ${user.store_status || "N/A"}</div>
                        <div class="documents">
                            <div class="document">
                                <p>Valid ID</p>
                                <img src="${user.valid_id}" alt="Valid ID" class="clickable-image">
                            </div>
                            <div class="document">
                                <p>Certificate</p>
                                <img src="${user.certificate_of_registration}" alt="Certificate" class="clickable-image">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;

        userCard.insertAdjacentElement("afterend", detailsRow);
    }

    document.addEventListener("DOMContentLoaded", function () {
        document.body.addEventListener("click", function (event) {
            if (event.target.classList.contains("clickable-image")) {
                openFullScreenImage(event.target.src);
            }
        });
    });
    
    function openFullScreenImage(imageSrc) {
        const fullScreenDiv = document.createElement("div");
        fullScreenDiv.classList.add("full-screen-view");
    
        const fullScreenImg = document.createElement("img");
        fullScreenImg.src = imageSrc;
        fullScreenImg.alt = "Enlarged Image";
    
        fullScreenDiv.appendChild(fullScreenImg);
        document.body.appendChild(fullScreenDiv);
    
        fullScreenDiv.addEventListener("click", function () {
            fullScreenDiv.remove();
        });
    }

    document.body.addEventListener("click", (event) => {
        if (event.target.classList.contains("clickable-image")) {
            openFullScreenImage(event.target.src);
        }
    });

    filterSelect.addEventListener("change", function () {
        if (this.value) {
            fetchUsers(this.value);
        } else {
            userList.innerHTML = "";
        }
    });
    userList.innerHTML = "";
}
