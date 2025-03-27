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
    const userInfoContainer = document.querySelector(".business-profile");
    const filterSelect = document.getElementById("filterSelect");

    let users = [
        {
            id: 1,
            name: "Store A",
            username: "Store A",
            mobile: "09123456789",
            email: "store@gmail.com",
            storeName: "Store A",
            ownerName: "sumn",
            contact: "0900-000-0000",
            address: "Somewhere, Dagupan City, Pangasinan",
            origin: "Dagupan",
            registeredStoreName: "sumn Store",
            registeredStoreAddress: "ABC ABC ABC",
            storeType: "Partnership",
            validId: "img/logo.png",
            certificate: "img/logo.png"
        },
        {
            id: 2,
            name: "Store B",
            username: "Store B",
            mobile: "09223334455",
            email: "store@gmail.com",
            storeName: "Store B",
            ownerName: "user user",
            contact: "0998-888-7777",
            address: "Dagupan",
            origin: "Dagupan",
            registeredStoreName: "User Store",
            registeredStoreAddress: "XYZ XYZ XYZ",
            storeType: "Partnership",
            validId: "img/logo.png",
            certificate: "img/logo.png"
        },
        {
            id: 3,
            name: "Store C",
            username: "Store C",
            mobile: "09223334455",
            email: "store@gmail.com",
            storeName: "Store C",
            ownerName: "user user",
            contact: "0998-888-7777",
            address: "Dagupan",
            origin: "Dagupan",
            registeredStoreName: "User Store",
            registeredStoreAddress: "XYZ XYZ XYZ",
            storeType: "Standard",
            validId: "img/logo.png",
            certificate: "img/logo.png"
        },
        {
            id: 4,
            name: "Store D",
            username: "Store D",
            mobile: "09123456789",
            email: "store@gmail.com",
            storeName: "Store D",
            ownerName: "sumn",
            contact: "0900-000-0000",
            address: "Somewhere, Dagupan City, Pangasinan",
            origin: "Dagupan",
            registeredStoreName: "sumn Store",
            registeredStoreAddress: "ABC ABC ABC",
            storeType: "Standard",
            validId: "img/logo.png",
            certificate: "img/logo.png"
        }
    ];

    function renderUsers(usersToDisplay) {
        userInfoContainer.innerHTML = "";

        if (usersToDisplay.length === 0) return;

        userInfoContainer.innerHTML = `
            <div class="header-row">
                <div>Name</div>     
                <div>Username</div>
                <div>Mobile Number</div>
                <div>Email</div>
            </div>
        `;

        usersToDisplay.forEach(user => {
            let userRow = document.createElement("div");
            userRow.classList.add("user-info");
            userRow.dataset.userId = user.id;
            userRow.innerHTML = `
                <div>${user.name}</div>
                <div>${user.username}</div>
                <div>${user.mobile}</div>
                <div class="email">
                    <span>${user.email}</span>
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
                        <div><strong>Store name:</strong> ${user.storeName}</div>
                        <div><strong>Owner name:</strong> ${user.ownerName}</div>
                        <div><strong>Contact number:</strong> ${user.contact}</div>
                        <div><strong>Address:</strong> ${user.address}</div>
                        <div><strong>Store Origin:</strong> ${user.origin}</div>
                    </div>
                    <div class="info-box">
                        <h2>BUSINESS INFORMATION</h2>
                        <div><strong>Registered store name:</strong> ${user.registeredStoreName}</div>
                        <div><strong>Registered store address:</strong> ${user.registeredStoreAddress}</div>
                        <div><strong>Store type:</strong> ${user.storeType}</div>
                <div class="documents">
                    <div class="document">
                        <p>Valid ID</p>
                        <img src="${user.validId}" alt="Valid ID" class="clickable-image" onerror="this.onerror=null; this.src='img/default-id.png';">
                    </div>
                    <div class="document">
                        <p>Certificate</p>
                        <img src="${user.certificate}" alt="Certificate" class="clickable-image" onerror="this.onerror=null; this.src='img/default-certificate.png';">
                    </div>
                </div>
                    </div>
                </div>
                <div class="actions">
                    <button class="decline">✖ Decline</button>
                    <button class="approve">✔ Approve</button>
                </div>
            </div>
        `;
    
        userRow.insertAdjacentElement("afterend", detailsRow);
        userInfoContainer.style.height = userInfoContainer.scrollHeight + "px";
    }
    

    function handleAction(userId, action) {
        alert(`User ID ${userId} has been ${action}d.`);
        
        users = users.filter(user => user.id !== userId);
        renderUsers(users);
    }

    filterSelect.addEventListener("change", function () {
        let selectedType = this.value.toLowerCase();

        if (!selectedType || selectedType === "all") {
            userInfoContainer.innerHTML = "";
            return;
        }

        let filteredUsers = users.filter(user => user.storeType.toLowerCase() === selectedType);
        renderUsers(filteredUsers);
    });

    document.addEventListener("click", function (event) {
        if (event.target.matches(".approve") || event.target.matches(".decline")) {
            let userId = event.target.closest(".details-row").dataset.userId;
            let action = event.target.matches(".approve") ? "approved" : "declined";
    
            let scrollY = window.scrollY;
    
            let userIndex = users.findIndex(u => u.id == userId);
            if (userIndex !== -1) {
                let user = users[userIndex];
    
                if (action === "approved") {
                    let approvedStores = JSON.parse(localStorage.getItem("approvedStores")) || [];
                    if (!approvedStores.some(store => store.username === user.username)) {
                        approvedStores.push({
                            name: user.name,
                            username: user.username,
                            store: user.storeName,
                            mobile: user.mobile,
                            email: user.email,
                            type: "owner",
                            contact: user.contact,
                            address: user.address,
                            origin: user.origin,
                            registeredStoreName: user.registeredStoreName,
                            registeredStoreAddress: user.registeredStoreAddress,
                            storeType: user.storeType,
                            validId: user.validId || "img/default-id.png",
                            certificate: user.certificate || "img/default-certificate.png"
                        });
                        localStorage.setItem("approvedStores", JSON.stringify(approvedStores));
                    }
                }
    
                users.splice(userIndex, 1);
    
                let userRow = document.querySelector(`.user-info[data-user-id="${userId}"]`);
                let detailsRow = document.querySelector(`.details-row[data-user-id="${userId}"]`);
    
                if (userRow) userRow.remove();
                if (detailsRow) detailsRow.remove();
    
                alert(`${user.storeName} has been ${action}.`);
            }
    
            window.scrollTo(0, scrollY);
        }
    });

});

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


    // Logout
    document.getElementById("logoutButton").addEventListener("click", function () {
        if (confirm("Are you sure you want to log out?")) {
            sessionStorage.clear();
            localStorage.clear();
            window.location.href = "login.html";
        }
    });
