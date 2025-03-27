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
    let users = [
        { name: "sun sun sun", username: "sun3", store: "SuperSun", mobile: "09123456789", email: "sun@email.com", type: "owner" },
        { name: "san san san", username: "san", mobile: "0987654321", email: "san@example.com", type: "customer" } 
    ];
        let approvedStores = JSON.parse(localStorage.getItem("approvedStores")) || [];
        users = users.concat(approvedStores);

    const userList = document.getElementById("userList");
    const filterSelect = document.getElementById("filterSelect");
    const headerRow = document.querySelector(".header-row");
    const userDetailsContainer = document.getElementById("userDetailsContainer");

    function displayUsers(filter = "") {
        userList.innerHTML = "";
        userDetailsContainer.style.display = "none"; 
    
        if (!filter) return;
    
        let filteredUsers = users.filter(user => user.type === filter);
    
        headerRow.innerHTML = (filter === "owner") ? `
            <div>Name</div><div>Username</div><div>Store Name</div><div>Mobile</div><div>Email</div>
        ` : `
            <div>Name</div><div>Username</div><div>Mobile</div><div>Email</div>
        `;
        headerRow.style.gridTemplateColumns = (filter === "owner") ? "repeat(5, 1fr)" : "repeat(4, 1fr)";
    
        filteredUsers.forEach(user => {
            const userCard = document.createElement("div");
            userCard.classList.add("user-card", user.type === "owner" ? "owner" : "tagabili");

            userCard.innerHTML = (user.type === "owner") ? `
                <div class="user-info">${user.name}</div>
                <div class="user-info">${user.username}</div>
                <div class="user-info">${user.store}</div>
                <div class="user-info">${user.mobile}</div>
                <div class="user-info">${user.email}</div>
            ` : `
                <div class="user-info">${user.name}</div>
                <div class="user-info">${user.username}</div>
                <div class="user-info">${user.mobile}</div>
                <div class="user-info">${user.email}</div>
            `;
            
        
    
            userCard.style.cursor = "pointer";
            userCard.addEventListener("click", function () {
                displayUserDetails(user, this);
            });
            
    
            userList.appendChild(userCard);
        });
    }

    function displayUserDetails(user, clickedCard) {
        const existingDetails = document.querySelector(".details-row");
        if (existingDetails) existingDetails.remove();

        if (clickedCard.classList.contains("details-visible")) {
            clickedCard.classList.remove("details-visible");
            return;
        }

        let detailsContainer = document.createElement("div");
        detailsContainer.classList.add("details-row");
        detailsContainer.dataset.userId = user.username;

        if (user.type === "owner") {
            detailsContainer.innerHTML = `
                <div class="info-sections">
                    <div class="info-container">
                        <div class="info-box">
                            <h2>STORE INFORMATION</h2>
                            <div><strong>Store name:</strong> ${user.store}</div>
                            <div><strong>Owner name:</strong> ${user.name}</div>
                            <div><strong>Contact number:</strong> ${user.contact}</div>
                            <div><strong>Address:</strong> ${user.address}</div>
                            <div><strong>Store Origin:</strong> ${user.origin}</div>
                        </div>
                        <div class="info-box">
                            <h2>BUSINESS INFORMATION</h2>
                            <div><strong>Registered store name:</strong> ${user.registeredStoreName}</div>
                            <div><strong>Registered store address:</strong> ${user.registeredStoreAddress}</div>
                            <div><strong>Store type:</strong> ${user.storeType}</div>
                        </div>
                    </div>
                    <div class="documents">
                        <div class="document">
                            <p>Valid ID</p>
                            <img src="${user.validId}" alt="Valid ID" class="clickable-image">
                        </div>
                        <div class="document">
                            <p>Certificate</p>
                            <img src="${user.certificate}" alt="Certificate" class="clickable-image">
                        </div>
                    </div>
                </div>
            `;
        } else {
            detailsContainer.innerHTML = `
                <div class="user-details">
                    <h3>User Details</h3>
                    <p><strong>Name:</strong> ${user.name}</p>
                    <p><strong>Username:</strong> ${user.username}</p>
                    <p><strong>Mobile:</strong> ${user.mobile}</p>
                    <p><strong>Email:</strong> ${user.email}</p>
                    <p><strong>Password:</strong> <span id="passwordText">••••••••</span>
                        <button id="togglePassword">Show</button>
                    </p>
                </div>
            `;

            const passwordText = detailsContainer.querySelector("#passwordText");
            const togglePassword = detailsContainer.querySelector("#togglePassword");

            togglePassword.addEventListener("click", function () {
                if (passwordText.innerText === "••••••••") {
                    passwordText.innerText = user.password;
                    togglePassword.innerText = "Hide";
                } else {
                    passwordText.innerText = "••••••••";
                    togglePassword.innerText = "Show";
                }
            });
        }

        clickedCard.after(detailsContainer);
        clickedCard.classList.add("details-visible");
    }

    filterSelect.addEventListener("change", function () {
        displayUsers(this.value);
    });

    displayUsers(filterSelect.value);


    const openFormButton = document.getElementById("openFormButton");
    const closeFormButton = document.getElementById("closeFormButton");
    const userFormContainer = document.getElementById("userFormContainer");
    const userForm = document.getElementById("userForm");

    openFormButton.addEventListener("click", function () {
        userFormContainer.style.display = "block";
    });

    closeFormButton.addEventListener("click", function () {
        userFormContainer.style.display = "none";
    });

    userForm.addEventListener("submit", function (event) {
        event.preventDefault();
    
        const name = document.getElementById("name").value;
        const birthday = document.getElementById("birthday").value; 
        const username = document.getElementById("username").value;
        const mobile = document.getElementById("mobile").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
    
        const newUser = {
            name,
            birthday,
            username,
            mobile,
            email,
            password,
            type: "tagabili"
        };
    
        users.push(newUser);
        userForm.reset();
        userFormContainer.style.display = "none";
    
        if (filterSelect.value === "tagabili") {
            displayUsers("tagabili");
        }
    });
    
});

document.getElementById("userForm").addEventListener("submit", function (event) {
    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirmPassword").value;
    let errorText = document.getElementById("passwordError");

    if (password !== confirmPassword) {
        event.preventDefault(); 
        errorText.style.display = "block";
    } else {
        errorText.style.display = "none";
    }
});


document.addEventListener("DOMContentLoaded", function () {
    const filterSelect = document.getElementById("filterSelect");
    const openFormButton = document.getElementById("openFormButton");

    filterSelect.addEventListener("change", function () {
        if (this.value === "tagabili") {
            openFormButton.style.display = "block";
        } else {
            openFormButton.style.display = "none";
        }
    });

    openFormButton.style.display = "none";
});


document.getElementById("logoutButton").addEventListener("click", function () {
    if (confirm("Are you sure you want to log out?")) {
        sessionStorage.clear();
        localStorage.clear();
        window.location.href = "login.html";
    }
});

