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
    const userList = document.getElementById("userList");
    const popup = document.getElementById("orderPopup");
    const orderDetails = document.getElementById("orderDetails");
    const closeButton = document.querySelector(".close-btn");
    const viewOrderHistoryBtn = document.getElementById("viewOrderHistory");
    const orderDatePicker = document.getElementById("orderDatePicker");
    const today = new Date().toISOString().split("T")[0];
    const completeOrderBtn = document.getElementById("completeOrderBtn");
    const orderCodeInput = document.getElementById("orderCode");

    const orders = [
        {
            id: "1450",
            customer: "James",
            date: "2025-03-11",
            status: "Pending",
            items: [
                { name: "Apple", price: "₱20", store: "ABC Store" },
                { name: "Orange", price: "₱22", store: "ABC Store" },
                { name: "Tuna", price: "₱25", store: "DEF Store" },
            ]
        },
        {
            id: "67890",
            customer: "James",
            date: "2025-03-19",
            status: "Pending",
            items: [
                { name: "Apple", price: "₱20", store: "ABC Store" },
                { name: "Orange", price: "₱22", store: "ABC Store" },
            ]
        },
        {
            id: "11223",
            customer: "Laurence",
            date: "2025-03-27",
            status: "Pending",
            items: [
                { name: "Apple", price: "₱20", store: "ABC Store" },
                { name: "Orange", price: "₱22", store: "ABC Store" },
            ]
        },
        {
            id: "3590",
            customer: "James1",
            date: "2025-03-19",
            status: "Pending",
            items: [
                { name: "Apple", price: "₱20", store: "ABC Store" },
                { name: "Orange", price: "₱22", store: "ABC Store" },
            ]
        },
        {
            id: "4567",
            customer: "James9",
            date: "2025-03-23",
            status: "Pending",
            items: [
                { name: "Apple", price: "₱20", store: "ABC Store" },
                { name: "Orange", price: "₱22", store: "ABC Store" },
            ]
        },
        {
            id: "1234",
            customer: "James99",
            date: "2025-03-23",
            status: "Pending",
            items: [
                { name: "Apple", price: "₱20", store: "ABC Store" },
                { name: "Orange", price: "₱22", store: "ABC Store" },
            ]
        }

    ];

    function displayOrdersByDate(date) {
        userList.innerHTML = "";
    
        let completedOrders = JSON.parse(localStorage.getItem("completedOrders")) || {};
        
        const filteredOrders = orders.filter(order => order.date === date);
        if (filteredOrders.length === 0) {
            userList.innerHTML = "<div class='user-card'>No orders found for this date.</div>";
            return;
        }
    
        filteredOrders.forEach(order => {
            const savedOrder = completedOrders[order.id] || {};
            const status = savedOrder.status || order.status;
            const savedCode = savedOrder.code || "";
    
            const orderRow = document.createElement("div");
            orderRow.classList.add("user-card");
            orderRow.innerHTML = `
                <div>${order.id}</div>
                <div>${order.customer}</div>
                <div>${order.date}</div>
                <div>${status}</div>
                <div>
                    <button class="view-order-btn" data-id="${order.id}">View Order</button>
                </div>
            `;
            userList.appendChild(orderRow);
    
            document.addEventListener("click", function (event) {
                if (event.target.classList.contains("view-order-btn")) {
                    const orderId = event.target.getAttribute("data-id");
                    const order = orders.find(o => o.id === orderId);
                    const completedOrders = JSON.parse(localStorage.getItem("completedOrders")) || {};
                    
                    if (order) {
                        const savedOrder = completedOrders[orderId] || {};
                        const status = savedOrder.status || order.status;
                        const savedCode = savedOrder.code || "";
            
                        orderDetails.innerHTML = `
                            <p><strong>Order ID:</strong> ${order.id}</p>
                            <p><strong>Customer:</strong> ${order.customer}</p>
                            <p><strong>Date:</strong> ${order.date}</p>
                            <p><strong>Status:</strong> ${status}</p>
                        `;
            
                        if (status === "Completed") {
                            orderCodeInput.value = savedCode;  
                            orderCodeInput.disabled = true;
                            completeOrderBtn.disabled = true;
                            completeOrderBtn.textContent = "Order Completed";
                            completeOrderBtn.style.backgroundColor = "#f5f5f5"; 
                        } else {
                            orderCodeInput.value = ""; 
                            orderCodeInput.disabled = false;
                            completeOrderBtn.disabled = false;
                            completeOrderBtn.textContent = "Complete Order";
                            completeOrderBtn.style.backgroundColor = ""; 
                        }
            
                        popup.style.display = "flex";
                    }
                }
            });
            
        });
    }
    
    


    orderDatePicker.value = today;

    displayOrdersByDate(today);

    orderDatePicker.addEventListener("change", function () {
        displayOrdersByDate(this.value); 
    
        document.getElementById("viewOrderHistory").textContent = "Recent Orders";
        document.getElementById("viewOrderHistory").classList.add("reset-mode"); 
    });
    
    document.getElementById("viewOrderHistory").addEventListener("click", function () {
        orderDatePicker.value = today; 
        displayOrdersByDate(today); 
        
        this.textContent = "View Order History";
        this.classList.remove("reset-mode");
    });
    

    viewOrderHistoryBtn.addEventListener("click", function () {
        if (orderDatePicker.style.display === "none" || orderDatePicker.style.display === "") {
            orderDatePicker.style.display = "block"; 
        } else {
            orderDatePicker.style.display = "none";  
        }
    });
    
    document.addEventListener("click", function (event) {
        if (event.target.classList.contains("view-order-btn")) {
            const orderId = event.target.getAttribute("data-id");
            const order = orders.find(o => o.id === orderId);

            if (order) {
                let totalPrice = 0;
                let storeHTML = "";

                let storeItems = {};
                order.items.forEach(item => {
                    if (!storeItems[item.store]) {
                        storeItems[item.store] = [];
                    }
                    storeItems[item.store].push(item);
                    totalPrice += parseFloat(item.price.replace("₱", ""));
                });

                for (let store in storeItems) {
                    storeHTML += `
                        <h3>${store}</h3>
                        <table class="order-items-table">
                            <tr><th>Item</th><th>Price</th></tr>
                    `;
                    storeItems[store].forEach(item => {
                        storeHTML += `<tr><td>${item.name}</td><td>${item.price}</td></tr>`;
                    });
                    storeHTML += `</table>`;
                }

                orderDetails.innerHTML = `
                    <p><strong>Order ID:</strong> ${order.id}</p>
                    <p><strong>Customer:</strong> ${order.customer}</p>
                    <p><strong>Date:</strong> ${order.date}</p>
                    <p><strong>Status:</strong> ${order.status}</p>
                    ${storeHTML}
                    <h3>Total Price: <span style="color: #22644B;">₱${totalPrice.toFixed(2)}</span></h3>
                `;

                popup.style.display = "flex";
            }
        }
        
    });

    completeOrderBtn.addEventListener("click", function () {
        const enteredCode = orderCodeInput.value.trim();
        const orderIdElement = document.querySelector("#orderDetails p strong");
    
        if (!orderIdElement) return;
    
        const orderId = orderIdElement.nextSibling.textContent.trim();
        const order = orders.find(o => o.id === orderId); 
        const orderStatusElement = document.querySelector(`.view-order-btn[data-id="${orderId}"]`)
            ?.closest(".user-card")
            ?.querySelector("div:nth-child(4)");
    
        if (enteredCode === orderId && order) {
            order.status = "Completed";
            orderStatusElement.textContent = "Completed"; 
            
            let completedOrders = JSON.parse(localStorage.getItem("completedOrders")) || {};
            completedOrders[orderId] = { status: "Completed", code: enteredCode }; 
            localStorage.setItem("completedOrders", JSON.stringify(completedOrders));
    
            completeOrderBtn.disabled = true;
            completeOrderBtn.textContent = "Order Completed";
            completeOrderBtn.style.backgroundColor = "#f5f5f5"; 
            completeOrderBtn.style.color = "#000000"
            orderCodeInput.value = enteredCode; 
            orderCodeInput.disabled = true; 
    
            popup.style.display = "none"; 
        } else {
            alert("Incorrect order code. Please try again.");
        }
    });
    
    


    closeButton.addEventListener("click", function () {
        popup.style.display = "none";
    });

    popup.addEventListener("click", function (event) {
        if (event.target === popup) {
            popup.style.display = "none";
        }
    });
});