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
    const summaryData = {
        todaySales: 150.75,
        totalOrdersToday: 25,
        registeredUsers: 120
    };

    document.getElementById("salesToday").textContent = `₱${summaryData.todaySales.toLocaleString("en-PH", { minimumFractionDigits: 2 })}`;
    document.getElementById("ordersToday").textContent = summaryData.totalOrdersToday;
    document.getElementById("registeredUsers").textContent = summaryData.registeredUsers;
});

document.addEventListener("DOMContentLoaded", function () {
    const productCtx = document.getElementById('productChart').getContext('2d');

    const categoryData = {
        labels: ['Fruits', 'Vegetables', 'Meat', 'Fish', 'Frozen', 'Spices'],
        data: [500, 350, 200, 180, 300, 100],
        colors: ['#FF5733', '#33FF57', '#3357FF', '#FF33A1', '#FFC300', '#1D7151']
    };

    const productData = {
        labels: ['Apple', 'Orange', 'Milk', 'Chicken', 'Soda'],
        data: [150, 120, 100, 90, 130],
        colors: ['#4CAF50', '#2196F3', '#FF9800', '#F44336', '#9C27B0']
    };

    let productChart = new Chart(productCtx, {
        type: 'bar',
        data: {
            labels: categoryData.labels,
            datasets: [{
                label: 'Times Purchased',
                data: categoryData.data,
                backgroundColor: categoryData.colors,
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            scales: {
                y: { beginAtZero: true }
            }
        }
    });

    document.getElementById("productToggle").addEventListener("change", function () {
        if (this.value === "category") {
            productChart.data.labels = categoryData.labels;
            productChart.data.datasets[0].data = categoryData.data;
            productChart.data.datasets[0].backgroundColor = categoryData.colors;
        } else {
            productChart.data.labels = productData.labels;
            productChart.data.datasets[0].data = productData.data;
            productChart.data.datasets[0].backgroundColor = productData.colors;
        }
        productChart.update();
    });
});

const storeCtx = document.getElementById('storeChart').getContext('2d');
const storeChart = new Chart(storeCtx, {
    type: 'pie',
    data: {
        labels: ['Standard Store', 'Partnership Store'],
        datasets: [{
            label: 'Orders',
            data: [200, 300],
            backgroundColor: ['#FF6384', '#36A2EB']
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const storeCtx = document.getElementById('popularStoresChart').getContext('2d');

    const storeData = {
        weekly: [40, 60, 35, 50],
        monthly: [200, 300, 250, 275],
        yearly: [2400, 3200, 2800, 3100]
    };

    const labels = ["Store A", "Store B", "Store C", "Store D"];

    let popularStoresChart = new Chart(storeCtx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Purchases',
                data: storeData.weekly,
                backgroundColor: ['#FF5733', '#33FF57', '#3357FF', '#FF33A1']
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: { beginAtZero: true }
            }
        }
    });

    document.getElementById("storeTimeframe").addEventListener("change", function () {
        const timeframe = this.value;
        popularStoresChart.data.datasets[0].data = storeData[timeframe];
        popularStoresChart.update();
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const salesData = {
        weekly: [1500, 1800, 1300, 2200, 2000, 2500, 2700], 
        monthly: [12000, 13500, 15000, 11000],  
        yearly: [150000, 160000, 180000, 200000, 220000, 210000, 250000, 270000, 300000, 310000, 320000, 350000]  
    };

    const salesLabels = {
        weekly: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
        monthly: ["Week 1", "Week 2", "Week 3", "Week 4"],
        yearly: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
    };

    const ctx = document.getElementById('salesChart').getContext('2d');
    let salesChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: salesLabels.weekly,
            datasets: [{
                label: 'Sales (₱)',
                data: salesData.weekly,
                borderColor: '#4CAF50',
                backgroundColor: 'rgba(76, 175, 80, 0.2)',
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: { beginAtZero: true }
            }
        }
    });

    document.getElementById('salesFilter').addEventListener('change', function () {
        const selectedOption = this.value;
        salesChart.data.labels = salesLabels[selectedOption];
        salesChart.data.datasets[0].data = salesData[selectedOption];
        salesChart.update();
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const ordersData = {
        weekly: [100, 120, 90, 140, 130, 160, 170],  
        monthly: [400, 450, 500, 480],  
        yearly: [5000, 5500, 6000, 5800, 6200, 6400, 6800, 7100, 7400, 7800, 8000, 8500]  
    };

    const ordersLabels = {
        weekly: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
        monthly: ["Week 1", "Week 2", "Week 3", "Week 4"],
        yearly: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
    };

    const ordersCtx = document.getElementById('ordersChart').getContext('2d');
    let ordersChart = new Chart(ordersCtx, {
        type: 'bar',
        data: {
            labels: ordersLabels.weekly,
            datasets: [{
                label: 'Total Orders',
                data: ordersData.weekly,
                backgroundColor: '#22644B',
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: { beginAtZero: true }
            }
        }
    });

    document.getElementById('ordersFilter').addEventListener('change', function () {
        const selectedOption = this.value;
        ordersChart.data.labels = ordersLabels[selectedOption];
        ordersChart.data.datasets[0].data = ordersData[selectedOption];
        ordersChart.update();
    });
});

// Logout
document.getElementById("logoutButton").addEventListener("click", function () {
    if (confirm("Are you sure you want to log out?")) {
        sessionStorage.clear();
        localStorage.clear();
        window.location.href = "login.html";
    }
});
