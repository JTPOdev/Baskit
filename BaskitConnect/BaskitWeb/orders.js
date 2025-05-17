import { BASE_URL } from "./config.js"; 


document.addEventListener("DOMContentLoaded", () => {
    const navLinks = document.querySelectorAll("ul li a");
    const currentPage = window.location.pathname.split("/").pop();
    const heading = document.querySelector(".header-left h1");
    const nav = document.querySelector("nav");
    const userList = document.getElementById("userList");
    const popup = document.getElementById("orderPopup");
    const orderDetails = document.getElementById("orderDetails");
    const closeButton = document.querySelector(".close-btn");
    const orderDatePicker = document.getElementById("orderDatePicker");
    const completeOrderBtn = document.getElementById("completeOrderBtn");
    const orderCodeInput = document.getElementById("orderCode");
    const viewAllBtn = document.getElementById("viewAll");
    const viewRecentBtn = document.getElementById("viewRecent");
    const statusFilter = document.getElementById("statusFilter");
    const locationFilter = document.getElementById("locationFilter");
    const today = new Date().toISOString().split("T")[0];
 
    
    navLinks.forEach(link => {
        if (link.getAttribute("href") === currentPage) link.classList.add("active");
        else link.classList.remove("active");
    });
 
    heading.classList.add("active");
    heading.addEventListener("click", () => heading.classList.add("active"));
    setTimeout(() => nav.classList.add("show"), 100);
 
    async function fetchOrders(filterDate, filterStatus, filterLocation) {
        try {
            const params = new URLSearchParams();
            if (filterDate) params.append("date", filterDate);
            if (filterStatus && filterStatus !== "all") params.append("status", filterStatus);
            if (filterLocation && filterLocation !== "all") params.append("location", filterLocation);
    
            const response = await fetch(`${BASE_URL}/all/orders?${params.toString()}`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${localStorage.getItem("access_token")}`,
                    "Content-Type": "application/json",
                },
            });
    
            if (!response.ok) throw new Error(`Failed to fetch orders: ${response.status}`);
    
            const data = await response.json();
            console.log("Fetched Orders:", data);
    
            return data.orders || [];
        } catch (error) {
            console.error("Error fetching orders:", error);
            return [];
        }
    }
 
    async function displayOrders(filterDate, filterStatus, filterLocation) {
        userList.innerHTML = "";
        const orders = await fetchOrders(filterDate, filterStatus, filterLocation);
        let completedOrders = JSON.parse(localStorage.getItem("completedOrders")) || {};
    
        if (!orders || orders.length === 0) {
            userList.innerHTML = "<div class='user-card'>No orders found.</div>";
            return;
        }
    
        console.log("Fetched Orders:", orders);
        console.log("Filters:", { filterDate, filterStatus, filterLocation });
    
        const validStatuses = ["Pending", "Accepted"];
    
        let filteredOrders = orders.filter(order => {
            const status = completedOrders[order.user_id]?.status || order.status;
            const matchesStatus = !filterStatus || filterStatus === "all" || status === filterStatus;
            const matchesLocation = !filterLocation || filterLocation === "all" || 
                                    order.product_origin?.trim().toLowerCase() === filterLocation.trim().toLowerCase();
            const matchesDate = !filterDate || new Date(order.created_at).toISOString().split("T")[0] === filterDate;
    
            console.log(`Checking Order: ${order.order_id} | Origin: ${order.product_origin} | Status: ${status} | Date: ${order.created_at} | Matches: ${matchesLocation}, ${matchesStatus}, ${matchesDate}`);
    
            return validStatuses.includes(status) && matchesStatus && matchesLocation && matchesDate;
        });
    
        console.log("Filtered Orders:", filteredOrders);
    
        if (filteredOrders.length === 0) {
            userList.innerHTML = "<div class='user-card'>No orders found for this filter.</div>";
            return;
        }
    
        filteredOrders.forEach(order => {
            const savedOrder = completedOrders[order.user_id] || {};
            const status = savedOrder.status || order.status;
            const orderRow = document.createElement("div");
            orderRow.classList.add("user-card");
            orderRow.innerHTML = `
                <div>${order.order_id}</div>
                <div>${order.firstname} ${order.lastname}</div>
                <div>${order.mobile_number}</div>
                <div>${status}</div>
                <div><button class="view-order-btn" data-id="${order.user_id}">View Order</button></div>
            `;
            userList.appendChild(orderRow);
        }); 
    }
 
    orderDatePicker.value = today;
    orderDatePicker.style.display = "inline-block";
    displayOrders(today, "all", "all");
 
    viewAllBtn.addEventListener("click", () => {
        orderDatePicker.value = "";
        displayOrders(null, statusFilter.value, locationFilter.value);
    });
 
    viewRecentBtn.addEventListener("click", () => {
        orderDatePicker.value = today;
        displayOrders(today, statusFilter.value, locationFilter.value);
    });
 
    orderDatePicker.addEventListener("change", () => {
        const selectedDate = orderDatePicker.value;
        displayOrders(selectedDate, statusFilter.value, locationFilter.value);
    });
 
    statusFilter.addEventListener("change", () => {
        const selectedDate = orderDatePicker.value;
        displayOrders(selectedDate, statusFilter.value, locationFilter.value);
    });
 
    locationFilter.addEventListener("change", () => {
        const selectedDate = orderDatePicker.value;
        displayOrders(selectedDate, statusFilter.value, locationFilter.value);
    });
 
    document.addEventListener("click", async (event) => {
        if (event.target.classList.contains("view-order-btn")) {
            const userId = event.target.getAttribute("data-id");
            console.log("Fetching order details for Order ID:", userId);
    
            try {
                const response = await fetch(`${BASE_URL}/orders?user_id=${userId}`, {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${localStorage.getItem("access_token")}`,
                        "Content-Type": "application/json",
                    },
                });
    
                if (!response.ok) {
                    console.error("API Error:", response.status);
                    alert(`Failed to fetch order details. Status: ${response.status}`);
                    return;
                }
    
                const data = await response.json();
                console.log("Full API Response:", data);
                const orders = data.orders.orders;
    
                if (!orders || orders.length === 0) {
                    alert("No order details found.");
                    return;
                }
    
                const firstOrder = orders[0];
    
                let customerDetails = `
                <div style="display: flex; justify-content: space-between; align-items: flex-start;">
                    <div style="flex: 1; padding-right: 20px;">
                        <p><strong>Name:</strong> ${firstOrder.firstname} ${firstOrder.lastname}</p>
                        <p><strong>Mobile:</strong> ${firstOrder.mobile_number}</p>
                        <p><strong>Location:</strong> ${firstOrder.product_origin}</p>
                        <p><strong>Status:</strong> ${firstOrder.status}</p>
                        <p><strong>Arrived:</strong> ${firstOrder.is_ready}</p>
                    </div>
            
                    <div style="flex: 1; text-align: left;">
                        <p><strong>Tagabili:</strong> ${firstOrder.tagabili_firstname} ${firstOrder.tagabili_lastname}</p>
                        <p><strong>Mobile:</strong> ${firstOrder.tagabili_mobile}</p>
                        <p><strong>Order Date:</strong> ${firstOrder.created_at}</p>
                    </div>
                </div>
            `;
    
                let storeHTML = "";
                let totalPrice = 0;
                let storeOrders = {};
    
                orders.forEach(order => {
                    if (!storeOrders[order.store_name]) {
                        storeOrders[order.store_name] = [];
                    }
                    storeOrders[order.store_name].push(order);
                    totalPrice += parseFloat(order.product_price) + parseFloat(order.tagabili_fee);
                });
    
                for (let store in storeOrders) {
                    storeHTML += `
                        <h3 align="center" class="store-title">${store}</h3>
                        <table class="order-items-table">
                            <tr><th>Product</th><th>Quantity</th><th>Price</th></tr>
                    `;
    
                    storeOrders[store].forEach(order => {
                        storeHTML += `
                            <tr>
                                <td>${order.product_name}</td>
                                <td>1pc | Quantity: ${order.product_quantity}</td>
                                <td>₱${parseFloat(order.product_price).toFixed(2)}</td>
                            </tr>
                        `;
                    });
    
                    storeHTML += `</table><br/>`; 
                }
                
                const tagabiliFee = firstOrder.tagabili_fee ? parseFloat(firstOrder.tagabili_fee) : 0;

                orderDetails.innerHTML = `
                    ${customerDetails}
                    ${storeHTML}
                    <h3>Tagabili fee: <span style="color: #22644B;">₱${tagabiliFee}</span></h3>
                    <h3>Total Price: <span style="color: #22644B;">₱${totalPrice.toFixed(2)}</span></h3>
                `;
    
                popup.style.display = "flex";
            } catch (error) {
                console.error("Error fetching order details:", error);
                alert("Failed to load order details.");
            }
        }
    });
    
 
    completeOrderBtn.addEventListener("click", async () => {
        const enteredCode = orderCodeInput.value.trim();
        const orderId = orderDetails.querySelector("p strong")?.nextSibling?.textContent?.trim();
    
        const orderStatusElement = document.querySelector(`.view-order-btn[data-id="${orderId}"]`)
            ?.closest(".user-card")
            ?.querySelector("div:nth-child(4)");
    
        try {
            const response = await fetch(`${BASE_URL}/order/complete`, {
                method: 'POST',
                headers: { 
                    "Authorization": `Bearer ${localStorage.getItem("access_token")}`, 
                    "Content-Type": "application/json" 
                },
                body: JSON.stringify({ order_code: enteredCode })
            });
    
            const data = await response.json();
    
            if (!response.ok) {
                throw new Error(data.message || 'Failed to complete order');
            }
    
            alert("Order Completed Successfully!");
            if (orderStatusElement) orderStatusElement.textContent = "Completed";
    
            let completedOrders = JSON.parse(localStorage.getItem("completedOrders")) || {};
            completedOrders[orderId] = { status: "Completed", code: enteredCode };
            localStorage.setItem("completedOrders", JSON.stringify(completedOrders));
    
            completeOrderBtn.disabled = true;
            completeOrderBtn.textContent = "Order Completed";
            completeOrderBtn.style.backgroundColor = "#f5f5f5";
            completeOrderBtn.style.color = "#000000";
            orderCodeInput.disabled = true;
    

            popup.style.display = "none";
            displayOrders(orderDatePicker.value, statusFilter.value, locationFilter.value);
    
        } catch (error) {
            alert(error.message || "Incorrect order code. Please try again.");
        }
    });
    
    closeButton.addEventListener("click", () => popup.style.display = "none");
    
    popup.addEventListener("click", (event) => {
        if (event.target === popup) popup.style.display = "none";
    });
}); 