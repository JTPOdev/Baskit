import { BASE_URL } from "./config.js"; 

document.addEventListener("DOMContentLoaded", () => {
    const navLinks = document.querySelectorAll("ul li a");
    const currentPage = window.location.pathname.split("/").pop(); 

    navLinks.forEach(link => {
        link.classList.toggle("active", link.getAttribute("href") === currentPage);
    });

    const heading = document.querySelector(".header-left h1");
    if (heading) heading.classList.add("active");

    heading?.addEventListener("click", function () {
        this.classList.add("active"); 
    });

    const nav = document.querySelector("nav");
    setTimeout(() => nav?.classList.add("show"), 100);
    initUserManagement();
});

function initUserManagement() {
    const userInfoContainer = document.querySelector(".business-profile");
    const filterSelect = document.getElementById("filterSelect");

    let users = [];
    let declinedUsers = JSON.parse(localStorage.getItem("declinedUsers")) || [];
    let approvedUsers = JSON.parse(localStorage.getItem("approvedUsers")) || [];

    async function fetchUsers(filter = "") {
        try {
            const response = await fetch(`${BASE_URL}/store/request/all`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json"
                }
            });

            if (!response.ok) throw new Error("Failed to fetch users");

            let fetchedUsers = await response.json();
            console.log("Fetched users:", fetchedUsers);

            fetchedUsers = fetchedUsers.filter(user => 
                (user.request_status || "").trim().toLowerCase() === "pending" &&
                !declinedUsers.includes(user.id) &&
                !approvedUsers.includes(user.id) // Exclude already declined/approved users
            );

            if (!filter) {
                fetchedUsers = fetchedUsers.filter(user => {
                    const status = (user.store_status || "").trim().toLowerCase();
                    return status !== "partner" && status !== "standard";
                });
            } else {
                fetchedUsers = fetchedUsers.filter(user => (user.store_status || "").trim().toLowerCase() === filter);
            }

            users = fetchedUsers;
            renderUsers(users);
        } catch (error) {
            console.error("Error fetching users:", error);
            userInfoContainer.innerHTML = `
                <div class="empty-container">
                    <img src="img/cuate.png" alt="No Data Available">
                    <p class="empty-text">Error fetching data. Please try again.</p>
                </div>
            `;
        }
    }

    function renderUsers(usersToDisplay) {
        userInfoContainer.innerHTML = `
            <div class="header-row">
                <div>Name</div>     
                <div>Username</div>
                <div>Mobile Number</div>
                <div>Email</div>
            </div>
        `;

        if (usersToDisplay.length === 0) {
            userInfoContainer.innerHTML += `
                <div class="empty-container">
                    <img src="img/cuate.png" alt="No Data Available">
                    <p class="empty-text">No store requests available.</p>
                </div>
            `;
            return;
        }

        usersToDisplay.forEach(user => {
            let userRow = document.createElement("div");
            userRow.classList.add("user-info");
            userRow.dataset.userId = user.id;
            userRow.innerHTML = `
                <div>${user.firstname} ${user.lastname || "N/A"}</div>
                <div>${user.username || "N/A"}</div>
                <div>${user.mobile_number || "N/A"}</div>
                <div class="email">
                    <span>${user.user_email || "N/A"}</span>
                    <i class="fa-solid fa-chevron-down"></i>
                </div>
            `;

            userRow.addEventListener("click", function () {
                toggleUserDetails(userRow, user);
            });

            userInfoContainer.appendChild(userRow);
        });
    }

    function toggleUserDetails(userRow, user) {
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
                <div class="actions">
                    <button class="decline" data-user-id="${user.id}" data-action="decline">✖ Decline</button>
                    <button class="approve" data-user-id="${user.id}" data-action="approve">✔ Approve</button>
                </div>
            </div>
        `;

        userRow.insertAdjacentElement("afterend", detailsRow);
    }

    function showToast(message) {
        // Remove existing toast if present
        let existingToast = document.querySelector(".toast");
        if (existingToast) {
            existingToast.remove();
        }
    
        // Create toast
        const toast = document.createElement("div");
        toast.classList.add("toast");
        toast.textContent = message;
    
        // Append toast to body
        document.body.appendChild(toast);
    
        // Remove toast after 3 seconds
        setTimeout(() => {
            toast.classList.add("fade-out");
            setTimeout(() => toast.remove(), 500);
        }, 3000);
    }

    function handleAction(userId, action) {
        const endpoint = action === "approve" ? "store/approve" : "store/decline";
        const button = document.querySelector(`[data-user-id="${userId}"][data-action="${action}"]`);

        const loader = document.createElement("span");
        loader.classList.add("loading-spinner");

        button.innerHTML = "";
        button.appendChild(loader);
        button.disabled = true;

        fetch(`${BASE_URL}/${endpoint}/${userId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            }
        })
        .then(response => response.json())
        .then(() => {
            showToast(`User ${action}d`);

            if (action === "approve") {
                approvedUsers.push(userId);
                localStorage.setItem("approvedUsers", JSON.stringify(approvedUsers));
            } else {
                declinedUsers.push(userId);
                localStorage.setItem("declinedUsers", JSON.stringify(declinedUsers));
            }

            users = users.filter(user => user.id !== userId);
            renderUsers(users);
        })
        .catch(error => {
            console.error("Error updating store status:", error);
            alert("An error occurred. Please try again.");
        });
    }

    filterSelect.addEventListener("change", function () {
        fetchUsers(this.value.trim().toLowerCase());
    });

    document.addEventListener("click", function (event) {
        if (event.target.matches(".approve, .decline")) {
            let userId = parseInt(event.target.dataset.userId, 10);
            let action = event.target.dataset.action;
            handleAction(userId, action);
        }
    });

    fetchUsers();
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
    fullScreenDiv.innerHTML = `<img src="${imageSrc}" alt="Enlarged Image">`;
    document.body.appendChild(fullScreenDiv);
    fullScreenDiv.addEventListener("click", () => fullScreenDiv.remove());
}
