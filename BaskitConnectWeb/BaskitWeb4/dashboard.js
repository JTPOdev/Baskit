function getColor(index) {
    const colors = ['#FF5733', '#33FF57', '#3357FF', '#FF33A1', '#FFC300', '#1D7151', '#4CAF50', '#2196F3', '#FF9800', '#F44336', '#9C27B0'];
    return colors[index % colors.length];
}

document.addEventListener("DOMContentLoaded", function () {
    // Navigation highlighting
    const navLinks = document.querySelectorAll("ul li a");
    const currentPage = window.location.pathname.split("/").pop();
    navLinks.forEach(link => {
        if (link.getAttribute("href") === currentPage) {
            link.classList.add("active");
        } else {
            link.classList.remove("active");
        }
    });

    //user info to display in the header
    fetch('StatsController.php?data=user')
        .then(response => {
            console.log('User Response Status:', response.status);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('User Data:', data);
            if (data.error) {
                console.error('User data error:', data.error);
                document.querySelector('.header-left h1').textContent = 'Welcome back, Guest';
                return;
            }
            document.querySelector('.header-left h1').textContent = `Welcome back, ${data.username}`;
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
            document.querySelector('.header-left h1').textContent = 'Welcome back, Guest';
        });

    // Fetch summary data
    fetch('StatsController.php?data=summary')
        .then(response => {
            console.log('Summary Response Status:', response.status);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Summary Data:', data);
            if (data.error) {
                console.error('Summary data error:', data.error);
                document.getElementById("salesToday").textContent = '₱0.00';
                document.getElementById("ordersToday").textContent = '0';
                document.getElementById("registeredUsers").textContent = '0';
                return;
            }
            document.getElementById("salesToday").textContent = `₱${Number(data.todaySales).toLocaleString("en-PH", { minimumFractionDigits: 2 })}`;
            document.getElementById("ordersToday").textContent = data.totalOrdersToday;
            document.getElementById("registeredUsers").textContent = data.registeredUsers;
        })
        .catch(error => console.error('Error fetching summary data:', error));

        fetch('StatsController.php?data=user')
        .then(response => {
            console.log('User Response Status:', response.status);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('User Data:', data);
            if (data.error) {
                console.error('User data error:', data.error);
                document.querySelector('.header-left h1').textContent = 'Welcome back, Guest';
                return;
            }
            document.querySelector('.header-left h1').textContent = `Welcome back, ${data.name}`; 
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
            document.querySelector('.header-left h1').textContent = 'Welcome back, Guest';
        });

    // Product Chart
    const productCtx = document.getElementById('productChart').getContext('2d');
    let productChart = new Chart(productCtx, {
        type: 'bar',
        data: {
            labels: [],
            datasets: [{
                label: 'Times Purchased',
                data: [],
                backgroundColor: [],
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

    function fetchProductData(type) {
        fetch(`StatsController.php?data=products&type=${type}`)
            .then(response => {
                console.log('Product Response Status:', response.status);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Product Data:', data);
                if (data.error) {
                    console.error('Product data error:', data.error);
                    productChart.data.labels = ['Error'];
                    productChart.data.datasets[0].data = [0];
                    productChart.data.datasets[0].backgroundColor = ['#FF0000'];
                    productChart.update();
                    return;
                }
                productChart.data.labels = data.labels;
                productChart.data.datasets[0].data = data.data;
                productChart.data.datasets[0].backgroundColor = data.labels.map((_, i) => getColor(i));
                productChart.update();
            })
            .catch(error => console.error('Error fetching product data:', error));
    }

    // Store Chart
    const storeCtx = document.getElementById('storeChart').getContext('2d');
    let storeChart = new Chart(storeCtx, {
        type: 'pie',
        data: {
            labels: [],
            datasets: [{
                label: 'Store Orders',
                data: [],
                backgroundColor: [],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true
        }
    });

    function fetchStoreData() {
        fetch('StatsController.php?data=store_orders')
            .then(response => {
                console.log('Store Response Status:', response.status);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Store Data:', data);
                if (data.error) {
                    console.error('Store data error:', data.error);
                    storeChart.data.labels = ['Error'];
                    storeChart.data.datasets[0].data = [0];
                    storeChart.data.datasets[0].backgroundColor = ['#FF0000'];
                    storeChart.update();
                    return;
                }
                storeChart.data.labels = data.labels;
                storeChart.data.datasets[0].data = data.data;
                storeChart.data.datasets[0].backgroundColor = data.labels.map((_, i) => getColor(i));
                storeChart.update();
            })
            .catch(error => console.error('Error fetching store data:', error));
    }

    // Popular Stores Chart
    const popularStoresCtx = document.getElementById('popularStoresChart').getContext('2d');
    let popularStoresChart = new Chart(popularStoresCtx, {
        type: 'bar',
        data: {
            labels: [],
            datasets: [{
                label: 'Orders by Store',
                data: [],
                backgroundColor: [],
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

    function fetchPopularStoresData(timeframe) {
        fetch(`StatsController.php?data=popular_stores&timeframe=${timeframe}`)
            .then(response => {
                console.log('Popular Stores Response Status:', response.status);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Popular Stores Data:', data);
                if (data.error) {
                    console.error('Popular stores data error:', data.error);
                    popularStoresChart.data.labels = ['Error'];
                    popularStoresChart.data.datasets[0].data = [0];
                    popularStoresChart.data.datasets[0].backgroundColor = ['#FF0000'];
                    popularStoresChart.update();
                    return;
                }
                popularStoresChart.data.labels = data.labels;
                popularStoresChart.data.datasets[0].data = data.data;
                popularStoresChart.data.datasets[0].backgroundColor = data.labels.map((_, i) => getColor(i));
                popularStoresChart.update();
            })
            .catch(error => console.error('Error fetching popular stores data:', error));
    }

    // Sales Chart
    const salesCtx = document.getElementById('salesChart').getContext('2d');
    let salesChart = new Chart(salesCtx, {
        type: 'line',
        data: {
            labels: [],
            datasets: [{
                label: 'Sales',
                data: [],
                borderColor: '#4CAF50',
                fill: false,
                tension: 0.1
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

    function fetchSalesData(period) {
        fetch(`StatsController.php?data=sales&period=${period}`)
            .then(response => {
                console.log('Sales Response Status:', response.status);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Sales Data:', data);
                if (data.error) {
                    console.error('Sales data error:', data.error);
                    salesChart.data.labels = ['Error'];
                    salesChart.data.datasets[0].data = [0];
                    salesChart.update();
                    return;
                }
                salesChart.data.labels = data.labels;
                salesChart.data.datasets[0].data = data.data;
                salesChart.update();
            })
            .catch(error => console.error('Error fetching sales data:', error));
    }

    // Orders Chart
    const ordersCtx = document.getElementById('ordersChart').getContext('2d');
    let ordersChart = new Chart(ordersCtx, {
        type: 'bar',
        data: {
            labels: [],
            datasets: [{
                label: 'Total Orders',
                data: [],
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

    function fetchOrdersData(period) {
        fetch(`StatsController.php?data=orders&period=${period}`)
            .then(response => {
                console.log('Orders Response Status:', response.status);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Orders Data:', data);
                if (data.error) {
                    console.error('Orders data error:', data.error);
                    ordersChart.data.labels = ['Error'];
                    ordersChart.data.datasets[0].data = [0];
                    ordersChart.update();
                    return;
                }
                ordersChart.data.labels = data.labels;
                ordersChart.data.datasets[0].data = data.data;
                ordersChart.update();
            })
            .catch(error => console.error('Error fetching orders data:', error));
    }

    document.getElementById('productToggle').addEventListener('change', function () {
        fetchProductData(this.value);
    });

    document.getElementById('storeTimeframe').addEventListener('change', function () {
        fetchPopularStoresData(this.value);
    });

    document.getElementById('salesFilter').addEventListener('change', function () {
        fetchSalesData(this.value);
    });

    document.getElementById('ordersFilter').addEventListener('change', function () {
        fetchOrdersData(this.value);
    });

 
    fetchProductData('category'); 
    fetchStoreData(); 
    fetchPopularStoresData('weekly'); 
    fetchSalesData('weekly'); 
    fetchOrdersData('weekly'); 
});

// Logout button
document.getElementById("logoutButton").addEventListener("click", function () {
    if (confirm("Are you sure you want to log out?")) {
        sessionStorage.clear();
        localStorage.clear();
        window.location.href = "login.html";
    }
});